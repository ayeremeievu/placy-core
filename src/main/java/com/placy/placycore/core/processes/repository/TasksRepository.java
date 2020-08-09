package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface TasksRepository extends JpaRepository<TaskModel, String> {
    Optional<TaskModel> getFirstByCodeAndTaskResourceResourceImportVersion(String code, int version);

    List<TaskModel> getAllByTaskResourceResourceImportVersion(int version);
}
