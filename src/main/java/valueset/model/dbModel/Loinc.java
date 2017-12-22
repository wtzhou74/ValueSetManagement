package valueset.model.dbModel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loinc", schema="loinc2017")
public class Loinc implements Serializable{

	@Id
	private String loincNum;
	private String component;
	private String property;
	private String timeAspect;
	private String system;
	private String scaleType;
	private String methodType;
	private String loincClass;
	private String versionlastchanged;
	private String chngType;
	private String definitiondescription;
	private String status;
	private String consumerName;
	private Integer classtype;
	private String formula;
	private String species;
	private String exmplAnswers;
	private String surveyQuestText;
	private String surveyQuestSrc;
	private String unitsrequired;
	private String submittedUnits;
	private String relatednames2;
	private String shortname;
	private String orderObs;
	private String cdiscCommonTests;
	private String hl7FieldSubfieldId;
	private String externalCopyrightNotice;
	private String exampleUnits;
	private String longCommonName;
	private String unitsandrange;
	private String documentSection;
	private String exampleUcumUnits;
	private String exampleSiUcumUnits;
	private String statusReason;
	private String statusText;
	private String changeReasonPublic;
	private Integer commonTestRank;
	private Integer commonOrderRank;
	private Integer commonSiTestRank;
	private String hl7AttachmentStructure;
	private String externalcopyrightlink;
	private String paneltype;
	private String askatorderentry;
	private String associatedobservations;
	private String versionfirstreleased;
	private String validhl7attachmentrequest;
	public String getLoincNum() {
		return loincNum;
	}
	public void setLoincNum(String loincNum) {
		this.loincNum = loincNum;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getTimeAspect() {
		return timeAspect;
	}
	public void setTimeAspect(String timeAspect) {
		this.timeAspect = timeAspect;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getScaleType() {
		return scaleType;
	}
	public void setScaleType(String scaleType) {
		this.scaleType = scaleType;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getLoincClass() {
		return loincClass;
	}
	public void setLoincClass(String loincClass) {
		this.loincClass = loincClass;
	}
	public String getVersionlastchanged() {
		return versionlastchanged;
	}
	public void setVersionlastchanged(String versionlastchanged) {
		this.versionlastchanged = versionlastchanged;
	}
	public String getChngType() {
		return chngType;
	}
	public void setChngType(String chngType) {
		this.chngType = chngType;
	}
	public String getDefinitiondescription() {
		return definitiondescription;
	}
	public void setDefinitiondescription(String definitiondescription) {
		this.definitiondescription = definitiondescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public Integer getClasstype() {
		return classtype;
	}
	public void setClasstype(Integer classtype) {
		this.classtype = classtype;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getExmplAnswers() {
		return exmplAnswers;
	}
	public void setExmplAnswers(String exmplAnswers) {
		this.exmplAnswers = exmplAnswers;
	}
	public String getSurveyQuestText() {
		return surveyQuestText;
	}
	public void setSurveyQuestText(String surveyQuestText) {
		this.surveyQuestText = surveyQuestText;
	}
	public String getSurveyQuestSrc() {
		return surveyQuestSrc;
	}
	public void setSurveyQuestSrc(String surveyQuestSrc) {
		this.surveyQuestSrc = surveyQuestSrc;
	}
	public String getUnitsrequired() {
		return unitsrequired;
	}
	public void setUnitsrequired(String unitsrequired) {
		this.unitsrequired = unitsrequired;
	}
	public String getSubmittedUnits() {
		return submittedUnits;
	}
	public void setSubmittedUnits(String submittedUnits) {
		this.submittedUnits = submittedUnits;
	}
	public String getRelatednames2() {
		return relatednames2;
	}
	public void setRelatednames2(String relatednames2) {
		this.relatednames2 = relatednames2;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getOrderObs() {
		return orderObs;
	}
	public void setOrderObs(String orderObs) {
		this.orderObs = orderObs;
	}
	public String getCdiscCommonTests() {
		return cdiscCommonTests;
	}
	public void setCdiscCommonTests(String cdiscCommonTests) {
		this.cdiscCommonTests = cdiscCommonTests;
	}
	public String getHl7FieldSubfieldId() {
		return hl7FieldSubfieldId;
	}
	public void setHl7FieldSubfieldId(String hl7FieldSubfieldId) {
		this.hl7FieldSubfieldId = hl7FieldSubfieldId;
	}
	public String getExternalCopyrightNotice() {
		return externalCopyrightNotice;
	}
	public void setExternalCopyrightNotice(String externalCopyrightNotice) {
		this.externalCopyrightNotice = externalCopyrightNotice;
	}
	public String getExampleUnits() {
		return exampleUnits;
	}
	public void setExampleUnits(String exampleUnits) {
		this.exampleUnits = exampleUnits;
	}
	public String getLongCommonName() {
		return longCommonName;
	}
	public void setLongCommonName(String longCommonName) {
		this.longCommonName = longCommonName;
	}
	public String getUnitsandrange() {
		return unitsandrange;
	}
	public void setUnitsandrange(String unitsandrange) {
		this.unitsandrange = unitsandrange;
	}
	public String getDocumentSection() {
		return documentSection;
	}
	public void setDocumentSection(String documentSection) {
		this.documentSection = documentSection;
	}
	public String getExampleUcumUnits() {
		return exampleUcumUnits;
	}
	public void setExampleUcumUnits(String exampleUcumUnits) {
		this.exampleUcumUnits = exampleUcumUnits;
	}
	public String getExampleSiUcumUnits() {
		return exampleSiUcumUnits;
	}
	public void setExampleSiUcumUnits(String exampleSiUcumUnits) {
		this.exampleSiUcumUnits = exampleSiUcumUnits;
	}
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getChangeReasonPublic() {
		return changeReasonPublic;
	}
	public void setChangeReasonPublic(String changeReasonPublic) {
		this.changeReasonPublic = changeReasonPublic;
	}
	public Integer getCommonTestRank() {
		return commonTestRank;
	}
	public void setCommonTestRank(Integer commonTestRank) {
		this.commonTestRank = commonTestRank;
	}
	public Integer getCommonOrderRank() {
		return commonOrderRank;
	}
	public void setCommonOrderRank(Integer commonOrderRank) {
		this.commonOrderRank = commonOrderRank;
	}
	public Integer getCommonSiTestRank() {
		return commonSiTestRank;
	}
	public void setCommonSiTestRank(Integer commonSiTestRank) {
		this.commonSiTestRank = commonSiTestRank;
	}
	public String getHl7AttachmentStructure() {
		return hl7AttachmentStructure;
	}
	public void setHl7AttachmentStructure(String hl7AttachmentStructure) {
		this.hl7AttachmentStructure = hl7AttachmentStructure;
	}
	public String getExternalcopyrightlink() {
		return externalcopyrightlink;
	}
	public void setExternalcopyrightlink(String externalcopyrightlink) {
		this.externalcopyrightlink = externalcopyrightlink;
	}
	public String getPaneltype() {
		return paneltype;
	}
	public void setPaneltype(String paneltype) {
		this.paneltype = paneltype;
	}
	public String getAskatorderentry() {
		return askatorderentry;
	}
	public void setAskatorderentry(String askatorderentry) {
		this.askatorderentry = askatorderentry;
	}
	
	public String getAssociatedobservations() {
		return associatedobservations;
	}
	public void setAssociatedobservations(String associatedobservations) {
		this.associatedobservations = associatedobservations;
	}
	public String getVersionfirstreleased() {
		return versionfirstreleased;
	}
	public void setVersionfirstreleased(String versionfirstreleased) {
		this.versionfirstreleased = versionfirstreleased;
	}
	public String getValidhl7attachmentrequest() {
		return validhl7attachmentrequest;
	}
	public void setValidhl7attachmentrequest(String validhl7attachmentrequest) {
		this.validhl7attachmentrequest = validhl7attachmentrequest;
	}
	
	
	
	
}
