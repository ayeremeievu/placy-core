package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginRepository extends JpaRepository<OriginModel, String> {

    Optional<OriginModel> findFirstByCode(String code);
}
