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

import valueset.model.modelView.SemanticRelationModelView;
import valueset.rdfModelManage.LoadRdfModel;
import valueset.rdfModelManage.RdfModelViewUnit;

@Service
public class ValueSetMappingService {

	private final String predicate = "PREDICATE";
	private final String object = "OBJECT";
	
	@Autowired
	private LoadRdfModel loadRdfModel;
	
	public SemanticRelationModelView queryRelationOfConceptCode (String terminology, String conceptCode) {
		
		Model model = loadRdfModel.getModelMap().get(terminology);
		
		//For unit test
		//Model model = RDFDataMgr.loadModel("terminologies/RXNORM.ttl");
		
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		//Set prefix
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		nameSpaceMap.put("skos", "http://www.w3.org/2004/02/skos/core#");
		nameSpaceMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		nameSpaceMap.put("RxNorm", "http://purl.bioontology.org/ontology/RXNORM/");
		pss.setNsPrefixes(nameSpaceMap);
		// create query command - Query all predicates of a given concept code, and its corresponding concept/Literal
		String commandText = "SELECT DISTINCT ?p ?o WHERE { "
				+ " ?s skos:notation ?conceptCode ."
				+ " ?s ?p ?o ."
				+ "}";
		pss.setCommandText(commandText);
		pss.setLiteral("conceptCode", conceptCode);
		System.out.println(pss.toString());
		
		Query query = QueryFactory.create(pss.toString());//create a query statement with given query string
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();
		
		//Assemble the results to show
		SemanticRelationModelView relationModelView = new SemanticRelationModelView();
		List<RdfModelViewUnit> modelViewUnits  = new ArrayList<RdfModelViewUnit>();
		Map<String, RDFNode> rdfNodeMap = new HashMap<String, RDFNode>();
		
		while(rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			
			RdfModelViewUnit modelUnit = new RdfModelViewUnit ();
			modelUnit.setSubject(conceptCode);
			//Analyze its predicate
			rdfNodeMap.put(predicate, soln.get("p"));//add predicate
			rdfNodeMap.put(object, soln.get("o"));//add object
			analyzeRdfNode (rdfNodeMap, modelUnit, conceptCode);
			modelViewUnits.add(modelUnit);
		}
		
		relationModelView.setRdfViews(modelViewUnits);
		
		return relationModelView;
			
	}
	
	/**
	 * Analyze RDF Node of query results
	 * @param rdfNodeMap
	 * @param modelViewUnits
	 * @param conceptCode
	 */
	public void analyzeRdfNode (Map<String, RDFNode> rdfNodeMap, RdfModelViewUnit modelViewUnit, String conceptCode) {
		
		//Loop Map with forEach + lambda expression, only available in Java 8
		rdfNodeMap.forEach((key,value)->{
			if(predicate.equalsIgnoreCase(key)){								
				if (value.isLiteral()) {
					modelViewUnit.setPredicate(value.asLiteral().getLexicalForm());
				} else if (value.isResource()) {
					modelViewUnit.setPredicate(value.asResource().getLocalName());
				}
				
			}
			if (object.equalsIgnoreCase(key)) {
				if (value.isLiteral()) {
					modelViewUnit.setObject(value.asLiteral().getLexicalForm());
				} else if (value.isResource()) {
					//Cannot get name of resource via getLocalName()
					modelViewUnit.setObject(value.asResource().getURI());
				}
			}
		});
	}
	public static void main(String[] args) {
		//queryRelationOfConceptCode ("RxNorm", "104781");
	}
	
}
