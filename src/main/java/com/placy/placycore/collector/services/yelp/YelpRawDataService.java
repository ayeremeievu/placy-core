package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.collector.repository.yelp.YelpPlaceRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpReviewRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpUserRawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class YelpRawDataService {
    private static final int DEFAULT_PAGE_SIZE = 100;

    @Autowired
    private YelpPlaceRawRepository yelpPlaceRawRepository;

    @Autowired
    private YelpReviewRawRepository yelpReviewRawRepository;

    @Autowired
    private YelpUserRawRepository yelpUserRawRepository;

    public void saveUsers(YelpImportModel yelpImportModel) {

    }

    public void savePlaces(YelpImportModel yelpImportModel) {

    }

    public void saveReviews(YelpImportModel yelpImportModel) {

    }

    public List<YelpPlaceRawModel> getYelpPlacesPage(
            YelpImportModel yelpImportModel, Date startingDate) {
        return getYelpPlacesPage(yelpImportModel, startingDate, DEFAULT_PAGE_SIZE);
    }

    public List<YelpPlaceRawModel> getYelpPlacesPage(
            YelpImportModel yelpImportModel, Date startingDate, int pageSize) {
        return yelpPlaceRawRepository.findAllByIdYelpImportOrderByCreatedAtDescGreaterThanEqualCreatedAt(
                yelpImportModel, startingDate, PageRequest.of(0, pageSize)
        );
    }

    public List<YelpUserRawModel> getYelpUsersPage(
            YelpImportModel yelpImportModel, Date startingDate) {
        return getYelpUsersPage(yelpImportModel, startingDate, DEFAULT_PAGE_SIZE);
    }

    public List<YelpUserRawModel> getYelpUsersPage(YelpImportModel yelpImportModel, Date startingDate, int pageSize) {
        return yelpUserRawRepository.findAllByIdYelpImportOrderByCreatedAtDescGreaterThanEqualCreatedAt(
                yelpImportModel, startingDate, PageRequest.of(0, pageSize)
        );
    }

    public List<YelpReviewRawModel> getYelpReviewsPage(YelpImportModel yelpImportModel, Date startingDate) {
        return getYelpReviewsPage(yelpImportModel, startingDate, DEFAULT_PAGE_SIZE);
    }

    public List<YelpReviewRawModel> getYelpReviewsPage(
            YelpImportModel yelpImportModel, Date startingDate, int pageSize) {
        return yelpReviewRawRepository.findAllByIdYelpImportOrderByCreatedAtDescGreaterThanEqualCreatedAt(
                yelpImportModel, startingDate, PageRequest.of(0, pageSize)
        );
    }
}
