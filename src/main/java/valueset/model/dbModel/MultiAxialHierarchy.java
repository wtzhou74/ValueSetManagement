package valueset.model.dbModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="multi_axial_hierarchy", schema="loinc2017")
public class MultiAxialHierarchy {

	@Id
	private Long id;
	private String pathToRoot;
	private int sequence;
	private String immediateParent;
	private String code;
	private String codeText;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPathToRoot() {
		return pathToRoot;
	}
	public void setPathToRoot(String pathToRoot) {
		this.pathToRoot = pathToRoot;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getImmediateParent() {
		return immediateParent;
	}
	public void setImmediateParent(String immediateParent) {
		this.immediateParent = immediateParent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeText() {
		return codeText;
	}
	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}
	
	
	
}
