package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.CodeSystem;

/**
 * @author ZHOU WENTAO
 *
 */
public interface CodeSystemRepository extends CrudRepository<CodeSystem, Long>{

	//find code systems by giving code system name
	@Query("select u from CodeSystem u where (u.code like %?1%)")
	List<CodeSystem> findCodeSystemsByName(String codeSystemName);
	//find code system by giving OID
	@Query("select u from CodeSystem u where (u.codeSystemOid = ?1)")
	List<CodeSystem> findCodeSystemsByOid(String codeSystemOid);
}
