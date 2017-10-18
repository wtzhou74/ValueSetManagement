package valueset.model.wsModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Response of concept of specified TTY through RESTful API
 * @author ZHOU WENTAO
 *
 */
@JsonIgnoreProperties(
		ignoreUnknown = true)
public class RxnormDataWithSpecifiedTtyResponse {

	private RelatedGroup relatedGroup;

	public RelatedGroup getRelatedGroup() {
		return relatedGroup;
	}

	public void setRelatedGroup(RelatedGroup relatedGroup) {
		this.relatedGroup = relatedGroup;
	}
	
	
}
