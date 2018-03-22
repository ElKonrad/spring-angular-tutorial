package pl.pollub.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pollub.domain.Task;
import pl.pollub.service.TaskService;

@Controller
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping(value = "")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Iterable<Task>> listTasks() {
        return new ResponseEntity<>(taskService.list(), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.save(task), HttpStatus.CREATED);
    }
}
