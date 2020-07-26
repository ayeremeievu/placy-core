package com.placy.placycore.overpass.citites.mappers;

import com.placy.placycore.overpass.citites.data.OverpassCityResponseData;
import com.placy.placycore.overpasscore.data.ElementData;
import com.placy.placycore.overpasscore.mappers.OverpassResponseMapper;
import org.geojson.Feature;

/**
 * @author ayeremeiev@netconomy.net
 */
public class OverpassResponseToCityMapper implements OverpassResponseMapper<OverpassCityResponseData> {
    private final static String NAME_PROPERTY = "name";

    @Override
    public OverpassCityResponseData map(ElementData elementData) {
        String cityName = elementData.getTags().getName();
        return new OverpassCityResponseData(cityName);
    }
}
