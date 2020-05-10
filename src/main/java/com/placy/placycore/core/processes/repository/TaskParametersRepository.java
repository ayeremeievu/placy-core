package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.TaskParameterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface TaskParametersRepository extends JpaRepository<TaskParameterModel, String> {
    Optional<TaskParameterModel> getFirstByCodeAndTaskCode(String code, String taskCode);
}
