package javau7.bg.manager.controllers;

import javau7.bg.manager.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Create
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        Task createdTask = taskService.create(task);
        return ResponseEntity.ok(createdTask);
    }

    // Read
    @GetMapping("/{id}")
    public ResponseEntity<Task> read(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.update(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
