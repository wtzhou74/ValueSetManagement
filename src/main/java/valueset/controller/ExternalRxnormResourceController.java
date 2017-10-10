package valueset.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import valueset.service.ws.rxnorm.IRxNormWebService;

/**
 * Access extra RxNorm resource from UMLS-RxNorm through RESTful 
 * @author ZHOU WENTAO
 *
 */
@Controller
public class ExternalRxnormResourceController {

	@Autowired
	private IRxNormWebService rxNormWebService; 
	
	@PostMapping("/get-termType")
	public String showStatisticOfRelation (HttpServletRequest request) {
		String terminology = "RxNorm";
		Map<String, Integer> ttyNumber = rxNormWebService.calNumOfConceptsWithSameTTY(terminology);
		ttyNumber.forEach((key, value) -> {
			System.out.println("TTY: " + key + " NUMBER: " + value);
		});
		request.setAttribute("ttyNumber", ttyNumber);
		request.setAttribute("model", "SHOW_TERM_TYPE");
		return "index";
	}
}
