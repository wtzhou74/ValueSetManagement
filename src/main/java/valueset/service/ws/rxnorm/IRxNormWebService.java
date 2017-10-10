package valueset.service.ws.rxnorm;

import java.util.Map;

import valueset.model.wsModel.PropConceptGroup;
import valueset.model.wsModel.PropConceptResponse;

/**
 * This RxNorm web service interface defines all business behaviors to analyze concepts in RxNorm
 * @author ZHOU WENTAO
 *
 */
public interface IRxNormWebService {

	//calculate number of concepts with same TTY
	public Map<String, Integer> calNumOfConceptsWithSameTTY(String terminology);
	
	
}
