package valueset.model.wsModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
		ignoreUnknown = true)
public class ConceptProperties {

	private String rxcui = "";
	private String name = "";
	private String synonym = "";
	private String tty = "";
	private String language = "";
	private String suppress = "";
	private String umlscui = "";
	public String getRxcui() {
		return rxcui;
	}
	public void setRxcui(String rxcui) {
		this.rxcui = rxcui;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSynonym() {
		return synonym;
	}
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}
	public String getTty() {
		return tty;
	}
	public void setTty(String tty) {
		this.tty = tty;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSuppress() {
		return suppress;
	}
	public void setSuppress(String suppress) {
		this.suppress = suppress;
	}
	public String getUmlscui() {
		return umlscui;
	}
	public void setUmlscui(String umlscui) {
		this.umlscui = umlscui;
	}
	
	
}
