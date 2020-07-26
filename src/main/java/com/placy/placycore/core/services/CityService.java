package com.placy.placycore.core.services;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public Optional<CityModel> getCityByNameAndDivision(String name, DivisionModel divisionModel) {
        int divisionId = divisionModel.getId();

        return cityRepository.getFirstByCityNameAndDivisionId(name, divisionId);
    }

    public boolean existsCityByNameAndDivision(String name, DivisionModel divisionModel) {
        return getCityByNameAndDivision(name, divisionModel).isPresent();
    }

    public List<CityModel> saveAll(List<CityModel> citites) {
        return cityRepository.saveAll(citites);
    }

    public CityRepository getCityRepository() {
        return cityRepository;
    }

    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
}
