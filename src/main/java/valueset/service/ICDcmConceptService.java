package valueset.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.model.modelView.ConceptMappingResultModelView;

/**
 * Business logic to handle the identification of sensitive category of ICD-9/10CM concepts
 * @author ZHOU WENTAO
 *
 */
@Service
public class ICDcmConceptService {
	
	@Autowired
	private JenaBasedSemanticService jenaBasedSemanticService;
	@Autowired
	private ConceptCodeService conceptCodeService;
	
	/**
	 * Identify sensitive category of concept in ICD9/10 CM
	 * @param cui
	 * @param codeSystem
	 * @return
	 */
	public ConceptMappingResultModelView identifySenCategoryOfICDConcept (String cui, String codeSystem) {
		List<String> sensitiveCategories = new ArrayList<String>();
		
		//Check whether the current cui is available in VS
		sensitiveCategories = conceptCodeService.findSensitiveOfSpecifiedConcept(cui);
		if (sensitiveCategories.size() > 0) {
			return assembleViewResult (cui, "", sensitiveCategories, cui);
		} 
		
		//Its parent nodes will be checked if current node is not available in VS
		List<String> parentNodes = getAllParentNodes(cui, codeSystem);
		
		System.out.println("PATH: " + parentNodes.toString());
		if (parentNodes.size() > 0) {
			//Traverse each parent node until the sensitive category was find
			for (String parentNode : parentNodes) {
				sensitiveCategories = conceptCodeService.findSensitiveOfSpecifiedConcept(parentNode);
				if (sensitiveCategories.size() > 0) {
					return assembleViewResult (cui, parentNodes.toString(), sensitiveCategories, parentNode);
				}
			}
		}
		
		return assembleViewResult (cui, parentNodes.toString(), sensitiveCategories, "");
	}

	/**
	 * Find all parent nodes of a particular concept
	 * @param cui
	 * @param terminology
	 */
	public List<String> getAllParentNodes (String cui, String codeSystem) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT ?notation WHERE { ");
		buffer.append("?s skos:notation ?concept0 . ");
		buffer.append("?s rdfs:subClassOf* ?mid . ");
		buffer.append("?mid skos:notation ?notation . ");
		buffer.append(" }");
		//Test cui=F16.24
		List<String> parentNodes = new ArrayList<String>();
		parentNodes = jenaBasedSemanticService.queryTargetConeptViaRelation(codeSystem, cui, buffer.toString());
		
		return parentNodes;
	}

	/**
	 * Assemble the result view
	 * @param source
	 * @param path
	 * @param sensitiveCategories
	 * @param target
	 * @return
	 */
	public ConceptMappingResultModelView assembleViewResult (String source, String path, 
			List<String> sensitiveCategories, String target) {
		
		ConceptMappingResultModelView conceptMappingResultModelView = new ConceptMappingResultModelView();
		
		conceptMappingResultModelView.setSourceConcept(source);
		conceptMappingResultModelView.setPath(path);
		conceptMappingResultModelView.setTargetConcept(target);
		conceptMappingResultModelView.setSensitiveCategories(sensitiveCategories);
		
		return conceptMappingResultModelView;
		
	}
}
