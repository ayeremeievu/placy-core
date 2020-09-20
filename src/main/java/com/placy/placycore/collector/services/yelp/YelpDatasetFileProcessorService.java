package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.data.YelpPlaceJsonData;
import com.placy.placycore.collector.data.YelpReviewJsonData;
import com.placy.placycore.collector.data.YelpUserJsonData;
import com.placy.placycore.collector.mappers.yelp.YelpPlaceDataToModelSimpleMapper;
import com.placy.placycore.collector.mappers.yelp.YelpReviewDataToModelSimpleMapper;
import com.placy.placycore.collector.mappers.yelp.YelpUserDataToModelSimpleMapper;
import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpImportStatusEnum;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.collector.repository.yelp.YelpPlaceRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpReviewRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpUserRawRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Autowired
    private YelpImportService yelpImportService;

    @Autowired
    private YelpRawDataService yelpRawDataService;

    public void processFiles() {
        YelpImportModel yelpImportModel = new YelpImportModel();

        yelpImportModel.setStatus(YelpImportStatusEnum.IMPORTING);
        yelpImportModel.setStartDate(new Date());
        yelpImportModel = yelpImportService.saveAndFlushTransactional(yelpImportModel);

        processPlacesFiles(YELP_PLACES_FILENAME, yelpImportModel);
        processReviewsFiles(YELP_REVIEWS_FILENAME, yelpImportModel);
        processUsersFiles(YELP_USERS_FILENAME, yelpImportModel);

        yelpImportModel.setStatus(YelpImportStatusEnum.FINISHED_IMPORTING);
        yelpImportModel.setFinishDate(new Date());
        yelpImportService.saveAndFlushTransactional(yelpImportModel);
    }

    public void processPlacesFiles(String filename, YelpImportModel yelpImportModel) {
        yelpDatasetFileParserService.parse(
            filename, YelpPlaceJsonData.class, yelpPlaceJsonData -> {
                yelpRawDataService.processPlaceJsonData(yelpImportModel, yelpPlaceJsonData);
            }
        );
    }

    public void processReviewsFiles(String filename, YelpImportModel yelpImportModel) {
        yelpDatasetFileParserService.parse(
            filename, YelpReviewJsonData.class, yelpReviewJsonData -> {
                yelpRawDataService.processReviewJsonData(yelpImportModel, yelpReviewJsonData);
            }
        );
    }

    public void processUsersFiles(String filename, YelpImportModel yelpImportModel) {
        yelpDatasetFileParserService.parse(
            filename, YelpUserJsonData.class, yelpUserJsonData -> {
                yelpRawDataService.processUserJsonData(yelpImportModel, yelpUserJsonData);
            }
        );
    }

    public YelpDatasetFileParserService getYelpDatasetFileParserService() {
        return yelpDatasetFileParserService;
    }

    public void setYelpDatasetFileParserService(YelpDatasetFileParserService yelpDatasetFileParserService) {
        this.yelpDatasetFileParserService = yelpDatasetFileParserService;
    }

}
