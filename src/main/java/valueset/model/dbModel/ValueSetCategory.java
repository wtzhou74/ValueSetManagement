package valueset.model.dbModel;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Reference to PCM.VALUE_SET_CATEGORY
 * @author ZHOU WENTAO
 *
 */
@Entity
@Table(name="value_set_category", schema="pcm")
public class ValueSetCategory {

	@Id
	private Long valuesetCatId;
	private String userName;
	private String code;
	private String name;
	private int isFederal;
	private Long displayOrder;
	private String description;
	@CreatedDate
	private Date creationTime;
	@LastModifiedDate
	private Date modificationTime;
	
	@OneToMany(mappedBy="valueSetCategory")
	private List<ValueSet> valueSets;
	
	public ValueSetCategory() {
	}

	public List<ValueSet> getValueSets() {
		return valueSets;
	}


	public void setValueSets(List<ValueSet> valueSets) {
		this.valueSets = valueSets;
	}


	public Long getValuesetCatId() {
		return valuesetCatId;
	}

	public void setValuesetCatId(Long valuesetCatId) {
		this.valuesetCatId = valuesetCatId;
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

	public int getIsFederal() {
		return isFederal;
	}

	public void setIsFederal(int isFederal) {
		this.isFederal = isFederal;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
