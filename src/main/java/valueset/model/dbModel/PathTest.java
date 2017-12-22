package valueset.model.dbModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="path_test")
public class PathTest {

	@Id
	private Integer edge;
	private String source;
	private String destination;
	public Integer getEdge() {
		return edge;
	}
	public void setEdge(Integer edge) {
		this.edge = edge;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
}
