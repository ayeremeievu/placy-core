package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.TaskResourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface TaskResourcesRepository extends JpaRepository<TaskResourceModel, String> {

    List<TaskResourceModel> findAllByTaskNull();
}
