package valueset.model.dbModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Reference to PCM.VALUE_SET
 * @author ZHOU WENTAO
 *
 */
@Entity
@Table(name="value_set", schema="pcm")
public class ValueSet {

	@Id
	private Long valuesetId;
	private String userName;
	private String code;
	private String name;
	private String description;
	@CreatedDate
	private Date creationTime;
	@LastModifiedDate
	private Date modificationTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_valueset_cat_id")
	private ValueSetCategory valueSetCategory;
	
	public ValueSet() {
	}

	public ValueSetCategory getValueSetCategory() {
		return valueSetCategory;
	}



	public void setValueSetCategory(ValueSetCategory valueSetCategory) {
		this.valueSetCategory = valueSetCategory;
	}



	public Long getValuesetId() {
		return valuesetId;
	}

	public void setValuesetId(Long valuesetId) {
		this.valuesetId = valuesetId;
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
	
	
}
