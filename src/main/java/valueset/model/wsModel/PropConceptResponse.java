package valueset.model.wsModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Response through allProperties of RESTful API resource
 * @author ZHOU WENTAO
 *
 */
@JsonIgnoreProperties(
		ignoreUnknown = true)
public class PropConceptResponse {

	private PropConceptGroup propConceptGroup;
	
	
	public PropConceptResponse() {
	}


	public PropConceptGroup getPropConceptGroup() {
		return propConceptGroup;
	}


	public void setPropConceptGroup(PropConceptGroup propConceptGroup) {
		this.propConceptGroup = propConceptGroup;
	}
	
	
}
