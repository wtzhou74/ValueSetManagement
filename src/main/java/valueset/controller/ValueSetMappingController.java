package valueset.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import valueset.model.modelView.ConceptMappingResultModelView;
import valueset.model.modelView.RelationStatisticsModelView;
import valueset.model.modelView.SemanticRelationModelView;
import valueset.service.ConceptMappingService;
import valueset.service.JenaBasedSemanticService;
import valueset.service.ws.rxnorm.IRxNormWebService;

@Controller
public class ValueSetMappingController {

	@Autowired
	private JenaBasedSemanticService valueSetMappingService;
	@Autowired
	private IRxNormWebService rxNormWebService;
	@Autowired
	private ConceptMappingService conceptMappinService;
	
	@GetMapping("/semanticRelations")
	public String getDirectRelationByCodeAndTermnology (@RequestParam String terminology, String conceptCode,
			HttpServletRequest request) {
		SemanticRelationModelView modelView = valueSetMappingService.queryRelationOfConceptCode(terminology, conceptCode);
		request.setAttribute("rdfModelView", modelView);
		request.setAttribute("model", "SEMANTIC_RELATION_VIEW");
		return "index";
	}
	
	@GetMapping("/query-semanticrelation")
	public String semanticRelationQuery (HttpServletRequest request) {
		request.setAttribute("model", "SEMANTIC_RELATION_QUERY");
		return "index";
	}
	
	@PostMapping("/show-query-semantic-relation")
	public String querySemanticRelationWithCodes (HttpServletRequest request) {
		String terminology = request.getParameter("terminology");
		String sourceCode = request.getParameter("sourceCode");
		String targetCode = request.getParameter("targetCode");
		SemanticRelationModelView modelView = valueSetMappingService.queryRelationBetween2Concepts(terminology, sourceCode, targetCode);
		request.setAttribute("rdfModelView", modelView);
		request.setAttribute("model", "SEMANTIC_RELATION_VIEW");
		return "index";
	}
	
	@GetMapping("/make-statistics-of-relation")
	public String makeStatisticsOfRelation (HttpServletRequest request) {
		request.setAttribute("model", "RELATION_STATISTICS");
		return "index";
	}
	
	@PostMapping("/show-statisticsofrelation")
	public String showStatisticOfRelation (HttpServletRequest request) {
		String terminology = request.getParameter("terminology");
		String relation = request.getParameter("relation");
		RelationStatisticsModelView statisticsModelView = valueSetMappingService.getStatisticsOfRelation(terminology, relation);
		request.setAttribute("statisticsView", statisticsModelView);
		request.setAttribute("model", "SHOW_STATISTICS_RESULT");
		return "index";
	}
	
	@GetMapping("/get-related-concept")
	public String getRelatedConcepts (HttpServletRequest request) {
		request.setAttribute("model", "CONCEPT_MAPPINGS");
		return "index";
	}
	
	@PostMapping("/showRelatedConcepts")
	public String showRelatedConcepts (HttpServletRequest request) {
		String terminology = request.getParameter("terminology");
		String conceptCode = request.getParameter("conceptCode");
		SemanticRelationModelView modelView = valueSetMappingService.queryRelationOfConceptCode(terminology, conceptCode);
		request.setAttribute("rdfModelView", modelView);
		request.setAttribute("model", "SEMANTIC_RELATION_VIEW");
		return "index";
	}
	
	//CONCEPT_MAPPING_REQUEST
	@GetMapping("/query-mapped-concept")
	public String queryMappedConcept(HttpServletRequest request) {
		//conceptCodeService.findSensitiveOfSpecifiedConcept("152318");
		request.setAttribute("model", "CONCEPT_MAPPING_REQUEST");
		//conceptMappinService.findMappedConceptInVS("1040032");
		return "index";
	}
	
	@PostMapping("/showcConceptMappingResults")
	public String showConceptMappingResults (HttpServletRequest request) {
		String conceptCode = request.getParameter("conceptCode");
		
		List<ConceptMappingResultModelView> conceptMappingResultViews = conceptMappinService.findMappedConceptInVS(conceptCode);
		request.setAttribute("conceptMappingView", conceptMappingResultViews);
		request.setAttribute("model", "CONCEPT_MAPPING_RESULTS");
		return "index";
	}
	
	@GetMapping("/query-term_type")
	public String queryTermType(HttpServletRequest request) {
		//conceptCodeService.findSensitiveOfSpecifiedConcept("152318");
		request.setAttribute("model", "QUERY_TERM_TYPE");
		//conceptMappinService.findMappedConceptInVS("1040032");
		return "index";
	}
	@PostMapping("/showTermType")
	public String showTermType (HttpServletRequest request) {
		String conceptCode = request.getParameter("conceptCode");
		
		String termType = conceptMappinService.getTermType4SpecifiedConcept(conceptCode);
		request.setAttribute("termType", termType);
		request.setAttribute("model", "TERM_TYPE");
		return "index";
	}
	
}
