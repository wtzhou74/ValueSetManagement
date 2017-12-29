package valueset.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.model.modelView.ConceptMappingResultModelView;
import valueset.utils.ConstantUtil;

/**
 * Business logic to handle the identification of sensitive category of
 * ICD-9/10CM concepts
 * 
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
	 * 
	 * @param cui
	 * @param codeSystem
	 * @return
	 */
	public ConceptMappingResultModelView identifySenCategoryOfICDConcept(String cui, String codeSystem) {
		List<String> sensitiveCategories = new ArrayList<String>();

		// Check whether the current cui is available in VS
		sensitiveCategories = conceptCodeService.findSensitiveOfSpecifiedConcept(cui);
		if (sensitiveCategories.size() > 0) {
			return assembleViewResult(cui, "", sensitiveCategories, cui);
		}
		
		//SVC code, e.g. H0004HQ53
		if (ConstantUtil.SVC.equalsIgnoreCase(codeSystem)) {
			if (cui.length() >= 7
					&& (ConstantUtil.MODIFIER_HF.equalsIgnoreCase(cui.substring(5, 7))
							|| ConstantUtil.MODIFIER_HQ.equalsIgnoreCase(cui.substring(5, 7)))) {
				sensitiveCategories.add("ETH");
				return assembleViewResult(cui, "", sensitiveCategories, cui);
			} else {
				cui = cui.substring(0, 5);//Get first 5 characters as CPT code
				codeSystem = ConstantUtil.CPT;
			}
		}

		// Check which level of given CUI by quering HCPCS ontolgoy which only
		// contains codes of Level II
		if (ConstantUtil.HCPCS.equalsIgnoreCase(codeSystem)) {
			// if it is level I, then replace codeSystem with CPT
			if (!levelofHCPCS(cui, codeSystem)) {
				codeSystem = ConstantUtil.CPT;
			}
		}
		// Check the cases of LOINC
		if (ConstantUtil.LOINC.equalsIgnoreCase(codeSystem)) {
			return getSensitiveCategoryOfLoinc(sensitiveCategories, cui, codeSystem);

		}
		// Its parent nodes will be checked if current node is not available in
		// VS
		List<String> parentNodes = getAllParentNodes(cui, codeSystem);

		System.out.println("PATH: " + parentNodes.toString());
		if (parentNodes.size() > 0) {
			// Traverse each parent node until the sensitive category was find
			for (String parentNode : parentNodes) {
				sensitiveCategories = conceptCodeService.findSensitiveOfSpecifiedConcept(parentNode);
				if (sensitiveCategories.size() > 0) {
					return assembleViewResult(cui, parentNodes.toString(), sensitiveCategories, parentNode);
				}
			}
		}

		return assembleViewResult(cui, parentNodes.toString(), sensitiveCategories, "");
	}

	/**
	 * Check the sensitive categories of LOINCs
	 * 
	 * @param sensitiveCategories
	 * @param cui
	 * @param codeSystem
	 * @return
	 */
	public ConceptMappingResultModelView getSensitiveCategoryOfLoinc(List<String> sensitiveCategories, String cui,
			String codeSystem) {
		List<String> components = findComponentsOfLoinc(cui, codeSystem);
		/* Check the components */
		for (String component : components) {
			sensitiveCategories = conceptCodeService.findSensitiveOfSpecifiedConcept(component);
			if (sensitiveCategories.size() > 0) {
				return assembleViewResult(cui, "", sensitiveCategories, component);
			}
		}
		// check the LPs between component and LOINC
		/*
		 * List<String> lps = new ArrayList<String>(); for (String component :
		 * components) { lps = getImmediateSubClassOfComponent (component,
		 * codeSystem); for (String lp : lps) { sensitiveCategories =
		 * conceptCodeService.findSensitiveOfSpecifiedConcept(lp); if
		 * (sensitiveCategories.size() > 0) { return assembleViewResult (cui,
		 * "", sensitiveCategories, lp); } } }
		 */
		// Based on multi-axial hierarchy, immediate parent will be checked
		List<String> lps = new ArrayList<String>();
		lps = getImmediateSubClassOfComponent(cui, codeSystem);
		for (String lp : lps) {
			sensitiveCategories = conceptCodeService.findSensitiveOfSpecifiedConcept(lp);
			if (sensitiveCategories.size() > 0) {
				return assembleViewResult(cui, "", sensitiveCategories, lp);
			}
		}
		// TODO check the codes with same component
		// if ()
		return assembleViewResult(cui, "", sensitiveCategories, "");
	}

	/**
	 * Find all parent nodes of a particular concept
	 * 
	 * @param cui
	 * @param terminology
	 */
	public List<String> getAllParentNodes(String cui, String codeSystem) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DISTINCT ?notation WHERE { ");
		// buffer.append("SELECT ?s COUNT(?mid) WHERE { ");
		buffer.append("?s skos:notation ?concept0 . ");
		buffer.append("?s rdfs:subClassOf* ?mid . ");
		// buffer.append("?s rdfs:subClassOf ?mid .");
		buffer.append("?mid skos:notation ?notation . ");
		// buffer.append("GROUP BY ?s .");
		buffer.append(" }");
		// Test cui=F16.24
		List<String> parentNodes = new ArrayList<String>();
		parentNodes = jenaBasedSemanticService.queryTargetConeptViaRelation(codeSystem, cui, buffer.toString());

		return parentNodes;
	}

	/**
	 * find the components of given LOINC code
	 */
	public List<String> findComponentsOfLoinc(String cui, String codeSystem) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ?notation WHERE { ");
		buffer.append("?s skos:notation ?concept0 .");
		buffer.append("?s loinc:has_component ?component .");
		buffer.append("?component skos:notation ?notation .");
		buffer.append(" }");
		List<String> components = new ArrayList<String>();
		components = jenaBasedSemanticService.queryTargetConeptViaRelation(codeSystem, cui, buffer.toString());
		return components;
	}

	/**
	 * Find the LPs between component and LOINCs
	 * 
	 * @param component
	 * @param codeSystem
	 * @return
	 */
	public List<String> getImmediateSubClassOfComponent(String component, String codeSystem) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ?notation WHERE { ");
		buffer.append("?s skos:notation ?concept0 .");
		buffer.append("?s rdfs:subClassOf ?lp .");
		buffer.append("?lp skos:notation ?notation .");
		buffer.append(" }");
		List<String> lps = new ArrayList<String>();
		lps = jenaBasedSemanticService.queryTargetConeptViaRelation(codeSystem, component, buffer.toString());
		return lps;
	}

	/**
	 * Check whether the level HCPCS code is I or II. TRUE - LEVEL II, FALSE -
	 * Level I
	 * 
	 * @param cui
	 * @param codeSystem
	 * @return
	 */
	public boolean levelofHCPCS(String cui, String codeSystem) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ?cui WHERE { ");
		buffer.append("?s skos:notation ?concept0 .");
		buffer.append("?s umls:cui ?cui .");
		buffer.append(" }");

		if (jenaBasedSemanticService.queryTargetConeptViaRelation(codeSystem, cui, buffer.toString()).size() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Assemble the result view
	 * 
	 * @param source
	 * @param path
	 * @param sensitiveCategories
	 * @param target
	 * @return
	 */
	public ConceptMappingResultModelView assembleViewResult(String source, String path,
			List<String> sensitiveCategories, String target) {

		ConceptMappingResultModelView conceptMappingResultModelView = new ConceptMappingResultModelView();

		conceptMappingResultModelView.setSourceConcept(source);
		conceptMappingResultModelView.setPath(path);
		conceptMappingResultModelView.setTargetConcept(target);
		conceptMappingResultModelView.setSensitiveCategories(sensitiveCategories);

		return conceptMappingResultModelView;

	}
}
