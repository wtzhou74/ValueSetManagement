package valueset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import valueset.service.ValueSetService;

/**
 * Controller response requests related value set
 * @author ZHOU WENTAO
 *
 */
@Controller
public class ValueSetController {

	@Autowired
	private ValueSetService valueSetService;
	
	@GetMapping("/query-valueset-sensitivecategory")
	public String findAllValueSet(HttpServletRequest request) {
		request.setAttribute("valueSetModelViews", valueSetService.findValueSetAndCategory());
		request.setAttribute("mode", "ALL_VALUE_SET_CATEGORY");
		return "index";
	}
	
	@GetMapping("/all-sensitive-category")
	public String findAllSensitiveCategoy(HttpServletRequest request) {
		request.setAttribute("sensitiveCategories", valueSetService.findAllSensiveCategory());
		request.setAttribute("mode", "ALL_SENSITIVE_CATEGORY");
		return "index";
	}
	
	@GetMapping("/show-valuesetbyid")
	public String findValueSetByCategoryID(@RequestParam Long categoryId, HttpServletRequest request) {
		request.setAttribute("valueSetModelViews", valueSetService.findValueSetByCategoryID(categoryId));
		request.setAttribute("mode", "ALL_VALUE_SET_CATEGORY");
		return "index";
	}
	
	@GetMapping("/show-valuesetby-conceptcodeid")
	public String findValueSetByConceptCodeId (@RequestParam Long conceptCodeId, HttpServletRequest request) {
		request.setAttribute("valueSetModelViews", valueSetService.findValueSetAndCategoryByConceptCodeID(conceptCodeId));
		request.setAttribute("mode", "ALL_VALUE_SET_CATEGORY");
		return "index";
	}
}
