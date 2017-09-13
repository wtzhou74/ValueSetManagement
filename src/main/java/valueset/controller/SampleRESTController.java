package valueset.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import valueset.model.Task;
import valueset.service.TaskService;

@RestController
public class SampleRESTController {

	@Autowired
	private TaskService taskService;
	
	/*@GetMapping("/")
	public String sample(String[] args) {
		return "index";

	}*/
	
	/*@GetMapping("/all-tasks")
	public String allTasks () {
		return taskService.findAll().toString();
	}
	
	@GetMapping("/save-task")
	public String saveTask(@RequestParam String name, @RequestParam String desc) {
		Task task = new Task(name, desc, new Date(), false);
		taskService.save(task);
		return "Task saved";
	}

	@GetMapping("/delete-task")
	public String deleteTask(@RequestParam int id) {
		taskService.delete(id);
		return "Task deleted";
	}*/
}
