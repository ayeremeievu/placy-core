package com.placy.placycore.overpasscore.mappers;

import org.geojson.Feature;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface FeatureMapper<T> {
    T map(Feature feature);
}
