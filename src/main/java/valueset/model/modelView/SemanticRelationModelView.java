package valueset.model.modelView;

import java.util.ArrayList;
import java.util.List;

import valueset.rdfModelManage.RdfModelViewUnit;

public class SemanticRelationModelView {

	private List<RdfModelViewUnit> rdfViews = new ArrayList<RdfModelViewUnit>();

	public List<RdfModelViewUnit> getRdfViews() {
		return rdfViews;
	}

	public void setRdfViews(List<RdfModelViewUnit> rdfViews) {
		this.rdfViews = rdfViews;
	}
	
	
	
}
