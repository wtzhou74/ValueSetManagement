package valueset.model.wsModel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
		ignoreUnknown = true)
public class ConceptGroup {

	private String tty = "";
	private List<ConceptProperties> conceptProperties = new ArrayList<ConceptProperties>();
	
	public String getTty() {
		return tty;
	}
	public void setTty(String tty) {
		this.tty = tty;
	}
	public List<ConceptProperties> getConceptProperties() {
		return conceptProperties;
	}
	public void setConceptProperties(List<ConceptProperties> conceptProperties) {
		this.conceptProperties = conceptProperties;
	}
	
	
}
