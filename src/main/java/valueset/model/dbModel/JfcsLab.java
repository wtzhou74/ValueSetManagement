package valueset.model.dbModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="jfcs_labs", schema="pcm")
public class JfcsLab {

	@Id
	private Long id;
	private String participantId;
	private String testDescription;
	@Temporal(TemporalType.DATE)
	private Date testOrdered;
	private String resultDesc;
	private String observValue;
	private String units;
	@Temporal(TemporalType.DATE)
	private Date obsDateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public Date getTestOrdered() {
		return testOrdered;
	}
	public void setTestOrdered(Date testOrdered) {
		this.testOrdered = testOrdered;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getObservValue() {
		return observValue;
	}
	public void setObservValue(String observValue) {
		this.observValue = observValue;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public Date getObsDateTime() {
		return obsDateTime;
	}
	public void setObsDateTime(Date obsDateTime) {
		this.obsDateTime = obsDateTime;
	}
	
	
}
