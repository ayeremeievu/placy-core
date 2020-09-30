package com.placy.placycore.core.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class CityService {
    private static final int CACHE_MAX_SIZE = 10000;

    @Autowired
    private CityRepository cityRepository;

    private LoadingCache<CityCacheKey, Optional<CityModel> > citiesCache;

    @PostConstruct
    public void init() {
        citiesCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(getLoader());
    }

    private CacheLoader<CityCacheKey, Optional<CityModel> > getLoader() {
        return new CacheLoader<CityCacheKey, Optional<CityModel>>() {
            @Override
            public Optional<CityModel> load(CityCacheKey cityCacheKey) {
                return doGetCityByNameAndDivision(cityCacheKey.getName(), cityCacheKey.getDivisionModel());
            }
        };
    }

    public Optional<CityModel> getCityByNameAndDivision(String name, DivisionModel divisionModel) {
        CityCacheKey cityCacheKey = new CityCacheKey(name, divisionModel);

        return citiesCache.getUnchecked(cityCacheKey);
    }

    public Optional<CityModel> doGetCityByNameAndDivision(String name, DivisionModel divisionModel) {
        int divisionId = divisionModel.getId();

        return cityRepository.getFirstByCityNameAndDivisionId(name, divisionId);
    }

    public boolean existsCityByNameAndDivision(String name, DivisionModel divisionModel) {
        return getCityByNameAndDivision(name, divisionModel).isPresent();
    }

    public List<CityModel> getAllCitiesByNameDivisionCountry(String name, String divisionCode, String countryIso) {
        return cityRepository.getByCityNameAndDivisionCodeAndDivisionCountryIso(name, divisionCode, countryIso);
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

    private static class CityCacheKey {
        private String name;
        private DivisionModel divisionModel;

        public CityCacheKey(String name, DivisionModel divisionModel) {
            this.name = name;
            this.divisionModel = divisionModel;
        }

        public String getName() {
            return name;
        }

        public DivisionModel getDivisionModel() {
            return divisionModel;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CityCacheKey that = (CityCacheKey) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(divisionModel, that.divisionModel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, divisionModel);
        }
    }
}
