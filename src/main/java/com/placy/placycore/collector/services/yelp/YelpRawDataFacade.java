package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.mappers.yelp.YelpPlaceRawModelToPlaceModelMapper;
import com.placy.placycore.collector.mappers.yelp.YelpReviewRawModelToReviewModelMapper;
import com.placy.placycore.collector.mappers.yelp.YelpUserRawModelToUserModelMapper;
import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.collector.repository.yelp.YelpPlaceRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpReviewRawRepository;
import com.placy.placycore.collector.repository.yelp.YelpUserRawRepository;
import com.placy.placycore.core.model.AbstractDomainModel;
import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.*;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class YelpRawDataFacade {
    private static final Logger LOG = LoggerFactory.getLogger(YelpRawDataFacade.class);

    private static final int DEFAULT_PAGE_SIZE = 100;

    @Autowired
    private YelpPlaceRawRepository yelpPlaceRawRepository;

    @Autowired
    private YelpReviewRawRepository yelpReviewRawRepository;

    @Autowired
    private YelpUserRawRepository yelpUserRawRepository;

    @Autowired
    private YelpUserRawModelToUserModelMapper yelpUserRawModelToUserModelMapper;

    @Autowired
    private YelpPlaceRawModelToPlaceModelMapper yelpPlaceRawModelToPlaceModelMapper;

    @Autowired
    private YelpReviewRawModelToReviewModelMapper yelpReviewRawModelToReviewModelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private YelpOriginService yelpOriginService;

    public void saveUsers(YelpImportModel yelpImportModel) {
        LOG.info("Starting processing users.");
        Date startingDate = new Date();
        Consumer<List<YelpUserRawModel>> processPageConsumer = this::processUsersPage;
        Function<Date, List<YelpUserRawModel>> getNewPage = (data) -> getYelpUsersPage(yelpImportModel, startingDate);

        processAllPages(startingDate, processPageConsumer, getNewPage);
        LOG.info("Finished processing users.");
    }

    private void processUsersPage(List<YelpUserRawModel> yelpUsersPage) {
        List<UserModel> userPage = new ArrayList<>();

        userPage = yelpUsersPage.stream()
                .map(yelpUserRawModel -> yelpUserRawModelToUserModelMapper.map(yelpUserRawModel))
                .collect(Collectors.toList());

        OriginModel yelpOrigin = yelpOriginService.getYelpOrigin();

        List<UserModel> usersToSave = userPage.stream()
                .filter(userModel -> userService.existsUserByOrigin(yelpOrigin, userModel.getOriginCode()))
                .collect(Collectors.toList());

        userService.saveAll(usersToSave);
        LOG.info("Processed {} new users", usersToSave.size());
    }

    public void savePlaces(YelpImportModel yelpImportModel) {
        LOG.info("Starting processing places.");
        Date startingDate = new Date();
        Consumer<List<YelpPlaceRawModel>> processPageConsumer = this::processPlacesPage;
        Function<Date, List<YelpPlaceRawModel>> getNewPage = (data) -> getYelpPlacesPage(yelpImportModel, startingDate);

        processAllPages(startingDate, processPageConsumer, getNewPage);
        LOG.info("Finished processing places.");
    }

    private void processPlacesPage(List<YelpPlaceRawModel> placesPage) {
        List<PlaceModel> places = new ArrayList<>();

        for (YelpPlaceRawModel yelpPlaceRawModel : placesPage) {
            PlaceModel placeModel = yelpPlaceRawModelToPlaceModelMapper.map(yelpPlaceRawModel);

            if(placeModel != null) {
                OriginModel yelpOrigin = yelpOriginService.getYelpOrigin();

                if(!placeService.existsPlaceByOrigin(yelpOrigin, placeModel.getOriginCode())) {
                    places.add(placeModel);
                }
            }
        }

        placeService.saveAll(places);
        LOG.info("Processed {} new places", places.size());
    }

    public void saveReviews(YelpImportModel yelpImportModel) {
        LOG.info("Starting processing reviews.");
        Date startingDate = new Date();
        Consumer<List<YelpReviewRawModel>> processPageConsumer = this::processReviewsPage;
        Function<Date, List<YelpReviewRawModel>> getNewPage = (data) -> getYelpReviewsPage(yelpImportModel, startingDate);

        processAllPages(startingDate, processPageConsumer, getNewPage);
        LOG.info("Finished processing reviews.");
    }

    private void processReviewsPage(List<YelpReviewRawModel> reviewsPage) {
        List<ReviewModel> reviews = new ArrayList<>();

        for (YelpReviewRawModel yelpReviewRawModel : reviewsPage) {
            ReviewModel reviewModel = yelpReviewRawModelToReviewModelMapper.map(yelpReviewRawModel);

            if(reviewModel != null) {
                OriginModel yelpOrigin = yelpOriginService.getYelpOrigin();

                if(!reviewService.existsReviewByOrigin(yelpOrigin, reviewModel.getOriginCode())) {
                    reviews.add(reviewModel);
                }
            }
        }

        reviewService.saveAll(reviews);
        LOG.info("Processed {} new reviews", reviews.size());
    }

    private <T extends AbstractDomainModel> void processAllPages(
            Date startingDate,
            Consumer<List<T>> processPageConsumer,
            Function<Date, List<T>> getNewPage
    ) {
        List<T> dataPage = getNewPage.apply(startingDate);

        Date lastDate = startingDate;
        while(!CollectionUtils.isEmpty(dataPage)) {

            try {
                processPageConsumer.accept(dataPage);
            } catch (Exception ex) {
                LOG.error("Error happened during page processing. Last date {}.", lastDate, ex);
            }

            T lastElementInPage = dataPage.get(dataPage.size() - 1);
            lastDate = lastElementInPage.getCreatedAt();

            dataPage = getNewPage.apply(lastDate);
        }
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
