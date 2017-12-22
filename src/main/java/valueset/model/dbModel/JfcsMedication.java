package valueset.model.dbModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="jfcs_medication", schema="pcm")
public class JfcsMedication {

	@Id
	private String patientId;
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	private String gender;
	private String race;
	private String ethnicity;
	@Temporal(TemporalType.DATE)
	private Date admitDate;
	private String providerNpi;
	private String providerSpeciality;
	@Temporal(TemporalType.DATE)
	private Date lastEncounter;
	private String lastProcedureCode;
	@Temporal(TemporalType.DATE)
	private Date lastProcedureDate;
	private String diagnosis;
	private String medications;
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public Date getAdmitDate() {
		return admitDate;
	}
	public void setAdmitDate(Date admitDate) {
		this.admitDate = admitDate;
	}
	public String getProviderNpi() {
		return providerNpi;
	}
	public void setProviderNpi(String providerNpi) {
		this.providerNpi = providerNpi;
	}
	public String getProviderSpeciality() {
		return providerSpeciality;
	}
	public void setProviderSpeciality(String providerSpeciality) {
		this.providerSpeciality = providerSpeciality;
	}
	public Date getLastEncounter() {
		return lastEncounter;
	}
	public void setLastEncounter(Date lastEncounter) {
		this.lastEncounter = lastEncounter;
	}
	public String getLastProcedureCode() {
		return lastProcedureCode;
	}
	public void setLastProcedureCode(String lastProcedureCode) {
		this.lastProcedureCode = lastProcedureCode;
	}
	public Date getLastProcedureDate() {
		return lastProcedureDate;
	}
	public void setLastProcedureDate(Date lastProcedureDate) {
		this.lastProcedureDate = lastProcedureDate;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getMedications() {
		return medications;
	}
	public void setMedications(String medications) {
		this.medications = medications;
	}
	
	
}
