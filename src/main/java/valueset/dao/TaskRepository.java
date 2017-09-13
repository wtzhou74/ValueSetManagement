package valueset.dao;

import org.springframework.data.repository.CrudRepository;

import valueset.model.Task;

/**
 * @author ZHOU WENTAO
 *
 */
public interface TaskRepository extends CrudRepository<Task, Integer>{

}
