package valueset.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import valueset.model.dbModel.CodeSystem;
import valueset.model.modelView.ConceptCodeModelView;
import valueset.service.CodeSystemService;
import valueset.service.ConceptCodeService;
import valueset.service.ConceptMappingService;

/**
 * Controller response the requests related to concept code
 * @author ZHOU WENTAO
 *
 */
@Controller
public class ConceptCodeController {

	@Autowired
	private CodeSystemService codeSystemService;
	@Autowired
	private ConceptCodeService conceptCodeService;
	@Autowired
	private ConceptMappingService conceptMappinService;
	
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		request.setAttribute("mode", "MODE_HOME");
		return "index";
	}
	
	@GetMapping("/all-codesystems")
	public String getAllCodeSystem(HttpServletRequest request) {
		List<CodeSystem> codeSystems = codeSystemService.findAllCodeSystem();
		request.setAttribute("codeSystems", codeSystems);
		request.setAttribute("mode", "ALL_CODE_SYSTEM");
		return "index";
	}
	
	@GetMapping("/codeSystemVersion")
	public String getCodeSystemVersion(@RequestParam Long codeSystemId, HttpServletRequest request) {
		List<String> codeSystemVersions = codeSystemService.findCodeSystemVersion(codeSystemId);
		request.setAttribute("codeSystemVersions", codeSystemVersions);
		request.setAttribute("mode", "MODE_CODE_SYSTEM_VERSION");
		return "index";
		
	}
	
	@GetMapping("/query-conceptcode")
	public String findConceptCode(HttpServletRequest request) {
		request.setAttribute("mode", "CONCEPT_CODE_QUERY");
		return "index";
	}
	@PostMapping("/all-conceptcode")
	public String getConceptCodeByCodeSystemName(HttpServletRequest request) {
		//@ModelAttribute @RequestParam("codeSystemName") String codeSystem， and it refers to input.id
		//Achieve the type of request parameter
		if (!request.getParameterNames().hasMoreElements()) {
			request.setAttribute("mode", "MODE_HOME");
			return "index";
		}
		//For this request, there is only request parameter, and each parameter has one and only one corresponding value
		String requestParam = request.getParameterNames().nextElement();
		List<ConceptCodeModelView> conceptCodeModels = conceptCodeService.findConceptCodeWithTerminology(requestParam, (request.getParameterValues(requestParam))[0]);
		request.setAttribute("conceptCodeModels", conceptCodeModels);
		request.setAttribute("mode", "CONCEPT_CODE_VIA_NAME");
		return "index";
		
	}
	
	@GetMapping("/show-conceptCode")
	public String showConceptCode(@ModelAttribute List<ConceptCodeModelView> conceptCodeModels, HttpServletRequest request) {
		request.setAttribute("conceptCodeModels", conceptCodeModels);
		request.setAttribute("mode", "SHOW_CONCEPT_CODE_MODEL");
		return "index";
	}
	
	@GetMapping("/test-conceptCode")
	public String test() {
		//conceptCodeService.findSensitiveOfSpecifiedConcept("152318");
		conceptMappinService.findMappedConceptInVS("1040032");
		return "index";
	}
}
