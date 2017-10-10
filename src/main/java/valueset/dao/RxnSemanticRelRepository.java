package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.RxnSemanticRelations;

/**
 * @author ZHOU WENTAO
 *
 */
public interface RxnSemanticRelRepository extends CrudRepository<RxnSemanticRelations, Integer>{

	//Find semantic relations for a specified TTY
	@Query("select u from RxnSemanticRelations u where u.source_termtype = ?1")
	public List<RxnSemanticRelations> findSemanticRel4SpecifiedSourceTTY (String sourceTTY);
}
