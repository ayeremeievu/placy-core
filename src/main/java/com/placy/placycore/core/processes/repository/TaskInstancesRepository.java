package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.TaskInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface TaskInstancesRepository extends JpaRepository<TaskInstanceModel, String> {

}
