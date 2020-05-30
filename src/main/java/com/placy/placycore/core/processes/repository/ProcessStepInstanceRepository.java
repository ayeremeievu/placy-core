package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ProcessStepInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface ProcessStepInstanceRepository extends JpaRepository<ProcessStepInstanceModel, String> {

}
