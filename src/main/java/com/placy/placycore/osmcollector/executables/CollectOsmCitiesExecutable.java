package com.placy.placycore.osmcollector.executables;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.CountryModel;
import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.core.services.CountryService;
import com.placy.placycore.core.services.DivisionService;
import com.placy.placycore.overpass.citites.data.OverpassCityResponseData;
import com.placy.placycore.overpass.citites.services.OverpassCitiesService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class CollectOsmCitiesExecutable implements ExecutableBean {

    private final static Logger LOG = LoggerFactory.getLogger(CollectOsmCitiesExecutable.class);

    private final static String COUNTRY_ISO_PARAM = "countryIso";

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private OverpassCitiesService overpassCitiesService;

    @Override
    public Object execute(Map<String, Object> params) {
        CountryModel countryModel = getCountryModel(params);

        List<DivisionModel> divisions = countryModel.getDivisions();

        for (DivisionModel division : divisions) {
            LOG.info("Importing cities for division {} with name {} and country with iso {}",
                     division.getId(),
                     division.getName(),
                     countryModel.getIso());

            List<OverpassCityResponseData> overpassCitiesResponses =
                overpassCitiesService.getOverpassCities(countryModel.getNicename(), division.getName());

            if (overpassCitiesResponses == null) {
                overpassCitiesResponses = new ArrayList<>();
            }

            overpassCitiesResponses = filterCitiesResponse(overpassCitiesResponses);

            List<CityModel> cities = overpassCitiesResponses
                .stream()
                .map(overpassCityResponseData -> buildCityModel(overpassCityResponseData, division))
                .collect(Collectors.toList());

            List<CityModel> citiesToSave = cities
                .stream()
                .filter(cityModel -> !cityService.existsCityByNameAndDivision(cityModel.getCityName(), division))
                .collect(Collectors.toList());

            cityService.saveAll(citiesToSave);

            try {
                TimeUnit.MINUTES.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOG.info("Returned {} cities from Overpass for division {}. New {} cities are added",
                     overpassCitiesResponses.size(),
                     division.getId(),
                     citiesToSave.size());
        }

        return null;
    }

    private List<OverpassCityResponseData> filterCitiesResponse(List<OverpassCityResponseData> overpassCitiesResponses) {
        return overpassCitiesResponses.stream()
            .filter(this::hasName)
            .collect(Collectors.toList());
    }

    private boolean hasName(OverpassCityResponseData overpassCityResponseData) {
        return StringUtils.isNoneBlank(overpassCityResponseData.getName());
    }

    private CityModel buildCityModel(OverpassCityResponseData overpassCityResponseData, DivisionModel division) {
        CityModel cityModel = new CityModel();

        cityModel.setCityName(overpassCityResponseData.getName());
        cityModel.setDivision(division);

        return cityModel;
    }

    private CountryModel getCountryModel(Map<String, Object> params) {
        String countryIso = (String) params.get(COUNTRY_ISO_PARAM);

        Optional<CountryModel> countryOptional = countryService.getCountryByIso(countryIso);

        if (!countryOptional.isPresent()) {
            throw new IllegalArgumentException(String.format("There is no such country with iso '%s'", countryIso));
        }

        return countryOptional.get();
    }

    public CountryService getCountryService() {
        return countryService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public OverpassCitiesService getOverpassCitiesService() {
        return overpassCitiesService;
    }

    public void setOverpassCitiesService(OverpassCitiesService overpassCitiesService) {
        this.overpassCitiesService = overpassCitiesService;
    }

    public DivisionService getDivisionService() {
        return divisionService;
    }

    public void setDivisionService(DivisionService divisionService) {
        this.divisionService = divisionService;
    }
}
