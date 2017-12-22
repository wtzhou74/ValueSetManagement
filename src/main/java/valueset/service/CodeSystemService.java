package valueset.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import valueset.dao.CodeSystemRepository;
import valueset.model.dbModel.CodeSystem;
import valueset.model.dbModel.CodeSystemVersion;
import valueset.model.dbModel.ConceptCode;
import valueset.model.modelView.ConceptCodeModelView;

/**
 * The service used to deal with code system and code system version
 * 
 * @author ZHOU WENTAO
 *
 */
@Service
@Transactional
public class CodeSystemService {

	private final CodeSystemRepository codeSystemRep;

	public CodeSystemService(CodeSystemRepository codeSystemRep) {
		this.codeSystemRep = codeSystemRep;
	}

	/**
	 * Find all the coding system
	 * 
	 * @return
	 */
	public List<CodeSystem> findAllCodeSystem() {
		List<CodeSystem> codeSystems = new ArrayList<CodeSystem>();
		for (CodeSystem codeSystem : codeSystemRep.findAll()) {
			codeSystems.add(codeSystem);
		}
		return codeSystems;
	}

	/**
	 * Find coding system version by giving code system ID
	 * 
	 * @param CodeSystemId
	 * @return
	 */
	public List<String> findCodeSystemVersion(Long CodeSystemId) {
		List<String> codeSystemVersions = new ArrayList<String>();
		// get codeSystem by giving code system id
		CodeSystem codeSystem = codeSystemRep.findOne(CodeSystemId);
		if (null != codeSystem && codeSystem.getCodeSystemVersions().size() != 0) {
			for (CodeSystemVersion codeSystemVersion : codeSystem.getCodeSystemVersions()) {
				codeSystemVersions.add(codeSystemVersion.getVersionName());
			}
		}

		return codeSystemVersions;
	}

}
