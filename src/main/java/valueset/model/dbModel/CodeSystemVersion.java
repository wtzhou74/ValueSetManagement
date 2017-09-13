package valueset.model.dbModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Reference to PCM.CODE_SYSTEM_VERSION
 * @author ZHOU WENTAO
 *
 */
@Entity
@Table(name="code_system_version", schema="pcm")
public class CodeSystemVersion implements Serializable{

	@Id
	private Long code_system_version_id;
	@CreatedDate
	private Date creationTime;
	@LastModifiedDate
	private Date modificationTime;
	private String userName;
	private String description;
	private String versionName;
	//private Long fkCodeSystemId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_Code_System_Id")
	private CodeSystem codeSystem;
	
	@OneToMany(mappedBy = "codeSystemVersion")
	private Collection<ConceptCode> conceptCodes = new ArrayList<ConceptCode>();

	
	public CodeSystemVersion() {
	}

	
	public Collection<ConceptCode> getConceptCodes() {
		return conceptCodes;
	}


	public void setConceptCodes(Collection<ConceptCode> conceptCodes) {
		this.conceptCodes = conceptCodes;
	}


	public Long getCode_system_version_id() {
		return code_system_version_id;
	}

	public void setCode_system_version_id(Long code_system_version_id) {
		this.code_system_version_id = code_system_version_id;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	/*public Long getFkCodeSystemId() {
		return fkCodeSystemId;
	}

	public void setFkCodeSystemId(Long fkCodeSystemId) {
		this.fkCodeSystemId = fkCodeSystemId;
	}*/

	public CodeSystem getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(CodeSystem codeSystem) {
		this.codeSystem = codeSystem;
	}
	
	
}
