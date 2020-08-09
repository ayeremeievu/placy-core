package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.ResourceImportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface ProcessResourcesRepository extends JpaRepository<ProcessResourceModel, String> {
    @Query(value = "SELECT prm "
        + "FROM ProcessResourceModel prm "
        + "WHERE prm.latestDateProcessed IS NULL OR "
        + "prm.latestDateProcessed < prm.latestDateImported ")
    List<ProcessResourceModel> getAllProcessesToProcess();

    Optional<ProcessResourceModel> getFirstByResourceName(String resource);

    List<ProcessResourceModel> getAllByResourceImport(ResourceImportModel resourceImportModel);

    Optional<ProcessResourceModel> getFirstByProcess(ProcessModel process);
}
