package valueset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import valueset.model.modelView.RelationStatisticsModelView;
import valueset.model.modelView.SemanticRelationModelView;
import valueset.service.ValueSetMappingService;

@Controller
public class ValueSetMappingController {

	@Autowired
	private ValueSetMappingService valueSetMappingService;
	
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
}
