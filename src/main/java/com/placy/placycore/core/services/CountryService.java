package com.placy.placycore.core.services;

import com.placy.placycore.core.model.CountryModel;
import com.placy.placycore.core.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public Optional<CountryModel> getCountryByIso(String iso) {
        return countryRepository.getFirstByIso(iso);
    }

    public CountryRepository getCountryRepository() {
        return countryRepository;
    }

    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
}
