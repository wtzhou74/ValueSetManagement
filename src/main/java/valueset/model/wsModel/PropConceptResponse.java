package valueset.model.wsModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
