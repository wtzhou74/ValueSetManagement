package valueset.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.tree.TreeNode;

import org.apache.lucene.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.model.dbModel.RxnSemanticRelations;
import valueset.model.modelView.ConceptMappingResultModelView;
import valueset.model.modelView.GraphNodeModel;
import valueset.model.wsModel.ConceptGroup;
import valueset.model.wsModel.ConceptProperties;
import valueset.model.wsModel.PropConcept;
import valueset.model.wsModel.PropConceptResponse;
import valueset.model.wsModel.RxnormDataWithSpecifiedTtyResponse;
import valueset.service.ws.rxnorm.RxNormWebService;
import valueset.utils.ConstantUtil;

/**
 * Major business logics used to handle concept mapping between RxNorm and VS
 * @author ZHOU WENTAO
 *
 */
@Service
public class ConceptMappingService {
	
	@Autowired
	private RxNormWebService rxNormWebService;
	@Autowired
	private ConceptCodeService conceptCodeService;
	@Autowired
	private RxnSemanticRelationService rxnSemanticRelationService;
	@Autowired
	private JenaBasedSemanticService jenaBasedSemanticService;
	@Autowired
	private ICDcmConceptService iCDcmConceptService;
	
	public List<ConceptMappingResultModelView> findMappedConceptInVS (String sourceConcept, String codeSystem, String solution) {
		
		List<ConceptMappingResultModelView> conceptMappingResultViews = new ArrayList<ConceptMappingResultModelView>();
		//ICD10CM
		if (ConstantUtil.ICD10CM.equalsIgnoreCase(codeSystem)
				|| ConstantUtil.ICD9CM.equalsIgnoreCase(codeSystem)) {
			ConceptMappingResultModelView conceptMappingResultView = iCDcmConceptService.identifySenCategoryOfICDConcept(sourceConcept, codeSystem);
			conceptMappingResultViews.add(conceptMappingResultView);
			
			return conceptMappingResultViews;
		}
		//TODO The following codes should be re-arranged
		
		//Check the TTY of the source concept
		//Find the term type of the specified concept
		
		if (solution.equalsIgnoreCase("option1")){
			
			List<String> ingredients = getIngredientsOfSpecifiedConcept(sourceConcept, ConstantUtil.IN);
			
			for (String ingredient : ingredients) {
				List<String> senCategoryList = conceptCodeService.findSensitiveOfSpecifiedConcept(ingredient);
				
				ConceptMappingResultModelView conceptMappingResult = new ConceptMappingResultModelView();
				
				conceptMappingResult.setSensitiveCategories(senCategoryList);
				conceptMappingResult.setSourceConcept(sourceConcept);
				conceptMappingResult.setTargetConcept(ingredient);
				conceptMappingResultViews.add(conceptMappingResult);
				//print
				//TODO UPDATE
				printSensitive(sourceConcept, senCategoryList);
			}
			
			return conceptMappingResultViews;
		}

		String sourceTTY = getTermType4SpecifiedConcept(sourceConcept);
		
		//Map the given concept to VS directly if TTY = IN
		if (ConstantUtil.IN.equalsIgnoreCase(sourceTTY)) {
			//find value set category for specified concept
			List<String> senCategoryList = conceptCodeService.findSensitiveOfSpecifiedConcept(sourceConcept);
	
			ConceptMappingResultModelView conceptMappingResult = new ConceptMappingResultModelView();
			
			conceptMappingResult.setSensitiveCategories(senCategoryList);
			conceptMappingResult.setSourceConcept(sourceConcept);
			conceptMappingResultViews.add(conceptMappingResult);
			//print
			//TODO UPDATE
			printSensitive(sourceConcept, senCategoryList);
			
			return conceptMappingResultViews;
		}
			
		//List<String> relationPath = new ArrayList<>();
		//find its target Sensitive
		findTargetSensitive (sourceTTY, sourceConcept, conceptMappingResultViews, codeSystem);
		
		return conceptMappingResultViews;
		
		
	}
	
	/**
	 * Find the target concept whose TTY is Ingredient
	 * @param sourceTTY
	 * @param relationPath
	 * @param sourceConcept
	 */
	public void findTargetSensitive (String sourceTTY, String sourceConcept, 
			List<ConceptMappingResultModelView> conceptMappingResultViews, String codeSystem) {
		
		List<List<GraphNodeModel>> availablePaths = getAllAvailPathViaBreadthFirst(sourceTTY);
		//to get the chain of relations by traversing the available paths backforward
		List<List<String>> relationPaths = new ArrayList<List<String>>();
		for (List<GraphNodeModel> paths : availablePaths) {
			List<String> relations = new ArrayList<>();
			for (int i = paths.size() - 1; i >= 0; i--) {
				//Skip root node
				if ("" == paths.get(i).getRelation()) {
					continue;
				}
				relations.add(paths.get(i).getRelation());
			}
			relationPaths.add(relations);
		}
		for (List<String> path : relationPaths) {
			findSensitiveOfTargetConcept(path, sourceConcept, conceptMappingResultViews, codeSystem);
		}
		
	}
	
