package valueset.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import valueset.dao.TaskRepository;
import valueset.model.Task;

@Service
@Transactional
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	//find all the items of related type
	public List<Task> findAll() {
		List<Task> tasks = new ArrayList<>();
		//get the items through method offered by crudRepository
		for (Task task: taskRepository.findAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	
	//find the items by giving ID
	public Task findTask(int id) {
		return taskRepository.findOne(id);
	}
	
	//save one record into db
	public void save(Task task) {
		taskRepository.save(task);
	}
	
	//delete one record by giving id
	public void delete (int id) {
		taskRepository.delete(id);
	}
}
