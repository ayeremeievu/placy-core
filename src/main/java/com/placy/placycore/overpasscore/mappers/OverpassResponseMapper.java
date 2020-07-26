package com.placy.placycore.overpasscore.mappers;

import com.placy.placycore.overpasscore.data.ElementData;
import org.geojson.Feature;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface OverpassResponseMapper<T> {
    T map(ElementData elementData);
}
