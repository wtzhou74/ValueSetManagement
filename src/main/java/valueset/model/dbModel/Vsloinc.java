package valueset.model.dbModel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vsloinc", schema="loinc2017")
public class Vsloinc implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	private String code;
	private String description;
	private String codesystem;
	private String codesystemversion;
	private String codesystemoid;
	private String tty;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodesystem() {
		return codesystem;
	}
	public void setCodesystem(String codesystem) {
		this.codesystem = codesystem;
	}
	public String getCodesystemversion() {
		return codesystemversion;
	}
	public void setCodesystemversion(String codesystemversion) {
		this.codesystemversion = codesystemversion;
	}
	public String getCodesystemoid() {
		return codesystemoid;
	}
	public void setCodesystemoid(String codesystemoid) {
		this.codesystemoid = codesystemoid;
	}
	public String getTty() {
		return tty;
	}
	public void setTty(String tty) {
		this.tty = tty;
	}
	
}
