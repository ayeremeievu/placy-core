package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.CountryModel;
import com.placy.placycore.core.model.DivisionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface DivisionRepository extends JpaRepository<DivisionModel, Integer> {

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    Optional<DivisionModel> findFirstByCode(String code);
}
