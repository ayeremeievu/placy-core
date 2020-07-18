package com.placy.placycore.collector.services;

import com.placy.placycore.collector.data.YelpPlaceJsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpDatasetPlacesProcessorService {
    private final static Logger LOG = LoggerFactory.getLogger(YelpDatasetPlacesProcessorService.class);

    @Autowired
    private YelpDatasetFileParserService yelpDatasetFileParserService;

    public void process(String filename) {
        yelpDatasetFileParserService.parse(
            filename, YelpPlaceJsonData.class, this::process
        );
    }

    private void process(YelpPlaceJsonData yelpPlaceJsonData) {
        LOG.info("Place : " + yelpPlaceJsonData.toString());
    }

    public YelpDatasetFileParserService getYelpDatasetFileParserService() {
        return yelpDatasetFileParserService;
    }

    public void setYelpDatasetFileParserService(YelpDatasetFileParserService yelpDatasetFileParserService) {
        this.yelpDatasetFileParserService = yelpDatasetFileParserService;
    }
}
