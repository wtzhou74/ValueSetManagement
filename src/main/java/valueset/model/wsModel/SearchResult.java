package valueset.model.wsModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class SearchResult {
	
	private String id;
	private String ui;
	private String name;
	private String uri;
	private String rootSource;
	private String ingredient;
	private String sensitveCategory;
	
	//getters
    public String getUi() {
		
		return this.ui;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getUri() {
		
		return this.uri;
	}
	
	public String getRootSource() {
		
		return this.rootSource;
	}

	//setters
	private void setUi(String ui) {
		
		this.ui = ui;
	}
	
	private void setName(String name) {
		
		this.name = name;
	}
	
	private void setUri(String uri) {
		
		this.uri = uri;
	}
	
	private void setRootSource(String rootSource) {
		
		this.rootSource = rootSource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSensitveCategory() {
		return sensitveCategory;
	}

	public void setSensitveCategory(String sensitveCategory) {
		this.sensitveCategory = sensitveCategory;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	
	
}
