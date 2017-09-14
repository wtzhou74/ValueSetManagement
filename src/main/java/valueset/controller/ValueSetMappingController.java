package valueset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		String sourceCode = request.getParameter("sourceCode");
		String targetCode = request.getParameter("targetCode");
		return "index";
	}
}
