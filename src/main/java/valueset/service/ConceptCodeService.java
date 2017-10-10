package valueset.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import valueset.dao.CodeSystemRepository;
import valueset.dao.ConceptCodeRepository;
import valueset.model.dbModel.CodeSystem;
import valueset.model.dbModel.CodeSystemVersion;
import valueset.model.dbModel.ConceptCode;
import valueset.model.dbModel.ValueSet;
import valueset.model.dbModel.ValueSetCategory;
import valueset.model.modelView.ConceptCodeModelView;

/**
 * Service used to deal with concept code, code system
 * @author ZHOU WENTAO
 *
 */
@Service
@Transactional
public class ConceptCodeService {

	private final CodeSystemRepository codeSystemRep;
	private final ConceptCodeRepository conceptCodeRep;
	
	public ConceptCodeService(CodeSystemRepository codeSystemRep, ConceptCodeRepository conceptCodeRep) {
		this.codeSystemRep = codeSystemRep;
		this.conceptCodeRep = conceptCodeRep;
	}
	
	/**
	 * Find all related concept code by given code system name
	 * @param parameterName
	 * @param parameterValue
	 * @return
	 */
	public List<ConceptCodeModelView> findConceptCodeWithTerminology(String parameterName,
			String parameterValue) {
		List<ConceptCodeModelView> conceptCodeModels = new ArrayList<ConceptCodeModelView>();
		List<CodeSystem> codeSystems = new ArrayList<CodeSystem>();
		if (parameterName.equalsIgnoreCase("codeSystemName")) {
			//Actually, one terminology corresponding to one code system, so, here, List is not needed
			codeSystems = codeSystemRep.findCodeSystemsByName(parameterValue);
		}
		
		if (parameterName.equalsIgnoreCase("codeSystemOid")) {
			codeSystems = codeSystemRep.findCodeSystemsByOid(parameterValue);
		}
		
		
		if (null != codeSystems
				&& codeSystems.size() > 0) {
			for(CodeSystem codeSystem : codeSystems) {
				queryDB2GetConceptCodes(codeSystem, conceptCodeModels);
			}
		}
		return conceptCodeModels;
	}
	
	/**
	 * Query DB to get all concept codes with given terminology
	 * @param codeSystems
	 * @param conceptCodeModels
	 */
	public void queryDB2GetConceptCodes(CodeSystem codeSystem, List<ConceptCodeModelView> conceptCodeModels) {
		ConceptCodeModelView conceptCodeModel = new ConceptCodeModelView();
		conceptCodeModel.setCodeSystem(codeSystem);
		//Need to correct the following code if different code system veriosn should be considered
		for (CodeSystemVersion codeSystemVersion : codeSystem.getCodeSystemVersions()) {
			conceptCodeModel.setConceptCodes((List<ConceptCode>)codeSystemVersion.getConceptCodes());
		}
		conceptCodeModels.add(conceptCodeModel);
	}
	
	/**
	 * Find Sensitive information for a specified concept code
	 * @param conceptCode
	 * @return
	 */
	public List<String> findSensitiveOfSpecifiedConcept (String conceptCode) {
		ConceptCode concept = new ConceptCode ();
		//One concept could refer to more than one category
		List<String> sensitiveCategorys = new ArrayList<String>();
		List<ConceptCode> concepts = conceptCodeRep.findConceptByConceptCode(conceptCode);
		if (concepts.isEmpty()) {
			System.out.println("NO SENSITIVE CATEGORY FOUND IN VS");
			return sensitiveCategorys;
		}
		//Each code refers to one ConceptCode Object
		concept = concepts.get(0);
		List<ValueSet> valueSets = concept.getValueSets();
		for (ValueSet valueSet : valueSets) {
			ValueSetCategory valueSetCategory = valueSet.getValueSetCategory();
			sensitiveCategorys.add(valueSetCategory.getCode());
		}
		return sensitiveCategorys;
	}
	
	
}
