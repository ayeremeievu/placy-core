package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.constants.CollectorConstants;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.*;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.core.services.DivisionService;
import com.placy.placycore.core.services.OriginService;
import com.placy.placycore.reviewscore.model.PlaceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class YelpPlaceRawModelToPlaceModelMapper extends AbstractSimpleMapper<YelpPlaceRawModel, PlaceModel> {
    private static final Logger LOG = LoggerFactory.getLogger(YelpPlaceRawModelToPlaceModelMapper.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private OriginService originService;

    /**
     * @return null, if mapping is not possible.
     * */
    @Override
    public PlaceModel map(YelpPlaceRawModel yelpPlaceRawModel) {
        long milisBeforeMap = System.currentTimeMillis();
        PlaceModel placeModel = new PlaceModel();

        String state = yelpPlaceRawModel.getState();

        long milisBeforeDivision = System.currentTimeMillis();
        Optional<DivisionModel> division = divisionService.getDivisionByCode(state.trim());
        long tookToFetchDivision = System.currentTimeMillis() - milisBeforeDivision;

        if(division.isEmpty()) {
//            LOG.debug("Yelp place with id '{}' has unknown division state '{}'", yelpPlaceRawModel.getPk().getId(), state);
            return null;
        }

        DivisionModel divisionModel = division.get();

        String city = yelpPlaceRawModel.getCity();

        long milisBeforeCity = System.currentTimeMillis();
        Optional<CityModel> cityByNameAndDivision = cityService.getCityByNameAndDivision(city.trim(), divisionModel);
        long tookToFetchCity = System.currentTimeMillis() - milisBeforeCity;

        if(cityByNameAndDivision.isEmpty()) {
//            LOG.debug("Yelp place with id '{}' has unknown city '{}'", yelpPlaceRawModel.getPk().getId(), state);
            return null;
        }


        long milisBeforeTheSimpleProperties = System.currentTimeMillis();
        CityModel cityModel = cityByNameAndDivision.get();

        populateSimplePlace(yelpPlaceRawModel, placeModel);
        long tookToPopulateSimpleProperties = System.currentTimeMillis() - milisBeforeTheSimpleProperties;

        long milisBeforeAddress = System.currentTimeMillis();
        populateAddress(placeModel, yelpPlaceRawModel, cityModel);
        long tookToPopulateAddress = System.currentTimeMillis() - milisBeforeAddress;

        long tookToMap = System.currentTimeMillis() - milisBeforeMap;

        if(tookToMap > 50) {
            LOG.info("Took to map in total {}. Took time to get division {} milis. " +
                            "Took time to get city {} milis. Took to populate simple attributes {} milis. Took to populate address {} milis.",
                    tookToMap, tookToFetchDivision, tookToFetchCity, tookToPopulateSimpleProperties, tookToPopulateAddress);
        }

        return placeModel;
    }

    private void populateSimplePlace(YelpPlaceRawModel yelpPlaceRawModel, PlaceModel placeModel) {
        OriginModel originModel = originService.geMandatoryByCodeCached(CollectorConstants.Yelp.ORIGIN_CODE);

        placeModel.setOrigin(originModel);
        placeModel.setName(yelpPlaceRawModel.getName());
        placeModel.setLatitude(yelpPlaceRawModel.getLatitude());
        placeModel.setLongitude(yelpPlaceRawModel.getLongitude());
        placeModel.setOriginCode(yelpPlaceRawModel.getPk().getId());
    }

    private void populateAddress(PlaceModel placeModel, YelpPlaceRawModel yelpPlaceRawModel, CityModel cityModel) {
        AddressModel addressModel = new AddressModel();

        addressModel.setAddressLine(yelpPlaceRawModel.getAddress());
        addressModel.setPostalCode(yelpPlaceRawModel.getPostalCode());
        addressModel.setCity(cityModel);

        placeModel.setAddress(addressModel);
    }
}
