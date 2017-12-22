package valueset.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.jena.ext.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import valueset.dao.LoincGroupRepository;
import valueset.dao.LoincPartLinkRepository;
import valueset.dao.LoincPartRepository;
import valueset.dao.LoincRepository;
import valueset.dao.MultiAxialHierarchyRepository;
import valueset.dao.VsloincRepository;
import valueset.model.dbModel.LoincGroup;
import valueset.model.dbModel.LoincPart;
import valueset.model.dbModel.LoincPartLink;
import valueset.model.dbModel.MultiAxialHierarchy;
import valueset.model.dbModel.Vsloinc;

@Service
@Transactional
public class LoincCodesTest {

	@Autowired
	private VsloincRepository vsloincRepository;
	@Autowired
	private LoincPartRepository loincPartRepository;
	@Autowired
	private LoincPartLinkRepository loincPartLinkRepository;
	@Autowired
	private LoincGroupRepository loincGroupRepository;
	@Autowired
	private ConceptCodeService conceptCodeService;
	@Autowired
	private MultiAxialHierarchyRepository multiAxialHierarchyRepository;
	
	public void getLoincNumber () {
		List<Vsloinc> standardLoincs = vsloincRepository.findStandardLoinc();
		//List<Vsloinc> lpLoincs = vsloincRepository.findLpCodes();
		coverLPinVs(standardLoincs);
		//List<LoincPart> componentCodes = loincPartRepository.findComponentCodes();
		System.out.println("LOAD SUCCESS");
	}
	
	//Get groups
	public List<LoincGroup> getGroupofLoincOfVS (List<Vsloinc> standardLoincs) {
		List<LoincGroup> groups = new ArrayList<LoincGroup>();
		for (Vsloinc vsloinc : standardLoincs) {
			List<LoincGroup> loincGroups = loincGroupRepository.findGroupIDofLoincCode(vsloinc.getCode());
			groups.addAll(loincGroups);
		}
		
		return groups;
		
	}
	
	//Check all corresponding LPs are in LPs of VS
	public void coverLPinVs (List<Vsloinc> standardLoincs) {
		List<String> lps = new ArrayList<String>();//All corresponding LPs of vsLoincs
		List<LoincPartLink> loincPartLinks = Lists.newArrayList(loincPartLinkRepository.findAll());
		//loincPartLinks.addAll(Lists.newArrayList(loincPartLinkRepository.findAll()));//Guava Library
		//loincPartLinks.addAll(loincPartLinkRepository.findAllofComponent());
		
		//Get corresponding LoincPartLink
		for (Vsloinc vsloinc : standardLoincs) {
			for (LoincPartLink loincPartLink : loincPartLinks) {
				if (vsloinc.getCode().equalsIgnoreCase(loincPartLink.getLoincnumber())) {
					if (!lps.contains(loincPartLink.getPartnumber())) {
						lps.add(loincPartLink.getPartnumber());
					}
				}
			}
		}//366
		List<String> loincs = new ArrayList<String>();
		for (Vsloinc vsloinc : standardLoincs) {
			loincs.add(vsloinc.getCode());
		}
		int i =0;
		int j =0;
		List<String> finalResult = new ArrayList<String>();
		for (String lp : lps) {
			List<String> temploincs = new ArrayList<String>();
			for (LoincPartLink loincPartLink : loincPartLinks) {
				if (lp.equalsIgnoreCase(loincPartLink.getPartnumber())) {
					if (!finalResult.contains(loincPartLink.getLoincnumber())) {
						finalResult.add(loincPartLink.getLoincnumber());
						temploincs.add(loincPartLink.getLoincnumber());//record all loincs of the specified LP
						j++;
					}
					
				}
			}
			for (String loinc : temploincs) {
				if (!loincs.contains(loinc)) {
					i++;
					System.out.println("Not included LOINC of LPs, " + "LOINC: " + loinc + " LP: " + lp);
					break;
				}
			}
		}
		System.out.println(i);
		System.out.println(j);
		List<String> notCoveredLp = new ArrayList<String>();
		List<Vsloinc> vsLPs = vsloincRepository.findLpCodes();
		
		//get ALL components
		List<String> vsComponents = getLPofComponent(vsLPs);
		
		for (String vsComponent : vsComponents) {
			if (!lps.contains(vsComponent)) {
				notCoveredLp.add(vsComponent);
			}
		}
		
		return;
	}
	
