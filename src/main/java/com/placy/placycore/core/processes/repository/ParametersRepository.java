package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.TaskParameterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface ParametersRepository extends JpaRepository<TaskParameterModel, String> {


}
