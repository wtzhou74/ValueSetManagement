package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.LoincGroup;

public interface LoincGroupRepository extends CrudRepository<LoincGroup, Long>{

	@Query("select u from LoincGroup u where u.loincnumber=?1")
	public List<LoincGroup> findGroupIDofLoincCode (String loincCode);
}
