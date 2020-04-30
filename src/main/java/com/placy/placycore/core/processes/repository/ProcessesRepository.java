package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ProcessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface ProcessesRepository extends JpaRepository<ProcessModel, String> {

}
