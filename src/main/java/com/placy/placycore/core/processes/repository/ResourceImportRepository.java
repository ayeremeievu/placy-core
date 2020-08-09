package com.placy.placycore.core.processes.repository;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface ResourceImportRepository extends JpaRepository<ResourceImportModel, String> {
    Optional<ResourceImportModel> findTop1ByOrderByVersionDesc();
}
