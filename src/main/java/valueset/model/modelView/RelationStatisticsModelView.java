package valueset.model.modelView;

import java.util.ArrayList;
import java.util.List;

public class RelationStatisticsModelView {

	private int numOfConceptCode = 0;//Record the number of total concept code
	private int numOfSpecifiedRelation = 0;//Record the number of concept codes which do not have the specified relation
	private List<String> conceptCodesWithoutRelation = new ArrayList<String>();//Record the concepts that have no relation with other concepts
	private List<String> conceptCodesWithoutSpecifiedRelation = new ArrayList<String>();//Record the concepts that have no specified relation with other concepts
	private String terminology;//the terminology that the statistics was calculated on
	
	public int getNumOfConceptCode() {
		return numOfConceptCode;
	}
	public void setNumOfConceptCode(int numOfConceptCode) {
		this.numOfConceptCode = numOfConceptCode;
	}
	public int getNumOfSpecifiedRelation() {
		return numOfSpecifiedRelation;
	}
	public void setNumOfSpecifiedRelation(int numOfSpecifiedRelation) {
		this.numOfSpecifiedRelation = numOfSpecifiedRelation;
	}
	public List<String> getConceptCodesWithoutRelation() {
		return conceptCodesWithoutRelation;
	}
	public void setConceptCodesWithoutRelation(List<String> conceptCodesWithoutRelation) {
		this.conceptCodesWithoutRelation = conceptCodesWithoutRelation;
	}
	public List<String> getConceptCodesWithoutSpecifiedRelation() {
		return conceptCodesWithoutSpecifiedRelation;
	}
	public void setConceptCodesWithoutSpecifiedRelation(List<String> conceptCodesWithoutSpecifiedRelation) {
		this.conceptCodesWithoutSpecifiedRelation = conceptCodesWithoutSpecifiedRelation;
	}
	public String getTerminology() {
		return terminology;
	}
	public void setTerminology(String terminology) {
		this.terminology = terminology;
	}
	
	
	
}
