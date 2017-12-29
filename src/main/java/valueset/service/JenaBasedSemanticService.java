package valueset.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.dao.CodeSystemRepository;
import valueset.model.dbModel.CodeSystem;
import valueset.model.dbModel.ConceptCode;
import valueset.model.modelView.ConceptCodeModelView;
import valueset.model.modelView.RelationStatisticsModelView;
import valueset.model.modelView.SemanticRelationModelView;
import valueset.rdfModelManage.LoadRdfModel;
import valueset.rdfModelManage.RdfModelViewUnit;
import valueset.utils.ConstantUtil;

@Service
public class JenaBasedSemanticService {

	private final String PREDICATE = "PREDICATE";
	private final String OBJECT = "OBJECT";

	@Autowired
	private LoadRdfModel loadRdfModel;
	@Autowired
	private ConceptCodeService conceptCodeService;

	private final CodeSystemRepository codeSystemRep;

	public JenaBasedSemanticService(CodeSystemRepository codeSystemRep) {
		this.codeSystemRep = codeSystemRep;
	}

	public List<String> queryTargetConeptViaRelation(String terminology, String sourceConcept, String commandText) {

		// Get RDF model by requested terminology
		Model model = loadRdfModel.getModelMap().get(terminology);

		// Parameterize SPARQL query
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		// Set prefix
		setNameSpace(pss);
		// create query command - Query all predicates of a given concept code,
		// and its corresponding concept/Literal
		pss.setCommandText(commandText);
		pss.setLiteral("concept0", sourceConcept);

		Query query = QueryFactory.create(pss.toString());// create a query
															// statement with
															// given query
															// string
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();
		List<String> targetConcepts = new ArrayList<String>();
		if (!rs.hasNext()) {
			System.out.println("No components ! " + sourceConcept);
		}
		while (rs.hasNext()) {

			QuerySolution soln = rs.nextSolution();
			String targetConcept = "";
			// Handle RxNorm
			if (ConstantUtil.RXNORM.equalsIgnoreCase(terminology)) {
				RDFNode rdfNode = soln.get("o");
				if ("".equals(rdfNode.asResource().getLocalName())) {
					String uri = rdfNode.asResource().getURI();
					String[] strArray = uri.split("\\/");
					targetConcept = strArray[strArray.length - 1];
				} else {
					targetConcept = rdfNode.asResource().getLocalName();

				}
				targetConcepts.add(targetConcept);
			} else {
				if (ConstantUtil.LOINC.equalsIgnoreCase(terminology)) {
					if (!soln.getLiteral("notation").getLexicalForm().isEmpty()) {
						targetConcept = soln.getLiteral("notation").getLexicalForm();
						System.out.println(targetConcept);
					}
				} else {
					if (null != soln.getLiteral("notation")) {
						targetConcept = soln.getLiteral("notation").getLexicalForm();
					} else {
						targetConcept = soln.getLiteral("cui").getLexicalForm();
					}
				}

				targetConcepts.add(targetConcept);
			}

		}

		return targetConcepts;

	}

