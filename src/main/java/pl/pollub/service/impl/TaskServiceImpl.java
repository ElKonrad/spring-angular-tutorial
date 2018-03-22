package pl.pollub.service.impl;

import pl.pollub.domain.Task;
import pl.pollub.repository.TaskRepository;
import org.springframework.stereotype.Service;
import pl.pollub.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Iterable<Task> list() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
