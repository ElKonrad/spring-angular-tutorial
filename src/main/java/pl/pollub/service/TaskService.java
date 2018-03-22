package pl.pollub.service;

import pl.pollub.domain.Task;

public interface TaskService {

    Iterable<Task> list();

    Task save(Task task);
}
