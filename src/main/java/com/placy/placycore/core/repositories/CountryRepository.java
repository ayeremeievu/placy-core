package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.CountryModel;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface CountryRepository extends JpaRepository<CountryModel, String> {
    Optional<CountryModel> getFirstByIso(String iso);
}
