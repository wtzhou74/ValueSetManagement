package valueset.model.dbModel;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Reference to PCM.CONCEPT_CODE
 * @author ZHOU WENTAO
 *
 */
@Entity
@Table(name="concept_code", schema="pcm")
public class ConceptCode {

	@Id
	@GeneratedValue
	private Long conceptCodeId;
	private String userName;
	private String code;
	private String name;
	private String description;
	@CreatedDate
	private Date creationTime;
	@LastModifiedDate
	private Date modificationTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_code_system_version_id")
	private CodeSystemVersion codeSystemVersion;
	
	public ConceptCode() {
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="conceptcode_valueset",
			joinColumns=@JoinColumn(name="fk_concept_code_id", referencedColumnName="conceptCodeId"),
			inverseJoinColumns=@JoinColumn(name="fk_valueset_id", referencedColumnName="valuesetId")
			)
	private List<ValueSet> valueSets;
	
	public CodeSystemVersion getCodeSystemVersion() {
		return codeSystemVersion;
	}


	public void setCodeSystemVersion(CodeSystemVersion codeSystemVersion) {
		this.codeSystemVersion = codeSystemVersion;
	}


	public Long getConceptCodeId() {
		return conceptCodeId;
	}

	public void setConceptCodeId(Long conceptCodeId) {
		this.conceptCodeId = conceptCodeId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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


	public List<ValueSet> getValueSets() {
		return valueSets;
	}


	public void setValueSets(List<ValueSet> valueSets) {
		this.valueSets = valueSets;
	}
	
	
}
