package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstanceModel, String> {

    Optional<ProcessInstanceModel> getFirstByCode(String processInstanceCode);
}
