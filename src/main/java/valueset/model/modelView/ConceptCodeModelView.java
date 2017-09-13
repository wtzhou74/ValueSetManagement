package valueset.model.modelView;

import java.util.List;
import java.util.Map;

import valueset.model.dbModel.CodeSystem;
import valueset.model.dbModel.ConceptCode;

/**
 * A model view class used to describe code system and concept code
 * @author ZHOU WENTAO
 *
 */
public class ConceptCodeModelView {

	private List<ConceptCode> conceptCodes;
	private CodeSystem codeSystem;
	
	
	public ConceptCodeModelView() {
	}
	

	public List<ConceptCode> getConceptCodes() {
		return conceptCodes;
	}


	public void setConceptCodes(List<ConceptCode> conceptCodes) {
		this.conceptCodes = conceptCodes;
	}




	public CodeSystem getCodeSystem() {
		return codeSystem;
	}
	public void setCodeSystem(CodeSystem codeSystem) {
		this.codeSystem = codeSystem;
	}
	
	
}
