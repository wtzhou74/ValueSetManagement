package valueset.model.modelView;

import java.util.ArrayList;
import java.util.List;

public class ConceptMappingResultModelView {

	private String sourceConcept;
	private String targetConcept;
	private String relations = "";
	private List<String> sensitiveCategories = new ArrayList<String>();
	
	
	public String getSourceConcept() {
		return sourceConcept;
	}
	public void setSourceConcept(String sourceConcept) {
		this.sourceConcept = sourceConcept;
	}
	
	public String getTargetConcept() {
		return targetConcept;
	}
	public void setTargetConcept(String targetConcept) {
		this.targetConcept = targetConcept;
	}
	public String getRelations() {
		return relations;
	}
	public void setRelations(String relations) {
		this.relations = relations;
	}
	public List<String> getSensitiveCategories() {
		return sensitiveCategories;
	}
	public void setSensitiveCategories(List<String> sensitiveCategories) {
		this.sensitiveCategories = sensitiveCategories;
	}
	
	
}
