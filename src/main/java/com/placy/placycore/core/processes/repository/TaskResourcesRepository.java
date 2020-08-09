package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface TaskResourcesRepository extends JpaRepository<TaskResourceModel, String> {

    @Query(value = "SELECT trm "
        + "FROM TaskResourceModel trm "
        + "WHERE trm.latestDateProcessed IS NULL OR "
        + "trm.latestDateProcessed < trm.latestDateImported ")
    List<TaskResourceModel> findAllTasksToProcess();

    Optional<TaskResourceModel> findFirstByResourceName(String resource);

    List<TaskResourceModel> findAllByResourceImport(ResourceImportModel resourceImportModel);

    Optional<TaskResourceModel> findFirstByTask(TaskModel taskModel);
}
