package valueset.model.dbModel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loincpart", schema="loinc2017")
public class LoincPart implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	private String partnumber;
	private String parttypename;
	private String partname;
	private String partdisplayname;
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPartnumber() {
		return partnumber;
	}
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}
	public String getParttypename() {
		return parttypename;
	}
	public void setParttypename(String parttypename) {
		this.parttypename = parttypename;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	public String getPartdisplayname() {
		return partdisplayname;
	}
	public void setPartdisplayname(String partdisplayname) {
		this.partdisplayname = partdisplayname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
