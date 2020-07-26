package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface CityRepository extends JpaRepository<CityModel, String> {
    // TODO has to check by import and that it was not imported from certain channel yet.
    Optional<CityModel> getFirstByCityNameAndDivisionId(String name, int divisionId);
}