	/**
	 * Query direct semantic relation by giving terminology and concept code
	 * 
	 * @param terminology
	 * @param conceptCode
	 * @return
	 */
	public SemanticRelationModelView queryRelationOfConceptCode(String terminology, String conceptCode) {

		// Get RDF model by requested terminology
		Model model = loadRdfModel.getModelMap().get(terminology);

		// For unit test
		// Model model = RDFDataMgr.loadModel("terminologies/RXNORM.ttl");

		// Parameterize SPARQL query
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		// Set prefix
		setNameSpace(pss);
		// create query command - Query all predicates of a given concept code,
		// and its corresponding concept/Literal
		pss.setCommandText(commandTextUsedByQueryRelationofCode());
		pss.setLiteral("conceptCode", conceptCode);

		Query query = QueryFactory.create(pss.toString());// create a query
															// statement with
															// given query
															// string
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();

		// Assemble the results to show
		SemanticRelationModelView relationModelView = new SemanticRelationModelView();
		List<RdfModelViewUnit> modelViewUnits = new ArrayList<RdfModelViewUnit>();
		Map<String, RDFNode> rdfNodeMap = new HashMap<String, RDFNode>();

		while (rs.hasNext()) {

			RdfModelViewUnit modelUnit = new RdfModelViewUnit();
			modelUnit.setSubject(conceptCode);
			analyzeQueryResults(rs, modelUnit, rdfNodeMap, modelViewUnits, conceptCode, "");
			/*
			 * QuerySolution soln = rs.nextSolution(); //Analyze its predicate
			 * rdfNodeMap.put(predicate, soln.get("p"));//add predicate
			 * rdfNodeMap.put(object, soln.get("o"));//add object analyzeRdfNode
			 * (rdfNodeMap, modelUnit); modelViewUnits.add(modelUnit);
			 */
		}

		relationModelView.setRdfViews(modelViewUnits);

		return relationModelView;

	}

	/**
	 * Make a statistic about the specific relation of a given terminology
	 * 
	 * @param terminology
	 * @param relation
	 */
	public RelationStatisticsModelView getStatisticsOfRelation(String terminology, String relation) {

		List<ConceptCodeModelView> conceptCodeModels = new ArrayList<ConceptCodeModelView>();
		RelationStatisticsModelView relationStatisticsModelView = new RelationStatisticsModelView();
		/*
		 * CodeSystem codeSystem = new CodeSystem();
		 * codeSystem.setCode(terminology);
		 */
		List<CodeSystem> codeSystems = codeSystemRep.findCodeSystemsByName(terminology);
		conceptCodeService.queryDB2GetConceptCodes(codeSystems.get(0), conceptCodeModels);
		int numOfConceptCode = 0;// Record the number of total concept code
		// Record the number of concept codes which do not have the specified relation
		int numOfSpecifiedRelation = 0;
		// Record the concepts that have no relation with other concepts
		List<String> conceptCodesWithoutRelation = new ArrayList<String>();
		// Record the concepts that have no specified relation with other concepts
		List<String> conceptCodesWithoutSpecifiedRelation = new ArrayList<String>();

		List<SemanticRelationModelView> relationModels = new ArrayList<SemanticRelationModelView>();
		if (conceptCodeModels.size() > 0) {
			for (ConceptCodeModelView conceptCodetModel : conceptCodeModels) {
				List<ConceptCode> conceptCodes = conceptCodetModel.getConceptCodes();
				SemanticRelationModelView relationModel = new SemanticRelationModelView();
				numOfConceptCode = conceptCodes.size();
				for (ConceptCode conceptCode : conceptCodes) {
					relationModel = queryRelationOfConceptCode(terminology, conceptCode.getCode());
					// Record the concept that has no relation with other
					// concepts
					if (relationModel.getRdfViews().size() == 0) {
						conceptCodesWithoutRelation.add(conceptCode.getCode());
						System.out.println(conceptCode.getCode());
					}
					relationModels.add(relationModel);
				}

			}
		}

		// iterate relation models to check whether the concept has the given
		// relation
		for (SemanticRelationModelView relationModel : relationModels) {
			List<RdfModelViewUnit> rdfModelViewUnits = relationModel.getRdfViews();
			boolean flag = false;// whether the given code has the specified
									// relation
			// String irrelevantCode = "";
			for (RdfModelViewUnit rdfModelViewUnit : rdfModelViewUnits) {
				if (rdfModelViewUnit.getPredicate().toLowerCase().indexOf(relation.toLowerCase()) >= 0) {
					numOfSpecifiedRelation++;
					flag = true;
					break;
				}
			}
			if (!flag && rdfModelViewUnits.size() > 0) {
				conceptCodesWithoutSpecifiedRelation.add(rdfModelViewUnits.get(0).getSubject());
				System.out.println(rdfModelViewUnits.get(0).getSubject());
			}
		}

		relationStatisticsModelView.setNumOfConceptCode(numOfConceptCode);
		relationStatisticsModelView.setNumOfSpecifiedRelation(numOfSpecifiedRelation);
		relationStatisticsModelView.setConceptCodesWithoutRelation(conceptCodesWithoutRelation);
		relationStatisticsModelView.setConceptCodesWithoutSpecifiedRelation(conceptCodesWithoutSpecifiedRelation);
		relationStatisticsModelView.setTerminology(terminology);
		// System.out.println(stringCodes);
		System.out
				.println("Concept Code Number: " + numOfConceptCode + " / Relation Number: " + numOfSpecifiedRelation);

		return relationStatisticsModelView;
	}

