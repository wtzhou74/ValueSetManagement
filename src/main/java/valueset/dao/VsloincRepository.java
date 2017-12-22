package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.ValueSetCategory;
import valueset.model.dbModel.Vsloinc;

public interface VsloincRepository extends CrudRepository<Vsloinc, Long>{

	@Query("select u from Vsloinc u where u.code like 'LP%'")
	public List<Vsloinc> findLpCodes ();
	@Query("select u from Vsloinc u where u.code not like 'LP%'")
	public List<Vsloinc> findStandardLoinc();
}