	/**
	 * Fina all available Path through breadth first
	 * @param sourceTTY
	 * @return
	 */
	public List<List<GraphNodeModel>> getAllAvailPathViaBreadthFirst(String sourceTTY) {
		Deque<GraphNodeModel> nodeDeque = new ArrayDeque<GraphNodeModel>();
		List<String> tempTTY = new ArrayList<String>();
		List<GraphNodeModel> tempNodes = new ArrayList<GraphNodeModel>();
		
		List<List<GraphNodeModel>> availablePaths = new ArrayList<List<GraphNodeModel>>();
		
		//Record the first node, and the first level is 0
		GraphNodeModel rootNode = new GraphNodeModel();
		rootNode.setCurrentLevel(0);
		rootNode.setCurrentNode(sourceTTY);
		nodeDeque.add(rootNode);
		
		while (!nodeDeque.isEmpty()) {
			GraphNodeModel node = nodeDeque.pollFirst();//retrieve and remove the first element of queue
			tempNodes.add(node);		
			//Do not need to find children's node of IN
			if (ConstantUtil.IN.equalsIgnoreCase(node.getCurrentNode())) {
				continue;
			}
			//Skip if the TTY was visited
			if (tempTTY.contains(node.getCurrentNode())) {
				continue;
			}
			tempTTY.add(node.getCurrentNode());
			//Get the child's child's node
			List<GraphNodeModel> children = getChildren (node.getCurrentNode());
			
			if (children != null 
					&& !children.isEmpty()) {
				
				for (GraphNodeModel child : children) {
					
					//Record its path when IN was found
					if (ConstantUtil.IN.equalsIgnoreCase(child.getCurrentNode())) {
						List<GraphNodeModel> path = new ArrayList<GraphNodeModel>();
						path.add(child);
						String parentNode = child.getParentNode();
						for (int i = 0; i < tempNodes.size(); i++) {
							if (tempNodes.get(i).getCurrentNode().equals(parentNode)) {
								path.add(tempNodes.get(i));
								//find the child's parent's parent
								parentNode = tempNodes.get(i).getParentNode();
								//Find the parent's parent node
								if (parentNode.equalsIgnoreCase(rootNode.getCurrentNode())) {
									break;
								} else {
									i = 0;
								}
								
							}
						}
						availablePaths.add(path);
					}
					nodeDeque.add(child);
				}
			}
		}
		//Backward to get the path
		for (List<GraphNodeModel> paths : availablePaths) {
			for (int i = paths.size() - 1; i >= 0; i--) {
				System.out.print(paths.get(i).getParentNode() + " / " + paths.get(i).getRelation() + " ");
			}
			System.out.println();
			
			System.out.println("********************");
		}
		
		return availablePaths;
	}
	
	/**
	 * Get children nodes
	 * @param parent
	 * @return
	 */
	public List<GraphNodeModel> getChildren (String parent) {
		List<GraphNodeModel> children = new ArrayList<GraphNodeModel>();
		List<RxnSemanticRelations> semanticRelations = 
				rxnSemanticRelationService.findSemanticRelations(parent);
		for (RxnSemanticRelations semanticRelation : semanticRelations) {
			GraphNodeModel node = new GraphNodeModel();
			node.setParentNode(parent);
			node.setCurrentNode(semanticRelation.getDestination_termtype());
			node.setRelation(semanticRelation.getRelation_name());
			
			children.add(node);
		}
		return children;
	}
	
	/**
	 * Backforward to recursive find the path to IN
	 * @param nodes
	 * @param parentNode
	 * @return
	 */
	public void findPathToIN (List<GraphNodeModel> path, List<GraphNodeModel> nodes, String parentNode) {
		//List<GraphNodeModel> path = new ArrayList<GraphNodeModel>();
		//GraphNodeModel parentNode = new GraphNodeModel();
		for (GraphNodeModel tempNode : nodes) {
			if (tempNode.getCurrentNode().equals(parentNode)) {
				path.add(tempNode);
				//find the child's parent's parent
				parentNode = tempNode.getParentNode();
				break;
			}
		}
		findPathToIN (path, nodes, parentNode);
	}
	