	/**
	 * Query semantic relation by giving two codes and terminology
	 * 
	 * @param terminology
	 * @param sourceCode
	 * @param targetCode
	 * @return
	 */
	public SemanticRelationModelView queryRelationBetween2Concepts(String terminology, String sourceCode,
			String targetCode) {

		// Get RDF model by requested terminology
		Model model = loadRdfModel.getModelMap().get(terminology);
		// Parameterize SPARQL query
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		// Set prefix
		setNameSpace(pss);
		// create query command - Query all predicates of a given concept code,
		// and its corresponding concept/Literal
		String commandText = "SELECT DISTINCT ?s ?p ?o WHERE { " + " ?s ?p ?o ." + " ?s skos:notation ?sourceCode ."
				+ " ?o skos:notation ?targetCode ." + "}";
		pss.setCommandText(commandText);
		pss.setLiteral("sourceCode", sourceCode);// assign value to variable
		pss.setLiteral("targetCode", targetCode);// assign value to variable
		System.out.println(pss.toString());

		Query query = QueryFactory.create(pss.toString());// create a query
															// statement with
															// given query
															// string
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();

		SemanticRelationModelView relationModelView = new SemanticRelationModelView();
		List<RdfModelViewUnit> modelViewUnits = new ArrayList<RdfModelViewUnit>();
		Map<String, RDFNode> rdfNodeMap = new HashMap<String, RDFNode>();

		while (rs.hasNext()) {
			RdfModelViewUnit modelUnit = new RdfModelViewUnit();
			modelUnit.setSubject(sourceCode);
			modelUnit.setObject(targetCode);
			analyzeQueryResults(rs, modelUnit, rdfNodeMap, modelViewUnits, sourceCode, targetCode);
		}

		relationModelView.setRdfViews(modelViewUnits);

		return relationModelView;

	}

	/**
	 * Analyze Query result
	 * 
	 * @param rs
	 * @param modelUnit
	 * @param rdfNodeMap
	 * @param modelViewUnits
	 */
	public void analyzeQueryResults(ResultSet rs, RdfModelViewUnit modelUnit, Map<String, RDFNode> rdfNodeMap,
			List<RdfModelViewUnit> modelViewUnits, String sourceCode, String targetCode) {

		QuerySolution soln = rs.nextSolution();

		if (sourceCode.isEmpty() && !targetCode.isEmpty()) {
			rdfNodeMap.put(OBJECT, soln.get("s"));// add subject
			rdfNodeMap.put(PREDICATE, soln.get("p"));// add predicate
		}
		if (!sourceCode.isEmpty() && targetCode.isEmpty()) {
			rdfNodeMap.put(PREDICATE, soln.get("p"));// add predicate
			rdfNodeMap.put(OBJECT, soln.get("o"));// add object
		}

		if (!sourceCode.isEmpty() && !targetCode.isEmpty()) {
			rdfNodeMap.put(PREDICATE, soln.get("p"));// add predicate
		}
		analyzeRdfNode(rdfNodeMap, modelUnit);
		modelViewUnits.add(modelUnit);
	}

