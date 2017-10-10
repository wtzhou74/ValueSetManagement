package valueset.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import valueset.dao.RxnSemanticRelRepository;
import valueset.model.dbModel.RxnSemanticRelations;

@Service
@Transactional
public class RxnSemanticRelationService {

	private final RxnSemanticRelRepository rxnSemanticRelRepo;
	
	public RxnSemanticRelationService (RxnSemanticRelRepository rxnSemanticRelRepo) {
		this.rxnSemanticRelRepo = rxnSemanticRelRepo;
	}
	/**
	 * Fina semantic relations for a specified source term type
	 * @param sourceTTY
	 * @return
	 */
	public List<RxnSemanticRelations> findSemanticRelations (String sourceTTY) {
		
		List<RxnSemanticRelations> semanticRelations = new ArrayList<RxnSemanticRelations>();
		semanticRelations = rxnSemanticRelRepo.findSemanticRel4SpecifiedSourceTTY(sourceTTY);
		return semanticRelations;
	}
	
}
