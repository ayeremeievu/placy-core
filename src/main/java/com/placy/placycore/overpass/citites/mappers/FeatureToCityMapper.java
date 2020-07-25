package com.placy.placycore.overpass.citites.mappers;

import com.placy.placycore.overpass.citites.data.OverpassCityResponseData;
import com.placy.placycore.overpasscore.mappers.FeatureMapper;
import org.geojson.Feature;

/**
 * @author ayeremeiev@netconomy.net
 */
public class FeatureToCityMapper implements FeatureMapper<OverpassCityResponseData> {
    private final static String NAME_PROPERTY = "name";

    @Override
    public OverpassCityResponseData map(Feature feature) {
        String cityName = feature.getProperty(NAME_PROPERTY);
        return new OverpassCityResponseData(cityName);
    }
}
