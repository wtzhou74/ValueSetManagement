package valueset.model.wsModel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
		ignoreUnknown = true)
public class PropConcept {

	private String propName;
	private String propValue;
	private String propCategory;
	
	
	public PropConcept() {
	}
	
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public String getPropCategory() {
		return propCategory;
	}
	public void setPropCategory(String propCategory) {
		this.propCategory = propCategory;
	}
	
	
	
}
