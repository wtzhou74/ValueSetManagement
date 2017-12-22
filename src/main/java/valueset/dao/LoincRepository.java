package valueset.dao;

import org.springframework.data.repository.CrudRepository;

import valueset.model.dbModel.Loinc;

public interface LoincRepository extends CrudRepository<Loinc, String>{

}
