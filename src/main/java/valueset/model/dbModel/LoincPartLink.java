package valueset.model.dbModel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loincpartlink", schema="loinc2017")
public class LoincPartLink implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	private String loincnumber;
	private String longcommonname;
	private String partnumber;
	private String partname;
	private String parttypename;
	private String linktypename;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPartnumber() {
		return partnumber;
	}
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	public String getParttypename() {
		return parttypename;
	}
	public void setParttypename(String parttypename) {
		this.parttypename = parttypename;
	}
	public String getLinktypename() {
		return linktypename;
	}
	public void setLinktypename(String linktypename) {
		this.linktypename = linktypename;
	}
	
	
}
