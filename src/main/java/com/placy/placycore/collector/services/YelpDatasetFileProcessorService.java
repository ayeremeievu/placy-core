package com.placy.placycore.collector.services;

import com.placy.placycore.collector.data.YelpPlaceJsonData;
import com.placy.placycore.collector.data.YelpReviewJsonData;
import com.placy.placycore.collector.data.YelpUserJsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpDatasetFileProcessorService {
    private final static Logger LOG = LoggerFactory.getLogger(YelpDatasetFileProcessorService.class);

    private final static String YELP_PLACES_FILENAME = "places.json";
    private final static String YELP_REVIEWS_FILENAME = "reviews.json";
    private final static String YELP_USERS_FILENAME = "users.json";

    @Autowired
    private YelpDatasetFileParserService yelpDatasetFileParserService;

    public void processFile() {
        processPlacesFiles(YELP_PLACES_FILENAME);
        processReviewsFiles(YELP_REVIEWS_FILENAME);
        processUsersFiles(YELP_USERS_FILENAME);
    }

    public void processPlacesFiles(String filename) {
        yelpDatasetFileParserService.parse(
            filename, YelpPlaceJsonData.class, this::processPlaceFileEntry
        );
    }

    public void processReviewsFiles(String filename) {
        yelpDatasetFileParserService.parse(
            filename, YelpReviewJsonData.class, this::processReviewFileEntry
        );
    }

    public void processUsersFiles(String filename) {
        yelpDatasetFileParserService.parse(
            filename, YelpUserJsonData.class, this::processUserFileEntry
        );
    }

    public void processPlaceFileEntry(YelpPlaceJsonData yelpPlaceJsonData) {
        LOG.info("Place : " + yelpPlaceJsonData.toString());
    }

    public void processReviewFileEntry(YelpReviewJsonData yelpReviewJsonData) {
        LOG.info("Review : " + yelpReviewJsonData.toString());
    }

    public void processUserFileEntry(YelpUserJsonData yelpUserJsonData) {
        LOG.info("User : " + yelpUserJsonData.toString());
    }

    public YelpDatasetFileParserService getYelpDatasetFileParserService() {
        return yelpDatasetFileParserService;
    }

    public void setYelpDatasetFileParserService(YelpDatasetFileParserService yelpDatasetFileParserService) {
        this.yelpDatasetFileParserService = yelpDatasetFileParserService;
    }
}
