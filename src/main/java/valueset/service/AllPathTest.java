package valueset.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.transaction.Transactional;

import org.apache.jena.ext.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.dao.PathTestRepository;
import valueset.dao.RxnSemanticRelRepository;
import valueset.model.dbModel.PathTest;
import valueset.model.dbModel.RxnSemanticRelations;
import valueset.utils.ConstantUtil;

@Service
@Transactional
public class AllPathTest {

	@Autowired
	private RxnSemanticRelRepository rxnSemanticRelRepository;
	@Autowired
	private JenaBasedSemanticService jenaBasedSemanticService;
	private Stack<String> path = new Stack<String>();
	private Deque<String> onPath = new ArrayDeque<String>();//vertices on the path
	
	public void findAllPath (List<PathTest> edges, String startPoint) {
		path.push(startPoint);
		onPath.add(startPoint);
		
		if (startPoint.equalsIgnoreCase("3")) {
			System.out.println(path);
		} else {
			List<String> adjacentNodes = new ArrayList<String>();
			for (PathTest pathTest : edges) {
				if (startPoint.equalsIgnoreCase(pathTest.getSource())) {
					adjacentNodes.add(pathTest.getDestination());
				}
			}
			for (String adjacentNode : adjacentNodes) {
				if (!onPath.contains(adjacentNode)) {
					findAllPath (edges, adjacentNode);
				}
			}
			
		}
		
		path.pop();
		onPath.remove(startPoint);
		
	}
	
	public void findAllPathViaDFS (List<RxnSemanticRelations> edges, String sourceTTY, List<String> allPath) {
		path.push(sourceTTY);
		onPath.add(sourceTTY);
		if (sourceTTY.equalsIgnoreCase(ConstantUtil.IN)) {
			String test = path.toString();//14779
			if (!allPath.contains(test))
			{
				//System.out.println(test);
				allPath.add(path.toString());
			}
		} else {
			List<String> adjacentNodes = new ArrayList<String>();
			for (RxnSemanticRelations rxnSemanticRelations : edges) {
				if (rxnSemanticRelations.getSource_termtype().equalsIgnoreCase(sourceTTY)) {
					adjacentNodes.add(rxnSemanticRelations.getDestination_termtype());
				}
			}
			for (String adjacentNode : adjacentNodes) {
				if (!onPath.contains(adjacentNode)) {
					findAllPathViaDFS (edges, adjacentNode, allPath);
				}
			}
			
		}
		
		path.pop();
		onPath.removeLast();
	}
	
	public void traverseAllPath (String startPoint, String endPoint) {
		/*List<PathTest> edges = (List<PathTest>) Lists.newArrayList(pathTestRepository.findAll());
		findAllPath(edges, "2");*/
		List<RxnSemanticRelations> edges = Lists.newArrayList(rxnSemanticRelRepository.findAll());
		List<RxnSemanticRelations> tempEdge = new ArrayList<RxnSemanticRelations>();
		for (RxnSemanticRelations edge : edges) {
			if (edge.getRelation_name().endsWith("_of")
					|| edge.getRelation_name().equalsIgnoreCase("inverse_isa")) {
				tempEdge.add(edge);
			}
		}
		List<String> allPath = new ArrayList<String>();
		findAllPathViaDFS (edges, "BPCK", allPath);
		Collections.sort(allPath, new Comparator<String>() { 
			public int compare(String a, String b) {
				return a.split(",").length - b.split(",").length;
			}
		});
		for (int i = allPath.size() - 1; i >= 0; i--) {

			System.out.println(allPath.get(i));
		}
		System.out.println(allPath.get(allPath.size()-1));
 		return;
	}
	
	public void getSubGraph () {
		jenaBasedSemanticService.getLoincPartGraph();
	}
}
