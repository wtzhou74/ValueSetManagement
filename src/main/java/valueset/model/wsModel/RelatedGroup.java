package valueset.model.wsModel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
		ignoreUnknown = true)
public class RelatedGroup {

	private String rxcui = "";
	private List<String> termType = new ArrayList<String>();
	private List<ConceptGroup> conceptGroup = new ArrayList<ConceptGroup>();
	public String getRxcui() {
		return rxcui;
	}
	public void setRxcui(String rxcui) {
		this.rxcui = rxcui;
	}
	
	public List<String> getTermType() {
		return termType;
	}
	public void setTermType(List<String> termType) {
		this.termType = termType;
	}
	public List<ConceptGroup> getConceptGroup() {
		return conceptGroup;
	}
	public void setConceptGroup(List<ConceptGroup> conceptGroup) {
		this.conceptGroup = conceptGroup;
	}
	
	
	
	
	
}
