package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TasksService {
    @Autowired
    private TasksRepository tasksRepository;

    public void save(TaskModel taskModel) {
        tasksRepository.save(taskModel);
    }

    public void saveAll(List<TaskModel> taskModelList) {
        tasksRepository.saveAll(taskModelList);
    }

    public Optional<TaskModel> getTaskByCodeOptional(String code) {
        return tasksRepository.findFirstByCode(code);
    }

    public List<TaskModel> getAllTasks() {
        return tasksRepository.findAll();
    }
}
