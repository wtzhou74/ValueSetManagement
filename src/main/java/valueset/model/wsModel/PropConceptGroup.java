package valueset.model.wsModel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The properties for a specified RxNorm Concept
 * @author ZHOU WENTAO
 *
 */
@JsonIgnoreProperties(
		ignoreUnknown = true)
public class PropConceptGroup {
	
	private List<PropConcept> propConcept;

	public PropConceptGroup() {
	}

	public List<PropConcept> getPropConcept() {
		return propConcept;
	}

	public void setPropConcept(List<PropConcept> propConcept) {
		if (propConcept == null) {
			this.propConcept = new ArrayList<PropConcept>();
		}
		this.propConcept = propConcept;
	}



}
