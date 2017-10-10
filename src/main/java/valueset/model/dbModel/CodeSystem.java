package valueset.model.dbModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Reference to PCM.CODE_SYSTEM
 * @author ZHOU WENTAO
 *
 */
@Entity
@Table(name="code_system", schema="pcm")
public class CodeSystem implements Serializable{

	@Id
	private Long codeSystemId;
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date creationTime;
	@LastModifiedDate
	private Date modificationTime;
	private String userName;
	private String code;
	private String name;
	private String codeSystemOid;
	private String displayName;
	
	@OneToMany(mappedBy = "codeSystem")
	private Collection<CodeSystemVersion> codeSystemVersions;
	
	
	public CodeSystem() {
	}


	/*public CodeSystem(int codeSystemId, Date creationTime, Date modificationTime, String userName, String code,
			String name, String codeSystemOid, String displayName) {
		super();
		this.codeSystemId = codeSystemId;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
		this.userName = userName;
		this.code = code;
		this.name = name;
		this.codeSystemOid = codeSystemOid;
		this.displayName = displayName;
	}*/


	public Collection<CodeSystemVersion> getCodeSystemVersions() {
		return codeSystemVersions;
	}


	public void setCodeSystemVersions(Collection<CodeSystemVersion> codeSystemVersions) {
		this.codeSystemVersions = codeSystemVersions;
	}


	public Long getCodeSystemId() {
		return codeSystemId;
	}


	public void setCodeSystemId(Long codeSystemId) {
		this.codeSystemId = codeSystemId;
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCodeSystemOid() {
		return codeSystemOid;
	}


	public void setCodeSystemOid(String codeSystemOid) {
		this.codeSystemOid = codeSystemOid;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
}
