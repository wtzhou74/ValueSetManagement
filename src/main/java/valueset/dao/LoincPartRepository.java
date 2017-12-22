package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.LoincPart;

public interface LoincPartRepository extends CrudRepository<LoincPart, Long>{

	@Query("select u from LoincPart u where u.parttypename = 'COMPONENT' ")
	public List<LoincPart> findComponentCodes ();
}
