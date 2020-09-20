package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.data.YelpPlaceJsonData;
import com.placy.placycore.collector.data.YelpReviewJsonData;
import com.placy.placycore.collector.data.YelpUserJsonData;
import com.placy.placycore.collector.mappers.yelp.YelpPlaceDataToModelSimpleMapper;
import com.placy.placycore.collector.mappers.yelp.YelpReviewDataToModelSimpleMapper;
import com.placy.placycore.collector.mappers.yelp.YelpUserDataToModelSimpleMapper;
import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.collector.repository.yelp.YelpPlaceRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpReviewRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpUserRawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class YelpRawDataService {

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processPlaceJsonData(YelpImportModel yelpImportModel, YelpPlaceJsonData yelpPlaceJsonData) {
        YelpPlaceRawModel yelpPlaceRawModel = yelpPlaceDataToModelSimpleMapper.map(yelpPlaceJsonData);
        yelpPlaceRawModel.setYelpImport(yelpImportModel);

        yelpPlaceRawRepository.saveAndFlush(yelpPlaceRawModel);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processReviewJsonData(YelpImportModel yelpImportModel, YelpReviewJsonData yelpReviewJsonData) {
        YelpReviewRawModel yelpReviewRawModel = yelpReviewDataToModelSimpleMapper.map(yelpReviewJsonData);
        yelpReviewRawModel.setYelpImport(yelpImportModel);

        yelpReviewRawRepository.saveAndFlush(yelpReviewRawModel);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processUserJsonData(YelpImportModel yelpImportModel, YelpUserJsonData yelpUserJsonData) {
        YelpUserRawModel yelpUserRawModel = yelpUserDataToModelSimpleMapper.map(yelpUserJsonData);
        yelpUserRawModel.setYelpImport(yelpImportModel);

        yelpUserRawRepository.saveAndFlush(yelpUserRawModel);
    }
}
