package valueset.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.dao.ConceptCodeRepository;
import valueset.model.dbModel.ConceptCode;

@Service
@Transactional
public class LoincTest {

	private ConceptCodeRepository conceptCodeRepository;
	@Autowired
	private JenaBasedSemanticService jenaBasedSemanticService;

	public LoincTest(ConceptCodeRepository conceptCodeRepository) {
		this.conceptCodeRepository = conceptCodeRepository;
	}
	@Autowired
	private LoincCodesTest loincCodesTest;
	
	public void testConceptCodes () {
		
		loincCodesTest.getLoincNumber();
		List<ConceptCode> iterator = conceptCodeRepository.findConceptByCodeSystem(Long.valueOf(23));
		List<ConceptCode> temp = new ArrayList<ConceptCode>();
		List<ConceptCode> nonLPcode = new ArrayList<ConceptCode>();
		for (ConceptCode conceptCode : iterator) {
			if (conceptCode.getCode().startsWith("LP")) {
				temp.add(conceptCode);
			} else {
				nonLPcode.add(conceptCode);
			}
		}
		
		List<List<String>> tests = new ArrayList<List<String>>();
		List<String> nonComponentConcept = new ArrayList<String>();
		List<String> componentConcept = new ArrayList<String>();
		int i = 0;
		for(ConceptCode conceptCode : temp) {
			//ConceptCode conceptCode = iterator.next();
			List<String> notLeafNodes = getComponentStatement (conceptCode.getCode());
			//record non-component
			if (notLeafNodes.size() == 0) {
				i++;
				nonComponentConcept.add(conceptCode.getCode());
			} else {
				componentConcept.add(conceptCode.getCode());
			}
			
			tests.add(notLeafNodes);
		}
		
		System.out.println("Total Number of non-component: " + i);
		
		//Find subclass of component concept
		List<String> testNonComponent = new ArrayList<String>();
		for (String component : componentConcept) {
			List<String> subClassOfComponent = getSubClassStatement(component);
			testNonComponent.addAll(subClassOfComponent);
		}
		List<String> specialCode = new ArrayList<String>();
		for (String nonComponent : nonComponentConcept) {
			if(!testNonComponent.contains(nonComponent)) {
				specialCode.add(nonComponent);
			}
		}
		
		return;
	}
	
	public List<String> getChildrenStatement (String cui) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT ?notation WHERE { ");
		//buffer.append("SELECT ?s COUNT(?mid) WHERE { ");
		buffer.append("?s skos:notation ?concept0 . ");
		buffer.append("?subClass rdfs:subClassOf* ?s . ");
		//buffer.append("?s rdfs:subClassOf ?mid .");
		buffer.append("?subClass skos:notation ?notation . ");
		//buffer.append("GROUP BY ?s .");
		buffer.append(" }");
		//Test cui=F16.24
		List<String> parentNodes = new ArrayList<String>();
		parentNodes = jenaBasedSemanticService.queryTargetConeptViaRelation("LOINC", cui, buffer.toString());
		
		return parentNodes;
	}
	
	public List<String> getComponentStatement (String cui) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT ?notation WHERE { ");
		//buffer.append("SELECT ?s COUNT(?mid) WHERE { ");
		buffer.append("?s skos:notation ?concept0 . ");
		buffer.append("?s loinc:component_of ?loinc . ");
		//buffer.append("?s rdfs:subClassOf ?mid .");
		buffer.append("?loinc skos:notation ?notation . ");
		//buffer.append("GROUP BY ?s .");
		buffer.append(" }");
		//Test cui=F16.24
		List<String> parentNodes = new ArrayList<String>();
		parentNodes = jenaBasedSemanticService.queryTargetConeptViaRelation("LOINC", cui, buffer.toString());
		
		return parentNodes;
	}
	
	public List<String> getStatement (String cui) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT ?notation WHERE { ");
		//buffer.append("SELECT ?s COUNT(?mid) WHERE { ");
		buffer.append("?s skos:notation ?concept0 . ");
		buffer.append("?mid rdfs:subClassOf ?s . ");
		//buffer.append("?s rdfs:subClassOf ?mid .");
		buffer.append("?mid skos:notation ?notation . ");
		//buffer.append("GROUP BY ?s .");
		buffer.append(" }");
		//Test cui=F16.24
		List<String> parentNodes = new ArrayList<String>();
		parentNodes = jenaBasedSemanticService.queryTargetConeptViaRelation("LOINC", cui, buffer.toString());
		
		return parentNodes;
	}
	
	public List<String> getSubClassStatement (String cui) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT ?notation WHERE { ");
		//buffer.append("SELECT ?s COUNT(?mid) WHERE { ");
		buffer.append("?s skos:notation ?concept0 . ");
		buffer.append("?subClass rdfs:subClassOf ?s . ");
		//buffer.append("?s rdfs:subClassOf ?mid .");
		buffer.append("?subClass skos:notation ?notation . ");
		//buffer.append("GROUP BY ?s .");
		buffer.append(" }");
		//Test cui=F16.24
		List<String> parentNodes = new ArrayList<String>();
		parentNodes = jenaBasedSemanticService.queryTargetConeptViaRelation("LOINC", cui, buffer.toString());
		
		return parentNodes;
	}
}
