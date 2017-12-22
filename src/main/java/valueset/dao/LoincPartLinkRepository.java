package valueset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.LoincPart;
import valueset.model.dbModel.LoincPartLink;

public interface LoincPartLinkRepository extends CrudRepository<LoincPartLink, Long>{


	@Query("select u from LoincPartLink u where u.loincnumber = ?1")
	public List<LoincPartLink> findLPsOfSpecifiedLoincCode (String loincNum);
	
	@Query("select u from LoincPartLink u where u.parttypename = 'COMPONENT' ")
	public List<LoincPartLink> findAllofComponent ();
}