	public List<String> getLPofComponent (List<Vsloinc> Lps) {
		List<String> vsComponents = new ArrayList<String>();
		List<LoincPart> lpComponents = loincPartRepository.findComponentCodes();
		List<String> componets = new ArrayList<String>();
		for (LoincPart loincPart : lpComponents) {
			componets.add(loincPart.getPartnumber());
		}
		for (Vsloinc lp : Lps) {
			if (componets.contains(lp.getCode())) {
				vsComponents.add(lp.getCode());
			}
		}
		
		return vsComponents;
	}
	
	public List<String> CheckComponent (List<String> Lps) {
		List<String> vsComponents = new ArrayList<String>();
		List<LoincPart> lpComponents = loincPartRepository.findComponentCodes();
		List<String> componets = new ArrayList<String>();
		List<String> special = new ArrayList<String>();
		for (LoincPart loincPart : lpComponents) {
			componets.add(loincPart.getPartnumber());
		}
		for (String lp : Lps) {
			if (componets.contains(lp)) {
				vsComponents.add(lp);//446
			} else {
				special.add(lp);
			}
		}
		
		return vsComponents;
	}
	
	public List<String> getLPofNonComponent (List<Vsloinc> Lps) {
		List<String> vsNonComponents = new ArrayList<String>();
		List<LoincPart> lpComponents = loincPartRepository.findComponentCodes();
		List<String> componets = new ArrayList<String>();
		for (LoincPart loincPart : lpComponents) {
			componets.add(loincPart.getPartnumber());
		}
		for (Vsloinc lp : Lps) {
			if (!componets.contains(lp.getCode())) {
				vsNonComponents.add(lp.getCode());
			}
		}
		
		return vsNonComponents;
	}
	
	//TODO Check what loinc codes are included in the same sensitive category, 
	//which group are they in
	public void senCategoryGroup (List<Vsloinc> standardLoincs) {
		Map<String, List<String>> categoryCodeMap = new HashMap<String, List<String>>();
		for (Vsloinc vsloinc : standardLoincs) {
			List<String> categories = conceptCodeService.findSensitiveOfSpecifiedConcept(vsloinc.getCode());
			for (String category : categories) {
				if (categoryCodeMap.containsKey(category)) {
					categoryCodeMap.get(category).add(vsloinc.getCode());
				} else {
					List<String> codes = new ArrayList<String>();
					codes.add(vsloinc.getCode());
					categoryCodeMap.put(category, codes );
				}
			}
			
		}
		
	}
	
	//whether or immediate parent node is component
	public List<String> componentOfImmediateParent(List<String> nonComponentLps) {
		List<MultiAxialHierarchy> multiAxialHierarchies = Lists.newArrayList(multiAxialHierarchyRepository.findAll());
		List<String> immediateParents = new ArrayList<String>();
		for (String nonComponentLp : nonComponentLps) {
			for (MultiAxialHierarchy multiAxialHierarchy : multiAxialHierarchies) {
				if (multiAxialHierarchy.getCode().equalsIgnoreCase(nonComponentLp)) {
					String immediateParent = multiAxialHierarchy.getImmediateParent();
					if (immediateParents.contains(immediateParent)) {
						System.out.println("DUPLICATE: " + nonComponentLp + " immediate parent: " + immediateParent);
					}
					immediateParents.add(immediateParent);
					break;
				}
			}
		}
		return immediateParents;
	}
	
	//Solution based on LOINC Ontology
	public void getLoincPartGraph () {
		
	}
	
	
}
