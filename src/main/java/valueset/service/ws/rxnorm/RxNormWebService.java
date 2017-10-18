package valueset.service.ws.rxnorm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import valueset.dao.CodeSystemRepository;
import valueset.model.dbModel.CodeSystem;
import valueset.model.dbModel.ConceptCode;
import valueset.model.modelView.ConceptCodeModelView;
import valueset.model.wsModel.PropConcept;
import valueset.model.wsModel.PropConceptGroup;
import valueset.model.wsModel.PropConceptResponse;
import valueset.model.wsModel.RxnormDataWithSpecifiedTtyResponse;
import valueset.service.ConceptCodeService;
import valueset.utils.ConstantUtil;

@Service
public class RxNormWebService implements IRxNormWebService{

    /**
     * The RestTemplate used to retrieve data from the remote Quote API.
     */
    private final RestTemplate restTemplate;
    //private final String TTY = "TTY";
    
    @Autowired
	private ConceptCodeService conceptCodeService;
    private final CodeSystemRepository codeSystemRep;
	/**
	 * Construct a RxNormWebServiceBean with RestTemplateBuilder used to initiate restTemplate 
	 * used by this business service 
	 * @param restTemplateBuilder
	 */
	public RxNormWebService(RestTemplateBuilder restTemplateBuilder,
			CodeSystemRepository codeSystemRep) {
		this.restTemplate = restTemplateBuilder.build();
		this.codeSystemRep = codeSystemRep; 
	}

	/**
	 * calculate the number of concepts with same TTY by giving terminology
	 * @author ZHOU WENTAO
	 *
	 */
	public Map<String, Integer> calNumOfConceptsWithSameTTY(String terminology) {
		
		List<ConceptCodeModelView> conceptCodeModels = new ArrayList<ConceptCodeModelView>();
		List<CodeSystem> codeSystems = codeSystemRep.findCodeSystemsByName(terminology);
		List<ConceptCode> conceptCodes = new ArrayList<ConceptCode>();//record the concept codes in VS
		Map<String, String> conceptTty= new HashMap<String, String>();//record the value of TTY of each concept
		Map<String, Integer> ttyNumber = new HashMap<String, Integer>();//record the number of each TTY
		
		conceptCodeService.queryDB2GetConceptCodes(codeSystems.get(0), conceptCodeModels);
		if (conceptCodeModels.size() > 0) {
			//Get all concept codes in VS
			for (ConceptCodeModelView conceptCodetModel : conceptCodeModels) {
				conceptCodes = conceptCodetModel.getConceptCodes();
			}
			
			for (ConceptCode concept : conceptCodes) {
				//Iterate each concept code and get its properties
				PropConceptResponse propConceptReponse = getAllProperties(concept.getCode());
				if (propConceptReponse != null &&
						propConceptReponse.getPropConceptGroup() != null) {
					//record the value of TTY
					for (PropConcept propConcept : propConceptReponse.getPropConceptGroup().getPropConcept()) {
						if (ConstantUtil.PROP_NAME_TTY.equalsIgnoreCase(propConcept.getPropName())) {
							conceptTty.put(concept.getCode(), propConcept.getPropValue());
						}
					}
				}
			}
		}
		//calculate the number of concepts with same TTY
		conceptTty.forEach((Key, value) -> {
			if (ttyNumber.containsKey(value)) {
				ttyNumber.put(value, ttyNumber.get(value) + 1);
			} else {
				ttyNumber.put(value, 1);
			}
		});
		
		return ttyNumber;
	}
	
	/**
	 * Find all properties of a given CUI
	 * @param cui
	 * @return
	 */
	public PropConceptResponse getAllProperties (String cui) {
		
		PropConceptResponse propConceptReponse = new PropConceptResponse();
		propConceptReponse = 
				this.restTemplate.getForObject("https://rxnav.nlm.nih.gov/REST/rxcui/{rxcui}/allProperties.json?prop=all", PropConceptResponse.class, cui);
		
		return propConceptReponse;
	}
	
	/**
	 * Find concept details with specified TTY and cui
	 * */
	public RxnormDataWithSpecifiedTtyResponse getConceptWithSepcifiedTTYandCUI (String cui, String values) {
		RxnormDataWithSpecifiedTtyResponse conceptTTYResponse = new RxnormDataWithSpecifiedTtyResponse();
		conceptTTYResponse = 
				this.restTemplate.getForObject("https://rxnav.nlm.nih.gov/REST/rxcui/{cui}/related.json?tty={values}", RxnormDataWithSpecifiedTtyResponse.class,cui,values);
		return conceptTTYResponse;
	}
	/**
	 * Find RxTerm information for a specified RxNorm concept
	 * @param cui
	 */
	public void getRxTermInfo4GivenConcept (String cui) {
		
	}
	
}