	/**
	 * Find target concept for a specified source concept and its relation via retrieving RDF graph
	 * @param relation
	 * @param sourceConcept
	 */
	public void findSensitiveOfTargetConcept (List<String> relations, String sourceConcept, 
			List<ConceptMappingResultModelView> conceptMappingResultViews, String codeSystem) {
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT DISTINCT ?o WHERE { ");
		stringBuffer.append("?s skos:notation ?concept0 . ");
		if (relations.size() == 1) {
			stringBuffer.append("?s" + " RxNorm:" + relations.get(0) + " ?o .");
		} else {
			for (int i = 0; i < relations.size(); i++) {
				if (i == 0) {
					stringBuffer.append("?s" + " RxNorm:" + relations.get(i) + " ?concept" + (i+1) + ". ");
				} else {
					if (i == relations.size() - 1) {
						stringBuffer.append("?concept" + i + " RxNorm:" + relations.get(i) + " ?o .");
					} else {
						stringBuffer.append("?concept" + i + " RxNorm:" + relations.get(i) + " ?concept" + (i+1) + ". ");
					}
				}
			}
		}
		
		stringBuffer.append(" }");
		
		List<String> targetConcepts = jenaBasedSemanticService.queryTargetConeptViaRelation(codeSystem, sourceConcept, stringBuffer.toString());
		for (String targetConcept : targetConcepts) {
			//Check the term type of target concept, because each term type could refers to multiple target term types through a certain relation
			String tty = getTermType4SpecifiedConcept(targetConcept);
			//skip if the tty of target concept code is not IN
			if (!ConstantUtil.IN.equalsIgnoreCase(tty)) {
				continue;
			}
			List<String> senCategoryList = conceptCodeService.findSensitiveOfSpecifiedConcept(targetConcept);
			if (!senCategoryList.isEmpty()) {
				ConceptMappingResultModelView conceptMappingResultModelView = new ConceptMappingResultModelView();
				conceptMappingResultModelView.setSourceConcept(sourceConcept);
				conceptMappingResultModelView.setPath(getRelationInString(relations));
				conceptMappingResultModelView.setSensitiveCategories(senCategoryList);
				conceptMappingResultModelView.setTargetConcept(targetConcept);
				
				conceptMappingResultViews.add(conceptMappingResultModelView);
			}
			
			//print
			//TODO UPDATE
			printSensitive(targetConcept, senCategoryList);
		}
	}
	
	public void printSensitive (String conceptCode, List<String> senCategoryList) {
		for (String sensitiveCategory : senCategoryList) {
			System.out.println("SENSITIVE CATEGORY: " + conceptCode + ": " + sensitiveCategory);
		}
	}
	
	public String getRelationInString (List<String> relations) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String relation : relations) {
			stringBuffer.append(relation);
			stringBuffer.append(" / ");
		}
		return stringBuffer.toString();
	}
	
	//
	/**
	 * Find the term type for a specified concept
	 * @param cui
	 * @return
	 */
	public String getTermType4SpecifiedConcept (String cui) {
		PropConceptResponse propConceptResponse = rxNormWebService.getAllProperties(cui);
		List<PropConcept> propConcepts = propConceptResponse.getPropConceptGroup().getPropConcept();
		String termType = "";
		for (PropConcept propConcept : propConcepts) {
			//Find its term type
			if (ConstantUtil.PROP_CATEGORY_ATTRIBUTE.equalsIgnoreCase(propConcept.getPropCategory())
					&& ConstantUtil.PROP_NAME_TTY.equalsIgnoreCase(propConcept.getPropName())) {
				termType = propConcept.getPropValue();
				break;
			}
		}
		return termType;
	}
	
	/**
	 * Find all related Rxcui of concept with specified termtype
	 * @param cui
	 * @param tty
	 * @return
	 */
	public List<String> getIngredientsOfSpecifiedConcept (String cui, String tty) {
		List<String> ingredients = new ArrayList<String>();
		RxnormDataWithSpecifiedTtyResponse response = new RxnormDataWithSpecifiedTtyResponse();
		response = rxNormWebService.getConceptWithSepcifiedTTYandCUI(cui, tty);
		
		//Get the rxcui of concept with specified termtype
		if (null != response.getRelatedGroup()) {
			List<ConceptGroup> conceptGroups = response.getRelatedGroup().getConceptGroup();
			for (ConceptGroup conceptGroup : conceptGroups) {
				if (tty.equalsIgnoreCase(conceptGroup.getTty())) {
					for (ConceptProperties conceptProperty : conceptGroup.getConceptProperties()) {
						ingredients.add(conceptProperty.getRxcui());		
					}
				}
			}
		}
		
		return ingredients;
	}
	
	//BreadthFirst
	/*public void findTargetTTYisIngredient (String sourceTTY) {
		// sourceTTY is root
		Queue<String> queue = new ArrayDeque<String> ();
		List<String> list = new ArrayList<String>();
		
		if (sourceTTY != "") {
			queue.offer(sourceTTY);
		}
		
		while (!queue.isEmpty()) {
			//retrieve, but not remove, and add the head of the queue to the List
			list.add(queue.peek());
			//retrieve and remove the head of the queue
			String currentTTY = queue.poll();
			if (!list.contains(currentTTY)) {
				
			}
		}
	}*/
	
}
