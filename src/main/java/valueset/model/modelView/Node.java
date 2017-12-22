package valueset.model.modelView;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private String label = "";
	private String former = "";
	private List<String> allVisitedNodeLabel = new ArrayList<String>();
	private int distance;
	private boolean visited = false;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getFormer() {
		return former;
	}
	public void setFormer(String former) {
		this.former = former;
	}
	public List<String> getAllVisitedNodeLabel() {
		return allVisitedNodeLabel;
	}
	public void setAllVisitedNodeLabel(List<String> allVisitedNodeLabel) {
		this.allVisitedNodeLabel = allVisitedNodeLabel;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
