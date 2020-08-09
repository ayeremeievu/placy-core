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
import com.placy.placycore.collector.repository.yelp.YelpImportRepository;
import com.placy.placycore.collector.repository.yelp.YelpPlaceRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpReviewRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpUserRawRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private YelpPlaceDataToModelSimpleMapper yelpPlaceDataToModelSimpleMapper;

    @Autowired
    private YelpReviewDataToModelSimpleMapper yelpReviewDataToModelSimpleMapper;

    @Autowired
    private YelpUserDataToModelSimpleMapper yelpUserDataToModelSimpleMapper;

    @Autowired
    private YelpPlaceRawRepository yelpPlaceRawRepository;

    @Autowired
    private YelpReviewRawRepository yelpReviewRawRepository;

    @Autowired
    private YelpUserRawRepository yelpUserRawRepository;

    @Autowired
    private YelpImportService yelpImportService;

    public void processFiles() {
        YelpImportModel yelpImportModel = new YelpImportModel();

        yelpImportModel.setStatus(YelpImportStatusEnum.RUNNING);
        yelpImportModel.setStartDate(new Date());
        yelpImportModel = yelpImportService.save(yelpImportModel);

        processPlacesFiles(YELP_PLACES_FILENAME, yelpImportModel);
        processReviewsFiles(YELP_REVIEWS_FILENAME, yelpImportModel);
        processUsersFiles(YELP_USERS_FILENAME, yelpImportModel);

        yelpImportModel.setStatus(YelpImportStatusEnum.FINISHED);
        yelpImportModel.setFinishDate(new Date());
        yelpImportService.save(yelpImportModel);
    }

    public void processPlacesFiles(String filename, YelpImportModel yelpImportModel) {
        yelpDatasetFileParserService.parse(
            filename, YelpPlaceJsonData.class, yelpPlaceJsonData -> {
                YelpPlaceRawModel yelpPlaceRawModel = yelpPlaceDataToModelSimpleMapper.map(yelpPlaceJsonData);
                yelpPlaceRawModel.setYelpImport(yelpImportModel);

                yelpPlaceRawRepository.save(yelpPlaceRawModel);
            }
        );
    }

    public void processReviewsFiles(String filename, YelpImportModel yelpImportModel) {
        yelpDatasetFileParserService.parse(
            filename, YelpReviewJsonData.class, yelpReviewJsonData -> {
                YelpReviewRawModel yelpReviewRawModel = yelpReviewDataToModelSimpleMapper.map(yelpReviewJsonData);
                yelpReviewRawModel.setYelpImport(yelpImportModel);

                yelpReviewRawRepository.save(yelpReviewRawModel);
            }
        );
    }

    public void processUsersFiles(String filename, YelpImportModel yelpImportModel) {
        yelpDatasetFileParserService.parse(
            filename, YelpUserJsonData.class, yelpUserJsonData -> {
                YelpUserRawModel yelpUserRawModel = getYelpUserDataToModelSimpleMapper().map(yelpUserJsonData);
                yelpUserRawModel.setYelpImport(yelpImportModel);

                yelpUserRawRepository.save(yelpUserRawModel);
            }
        );
    }

//    public void processPlaceFileEntry(YelpPlaceJsonData yelpPlaceJsonData) {
//        LOG.info("Place : " + yelpPlaceJsonData.toString());
//    }
//
//    public void processReviewFileEntry(YelpReviewJsonData yelpReviewJsonData) {
//        LOG.info("Review : " + yelpReviewJsonData.toString());
//    }
//
//    public void processUserFileEntry(YelpUserJsonData yelpUserJsonData) {
//        LOG.info("User : " + yelpUserJsonData.toString());
//    }

    public YelpDatasetFileParserService getYelpDatasetFileParserService() {
        return yelpDatasetFileParserService;
    }

    public void setYelpDatasetFileParserService(YelpDatasetFileParserService yelpDatasetFileParserService) {
        this.yelpDatasetFileParserService = yelpDatasetFileParserService;
    }

    public YelpPlaceDataToModelSimpleMapper getYelpPlaceDataToModelSimpleMapper() {
        return yelpPlaceDataToModelSimpleMapper;
    }

    public void setYelpPlaceDataToModelSimpleMapper(YelpPlaceDataToModelSimpleMapper yelpPlaceDataToModelSimpleMapper) {
        this.yelpPlaceDataToModelSimpleMapper = yelpPlaceDataToModelSimpleMapper;
    }

    public YelpReviewDataToModelSimpleMapper getYelpReviewDataToModelSimpleMapper() {
        return yelpReviewDataToModelSimpleMapper;
    }

    public void setYelpReviewDataToModelSimpleMapper(YelpReviewDataToModelSimpleMapper yelpReviewDataToModelSimpleMapper) {
        this.yelpReviewDataToModelSimpleMapper = yelpReviewDataToModelSimpleMapper;
    }

    public YelpUserDataToModelSimpleMapper getYelpUserDataToModelSimpleMapper() {
        return yelpUserDataToModelSimpleMapper;
    }

    public void setYelpUserDataToModelSimpleMapper(YelpUserDataToModelSimpleMapper yelpUserDataToModelSimpleMapper) {
        this.yelpUserDataToModelSimpleMapper = yelpUserDataToModelSimpleMapper;
    }

    public YelpPlaceRawRepository getYelpPlaceRawRepository() {
        return yelpPlaceRawRepository;
    }

    public void setYelpPlaceRawRepository(YelpPlaceRawRepository yelpPlaceRawRepository) {
        this.yelpPlaceRawRepository = yelpPlaceRawRepository;
    }

    public YelpReviewRawRepository getYelpReviewRawRepository() {
        return yelpReviewRawRepository;
    }

    public void setYelpReviewRawRepository(YelpReviewRawRepository yelpReviewRawRepository) {
        this.yelpReviewRawRepository = yelpReviewRawRepository;
    }

    public YelpUserRawRepository getYelpUserRawRepository() {
        return yelpUserRawRepository;
    }

    public void setYelpUserRawRepository(YelpUserRawRepository yelpUserRawRepository) {
        this.yelpUserRawRepository = yelpUserRawRepository;
    }
}
