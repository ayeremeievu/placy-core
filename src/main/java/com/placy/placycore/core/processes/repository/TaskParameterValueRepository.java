package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface TaskParameterValueRepository extends JpaRepository<TaskParameterValueModel, String> {
}
