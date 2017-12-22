package valueset.model.dbModel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Service;

@Entity
@Table(name="loincgroup", schema="loinc2017")
public class LoincGroup implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	private String parentgroupid;
	private String parentgroup;
	private String parentgroupcategory;
	private String parentgroupdescription;
	private String groupid;
	private String group;
	private String loincnumber;
	private String longcommonname;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParentgroupid() {
		return parentgroupid;
	}
	public void setParentgroupid(String parentgroupid) {
		this.parentgroupid = parentgroupid;
	}
	public String getParentgroup() {
		return parentgroup;
	}
	public void setParentgroup(String parentgroup) {
		this.parentgroup = parentgroup;
	}
	public String getParentgroupcategory() {
		return parentgroupcategory;
	}
	public void setParentgroupcategory(String parentgroupcategory) {
		this.parentgroupcategory = parentgroupcategory;
	}
	public String getParentgroupdescription() {
		return parentgroupdescription;
	}
	public void setParentgroupdescription(String parentgroupdescription) {
		this.parentgroupdescription = parentgroupdescription;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getLoincnumber() {
		return loincnumber;
	}
	public void setLoincnumber(String loincnumber) {
		this.loincnumber = loincnumber;
	}
	public String getLongcommonname() {
		return longcommonname;
	}
	public void setLongcommonname(String longcommonname) {
		this.longcommonname = longcommonname;
	}
	
	
}
