package com.placy.placycore.osmcollector.services;

import com.placy.placycore.osmcollector.data.CollectorCityData;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface OsmCitiesService {

    public List<CollectorCityData> getCities(String country, String state);
}
