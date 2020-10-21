package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface CityRepository extends JpaRepository<CityModel, Integer> {
    // TODO has to check by import and that it was not imported from certain channel yet.
    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    Optional<CityModel> getFirstByCityNameAndDivisionId(String name, int divisionId);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<CityModel> getByCityNameAndDivisionCodeAndDivisionCountryIso(
            String name, String divisionCode, String countryIso);
}
