package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Repository
public interface ProcessResourcesRepository extends JpaRepository<ProcessResourceModel, String> {
    List<ProcessResourceModel> getAllByProcessNull();

    Optional<ProcessResourceModel> getFirstByResourceName(String resource);
}