	/**
	 * Analyze RDF Node of query results
	 * 
	 * @param rdfNodeMap
	 * @param modelViewUnits
	 */
	public void analyzeRdfNode(Map<String, RDFNode> rdfNodeMap, RdfModelViewUnit modelViewUnit) {

		// Loop Map with forEach + lambda expression, only available in Java 8
		rdfNodeMap.forEach((key, value) -> {
			if (PREDICATE.equalsIgnoreCase(key)) {
				if (value.isLiteral()) {
					modelViewUnit.setPredicate(value.asLiteral().getLexicalForm());
				} else if (value.isResource()) {
					modelViewUnit.setPredicate(value.asResource().getLocalName());
				}

			}
			if (OBJECT.equalsIgnoreCase(key)) {
				if (value.isLiteral()) {
					modelViewUnit.setObject(value.asLiteral().getLexicalForm());
				} else if (value.isResource()) {
					// Cannot get name of resource via getLocalName()
					modelViewUnit.setObject(value.asResource().getURI());
				}
			}
		});
	}

	/**
	 * Set Namespace for SPARQL query
	 * 
	 * @param pss
	 */
	public void setNameSpace(ParameterizedSparqlString pss) {
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		nameSpaceMap.put("skos", "http://www.w3.org/2004/02/skos/core#");
		nameSpaceMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		nameSpaceMap.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		nameSpaceMap.put("RxNorm", "http://purl.bioontology.org/ontology/RXNORM/");
		nameSpaceMap.put("umls", "http://bioportal.bioontology.org/ontologies/umls/");
		nameSpaceMap.put("loinc", "http://purl.bioontology.org/ontology/LNC/");
		pss.setNsPrefixes(nameSpaceMap);
	}

	public String commandTextUsedByQueryRelationofCode() {
		String commandText = "SELECT DISTINCT ?p ?o WHERE { " + " ?s skos:notation ?conceptCode ." + " ?s ?p ?o ."
				+ "}";
		return commandText;
	}

	public static void testCommandText() {
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		// Set prefix
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		nameSpaceMap.put("skos", "http://www.w3.org/2004/02/skos/core#");
		nameSpaceMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		nameSpaceMap.put("RxNorm", "http://purl.bioontology.org/ontology/RXNORM/");
		pss.setNsPrefixes(nameSpaceMap);

		// create query command - Query all predicates of a given concept code,
		// and its corresponding concept/Literal
		String commandText = "SELECT DISTINCT ?s ?p ?o WHERE { " + " ?s ?p ?o ." + "}";
		pss.setCommandText(commandText);
		String iri = pss.getNsPrefixMap().get("RxNorm");
		pss.setIri("s", iri + "concept");
		System.out.println(pss.toString());
	}

	public static void main(String[] args) {
		// queryRelationOfConceptCode ("RxNorm", "104781");
		String str = "sdff/fd/123";
		String[] strs = str.split("\\/");
		testCommandText();
	}

	public Model getLoincPartGraph() {

		Model model = loadRdfModel.getModelMap().get("LOINC");

		// Parameterize SPARQL query
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		// Set prefix
		setNameSpace(pss);
		// create query command - Query all predicates of a given concept code,
		// and its corresponding concept/Literal
		String commandText = "CONSTRUCT {?s ?p ?o}" + " WHERE {" + "  ?mid (<>|!<>)* loinc:MTHU000999 ."
				+ " ?o ?p ?mid ." + " ?s ?p ?o ." + "}";
		pss.setCommandText(commandText);
		// pss.setLiteral("concept0", sourceConcept);

		Query query = QueryFactory.create(pss.toString());// create a query
															// statement with
															// given query
															// string
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		Model subModel = qexec.execConstruct();
		subModel.write(System.out, "TURTLE");// output Model in ttl
		return subModel;
	}
}
