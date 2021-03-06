package com.placy.placycore.overpass.citites.services;

import com.placy.placycore.overpass.citites.data.OverpassCityResponseData;
import com.placy.placycore.overpass.citites.mappers.OverpassResponseToCityMapper;
import com.placy.placycore.overpasscore.data.OverpassResponseData;
import com.placy.placycore.overpasscore.query.SimpleOverpassQuery;
import com.placy.placycore.overpasscore.services.OverpassQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class OverpassCitiesService {
    private final static String QUERY_CITIES_IN_AREA = "[out:json];\n"
        + "area[name=\"#{country}\"]->.outerArea;\n"
        + "area[name=\"#{city}\"]->.innerArea;\n"
        + "(\n"
        + "  node[\"place\"=\"city\"](area.innerArea)(area.outerArea);\n"
        + ");\n"
        + "out body;\n"
        + ">;\n"
        + "out skel qt;";

    @Autowired
    private OverpassQueryService overpassQueryService;

    public List<OverpassCityResponseData> getOverpassCities(String country, String city) {

        SimpleOverpassQuery simpleOverpassQuery = new SimpleOverpassQuery(QUERY_CITIES_IN_AREA);
        simpleOverpassQuery.putParameter("country", country);
        simpleOverpassQuery.putParameter("city", city);

        OverpassResponseData features = overpassQueryService.queryOverpassSync(simpleOverpassQuery);

        return overpassQueryService.mapElements(features, new OverpassResponseToCityMapper());
    }

    public OverpassQueryService getOverpassQueryService() {
        return overpassQueryService;
    }

    public void setOverpassQueryService(OverpassQueryService overpassQueryService) {
        this.overpassQueryService = overpassQueryService;
    }
}
