package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.ConceptCode;

/**
 * @author ZHOU WENTAO
 *
 */
public interface ConceptCodeRepository extends CrudRepository<ConceptCode, Long>{

	//Find ConceptCode by giving concept code
	@Query("select u from ConceptCode u where u.code = ?1")
	List<ConceptCode> findConceptByConceptCode (String conceptCode);
}
