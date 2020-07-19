package com.placy.placycore.collector.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpPlacesCollectorService {
    private final static String YELP_PLACES_FILENAME = "places.json";

    @Autowired
    private YelpDatasetFileProcessorService yelpDatasetFileProcessorService;

    public void collect() {
        yelpDatasetFileProcessorService.processFile();
    }

}
