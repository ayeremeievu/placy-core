package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.CountryModel;
import com.placy.placycore.core.model.DivisionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface DivisionRepository extends JpaRepository<DivisionModel, Integer> {

}
