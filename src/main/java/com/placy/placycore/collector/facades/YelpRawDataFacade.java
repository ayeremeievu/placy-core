package com.placy.placycore.collector.facades;

import com.placy.placycore.collector.constants.CollectorConstants;
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
import com.placy.placycore.collector.services.yelp.YelpOriginService;
import com.placy.placycore.core.model.Identifiable;
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
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class YelpRawDataFacade {
    private static final Logger LOG = LoggerFactory.getLogger(YelpRawDataFacade.class);

    private static final int DEFAULT_PAGE_SIZE = 1000;

    private static final int DEFAULT_PLACES_SIZE = 100;

    private static final int THRESHOLD_TO_PROCESS_REVIEWS = 1000;

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
        Function<List<YelpUserRawModel>, Integer> processPageConsumer = this::processUsersPage;
        Function<String, List<YelpUserRawModel>> getNewPage = (id) -> getYelpUsersPage(yelpImportModel, id);

        processAllPages(processPageConsumer, getNewPage);
        LOG.info("Finished processing users.");
    }

    private int processUsersPage(List<YelpUserRawModel> yelpUsersPage) {
        List<UserModel> userPage = new ArrayList<>();

        long startingMilis = System.currentTimeMillis();

        long milisBeforeMapping = System.currentTimeMillis();
        userPage = yelpUsersPage.stream()
                .map(yelpUserRawModel -> yelpUserRawModelToUserModelMapper.map(yelpUserRawModel))
                .collect(Collectors.toList());
        long tookToMap = System.currentTimeMillis() - milisBeforeMapping;

        long milisBeforeQueringOrigin = System.currentTimeMillis();
        OriginModel yelpOrigin = yelpOriginService.getYelpOrigin();
        long tookToQueryOrigin = System.currentTimeMillis() - milisBeforeQueringOrigin;

//        List<UserModel> usersToSave = userPage.stream()
//                .filter(userModel -> !userService.existsUserByOrigin(yelpOrigin, userModel.getOriginCode()))
//                .collect(Collectors.toList());

        List<String> originCodes = userPage.stream()
                .map(UserModel::getOriginCode)
                .collect(Collectors.toList());

        long milisBeforeRemovingDublicates = System.currentTimeMillis();
        List<UserModel> existingUsersByCodes = userService.getUserByOriginCodes(yelpOrigin, originCodes);

        List<UserModel> usersToSave = userPage.stream()
                .filter(userFromRawData -> !containsUserWithCode(existingUsersByCodes, userFromRawData))
                .collect(Collectors.toList());
        long tookToTimeToFilterDublicates = System.currentTimeMillis() - milisBeforeRemovingDublicates;

        long milisBeforeSave = System.currentTimeMillis();
        Collection<UserModel> savedUsers = userService.saveAllTransactional(usersToSave);
        long tookToTimeToActualSave = System.currentTimeMillis() - milisBeforeSave;

        long tookTime = System.currentTimeMillis() - startingMilis;

        LOG.info("Saved {} new users. Took {} milis. To Actually save took {} milis. " +
                        "Took to filter dublicates was needed {} milis. " +
                        "Took to query origin {} milis. " +
                        "Took to map {} milis",
                usersToSave.size(), tookTime, tookToTimeToActualSave, tookToTimeToFilterDublicates, tookToQueryOrigin, tookToMap);

        return savedUsers.size();
    }

    private boolean containsUserWithCode(List<UserModel> existingUsersByCodes, UserModel userFromRawData) {
        return existingUsersByCodes.stream()
                .anyMatch(
                        existingUsersByCode -> existingUsersByCode.getOriginCode()
                                .equals(userFromRawData.getOriginCode())
                );
    }

    public void savePlaces(YelpImportModel yelpImportModel) {
        LOG.info("Starting processing places.");
        Function<List<YelpPlaceRawModel>, Integer> processPageConsumer = this::processPlacesPage;
        Function<String, List<YelpPlaceRawModel>> getNewPage = (id) -> getYelpPlacesPage(yelpImportModel, id);

        processAllPages(processPageConsumer, getNewPage);
        LOG.info("Finished processing places.");
    }

    private int processPlacesPage(List<YelpPlaceRawModel> placesPage) {
        List<PlaceModel> places = new ArrayList<>();

        long milisBeforePrefilter = System.currentTimeMillis();
        List<YelpPlaceRawModel> placesToProcess = prefilterAlreadyProcessedPlaces(placesPage);
        long tookToPrefilter = System.currentTimeMillis() - milisBeforePrefilter;

        long milisBeforeMap = System.currentTimeMillis();
        for (YelpPlaceRawModel yelpPlaceRawModel : placesToProcess) {
            PlaceModel placeModel = yelpPlaceRawModelToPlaceModelMapper.map(yelpPlaceRawModel);

            if(placeModel != null) {
                places.add(placeModel);
            }
        }
        long tookTimeToMap = System.currentTimeMillis() - milisBeforeMap;

        Collection<PlaceModel> savedPlaces = placeService.saveAllTransactional(places);
        LOG.info("Saved {} new places. Took time to prefilter {} milis. Took time to map {} milis", savedPlaces, tookToPrefilter, tookTimeToMap);

        return savedPlaces.size();
    }

    private List<YelpPlaceRawModel> prefilterAlreadyProcessedPlaces(List<YelpPlaceRawModel> placesRawPage) {
        if(CollectionUtils.isEmpty(placesRawPage)) {
            return new ArrayList<>();
        }

        List<PlaceModel> prefetchPlaces = prefetchExistingPlaces(placesRawPage);

        return placesRawPage
                .stream()
                .filter(placeModel -> !containsPlace(prefetchPlaces, placeModel.getId()))
                .collect(Collectors.toList());
    }

    private List<PlaceModel> prefetchExistingPlaces(List<YelpPlaceRawModel> placesPage) {
        List<String> placesIds = placesPage.stream()
                .map(YelpPlaceRawModel::getId)
                .collect(Collectors.toList());

        return placeService.getPlacesByOriginCodes(getYelpOrigin(), placesIds);
    }

    public void saveReviews(YelpImportModel yelpImportModel) {
        LOG.info("Starting processing reviews.");
        Function<List<PlaceModel>, Integer> processPageConsumer = this::processReviewsPage;
        Function<String, List<PlaceModel>> getNewPage = (id) -> getPlacesPage(id);

        processAllPages(processPageConsumer, getNewPage);
        LOG.info("Finished processing reviews.");
    }

//    public void saveReviewsOrderedByDate(YelpImportModel yelpImportModel) {
//        LOG.info("Starting processing reviews.");
//        Consumer<List<YelpPlaceRawModel>> processPageConsumer = this::processReviewsPage;
//        Function<String, List<YelpPlaceRawModel>> getNewPage = (id) -> getYelpPlacesPage(yelpImportModel, id);
//
//        processAllPages(processPageConsumer, getNewPage);
//        LOG.info("Finished processing reviews.");
//    }

//    private void processReviewsPage(List<YelpReviewRawModel> reviewsPage) {
//        List<ReviewModel> reviews = new ArrayList<>();
//
//        long beforePrefiltering = System.currentTimeMillis();
//
//        long beforePrefilteringAlreadyProcessed = System.currentTimeMillis();
//        List<YelpReviewRawModel> reviewsToProcess = prefilterAlreadyProcessedReviews(reviewsPage);
//        long tookToPrefilterAlreadyProcessed = System.currentTimeMillis() - beforePrefilteringAlreadyProcessed;
//
//        long beforePrefilteringByUnknownUsers = System.currentTimeMillis();
//        List<UserModel> prefetchedExistingUsers = prefetchExistingUsersByReview(reviewsToProcess);
//        reviewsToProcess = prefilterUnknownUsers(reviewsToProcess, prefetchedExistingUsers);
//        long tookToPrefilterUnknownUsers = System.currentTimeMillis() - beforePrefilteringByUnknownUsers;
//
//        long beforePrefilteringByUnknownPlaces = System.currentTimeMillis();
//        List<PlaceModel> prefetchedExistingPlaces = prefetchExistingPlacesByReview(reviewsToProcess);
//        reviewsToProcess = prefilterUnknownPlaces(reviewsToProcess, prefetchedExistingPlaces);
//        long tookToPrefilterUnknownPlaces = System.currentTimeMillis() - beforePrefilteringByUnknownPlaces;
//
//        long tookToPrefilter = System.currentTimeMillis() - beforePrefiltering;
//
//        long beforeMappingMilis = System.currentTimeMillis();
//        for (YelpReviewRawModel yelpReviewRawModel : reviewsToProcess) {
//            ReviewModel reviewModel = yelpReviewRawModelToReviewModelMapper
//                    .map(yelpReviewRawModel, prefetchedExistingUsers, prefetchedExistingPlaces);
//
//            if(reviewModel != null) {
//                reviews.add(reviewModel);
//            }
//        }
//        long tookToMap = System.currentTimeMillis() - beforeMappingMilis;
//
//        reviewService.saveAllTransactional(reviews);
//        LOG.info("Saved {} new reviews. Took to profilter {} milis. Took to map {} milis. " +
//                        "Took to prefilter already processes {} milis. " +
//                        "Took to prefilter unknown users {} milis. " +
//                        "Took to prefilter unknown places {} milis. ",
//                reviews.size(), tookToPrefilter, tookToMap,
//                tookToPrefilterAlreadyProcessed, tookToPrefilterUnknownUsers, tookToPrefilterUnknownPlaces);
//    }

    private int processReviewsPage(List<PlaceModel> placesPage) {
        int totalProcessed = 0;
//        LOG.info("Processing {} places. ", placesPage.size());

        long beforePlaces = System.currentTimeMillis();
        List<PlaceModel> placesBuffer = new ArrayList<>();
        List<YelpReviewRawModel> reviewsToProcessBuffer = new ArrayList<>();
        for (PlaceModel placeModel : placesPage) {
            placesBuffer.add(placeModel);
            long beforeGettingReviews = System.currentTimeMillis();
            reviewsToProcessBuffer.addAll(yelpReviewRawRepository.findAllByBusinessId(placeModel.getOriginCode()));
            long tookToGetReviews = System.currentTimeMillis() - beforeGettingReviews;

            if(reviewsToProcessBuffer.size() >= THRESHOLD_TO_PROCESS_REVIEWS) {
                totalProcessed += processPlaceReviews(reviewsToProcessBuffer, placesBuffer);
                reviewsToProcessBuffer = new ArrayList<>();
            }
        }

        if(reviewsToProcessBuffer.size() > 0) {
            totalProcessed += processPlaceReviews(reviewsToProcessBuffer, placesBuffer);
            reviewsToProcessBuffer = new ArrayList<>();
            placesBuffer = new ArrayList<>();
        }

        long tookToProcessPlaces = System.currentTimeMillis() - beforePlaces;
//        LOG.info("Processed {} places. Took {} milis. Processed {} reviews.", placesPage.size(), tookToProcessPlaces, totalProcessed);

        return totalProcessed;
    }

    private int processPlaceReviews(List<YelpReviewRawModel> reviewsToProcess, List<PlaceModel> placesBuffer) {
        long beforeProcessPlace = System.currentTimeMillis();

        List<ReviewModel> reviews = new ArrayList<>();

//        LOG.info("Place with origin id {} has {} reviews. ", placeModel.getOriginCode(), reviewsToProcess.size());

        if(CollectionUtils.isEmpty(reviewsToProcess)) {
            return 0;
        }

        int reviewsToProcessCount = reviewsToProcess.size();

        long beforePrefilteringAlreadyProcessed = System.currentTimeMillis();
        reviewsToProcess = prefilterAlreadyProcessedReviews(reviewsToProcess);
        long tookToPrefilterAlreadyProcessed = System.currentTimeMillis() - beforePrefilteringAlreadyProcessed;

        if(reviewsToProcess.size() == 0) {
//            LOG.info("All data is prefiltered. Processed {} reviews. " +
//                            "Took to prefilter already processes {} milis. " +
//                            "Took to get raw reviews {} milis",
//                    reviewsToProcessCount, tookToPrefilterAlreadyProcessed, tookToGetReviews);

            return reviewsToProcessCount;
        }

        long beforePrefilteringByUnknownUsers = System.currentTimeMillis();
        List<UserModel> prefetchedExistingUsers = prefetchExistingUsersByReview(reviewsToProcess);
        long tookToPrefilterUnknownUsers = System.currentTimeMillis() - beforePrefilteringByUnknownUsers;
        reviewsToProcess = prefilterUnknownUsers(reviewsToProcess, prefetchedExistingUsers);

        long beforeMappingMilis = System.currentTimeMillis();
        for (YelpReviewRawModel reviewToProcess : reviewsToProcess) {
            ReviewModel reviewModel = yelpReviewRawModelToReviewModelMapper
                    .map(reviewToProcess, prefetchedExistingUsers, placesBuffer);

            if(reviewModel != null) {
                reviews.add(reviewModel);
            }
        }
        long tookToMap = System.currentTimeMillis() - beforeMappingMilis;

        long tookToProcessPlace = System.currentTimeMillis() - beforeProcessPlace;

        if(tookToProcessPlace > 100) {
//            LOG.info("Processed {} reviews Took to map {} milis. Took to process place {} " +
//                            "Took to prefilter already processes {} milis. " +
//                            "Took to prefilter unknown users {} milis. ",
//                    reviewsToProcessCount, tookToMap, tookToProcessPlace,
//                    tookToPrefilterAlreadyProcessed, tookToPrefilterUnknownUsers);
        }
        reviewService.saveAllTransactional(reviews);

        return reviewsToProcessCount;
    }

    private List<YelpReviewRawModel> prefilterUnknownUsers(List<YelpReviewRawModel> reviewsToProcess, List<UserModel> prefetchedExistingUsers) {
        return reviewsToProcess.stream()
                .filter(reviewRawModel -> containsUser(prefetchedExistingUsers, reviewRawModel.getUserId()))
                .collect(Collectors.toList());
    }

    private boolean containsUser(List<UserModel> users, String userId) {
        return users.stream().anyMatch(user -> user.getOriginCode().equals(userId));
    }

    private List<YelpReviewRawModel> prefilterUnknownPlaces(List<YelpReviewRawModel> reviewsToProcess, List<PlaceModel> prefetchedExistingPlaces) {
        return reviewsToProcess.stream()
                .filter(reviewRawModel -> containsPlace(prefetchedExistingPlaces, reviewRawModel.getBusinessId()))
                .collect(Collectors.toList());
    }

    private boolean containsPlace(List<PlaceModel> places, String placeId) {
        return places.stream().anyMatch(place -> place.getOriginCode().equals(placeId));
    }

    private List<YelpReviewRawModel> prefilterAlreadyProcessedReviews(List<YelpReviewRawModel> reviewsPage) {
        if(CollectionUtils.isEmpty(reviewsPage)) {
            return new ArrayList<>();
        }

        List<ReviewModel> prefetchReviews = prefetchExistingReviews(reviewsPage);

        return reviewsPage
                .stream()
                .filter(reviewRawModel -> !containsReview(prefetchReviews, reviewRawModel))
                .collect(Collectors.toList());
    }

    private boolean containsReview(List<ReviewModel> prefetchReviews, YelpReviewRawModel reviewRawModel) {
        return prefetchReviews.stream()
                .filter(review -> review.getOrigin().getCode().equals(CollectorConstants.Yelp.ORIGIN_CODE))
                .anyMatch(prefetchedPlace -> prefetchedPlace.getOriginCode().equals(reviewRawModel.getId()));
    }

    private List<ReviewModel> prefetchExistingReviews(List<YelpReviewRawModel> reviewsPage) {
        List<String> reviewsIds = reviewsPage.stream()
                .map(reviewRawModel -> reviewRawModel.getId())
                .collect(Collectors.toList());

        return reviewService.getReviewsByOriginCodes(getYelpOrigin(), reviewsIds);
    }

    private List<UserModel> prefetchExistingUsersByReview(List<YelpReviewRawModel> reviewsPage) {
        List<String> reviewsPageUsersIds = reviewsPage.stream()
                .map(YelpReviewRawModel::getUserId)
                .collect(Collectors.toList());

        List<UserModel> prefetchedUsers = userService.getUserByOriginCodes(getYelpOrigin(), reviewsPageUsersIds);
        return prefetchedUsers;
    }

    private List<PlaceModel> prefetchExistingPlacesByReview(List<YelpReviewRawModel> reviewsPage) {
        List<String> reviewsPagePlacesOriginIds = reviewsPage.stream()
                .map(YelpReviewRawModel::getBusinessId)
                .collect(Collectors.toList());

        return placeService.getPlacesByOriginCodes(getYelpOrigin(), reviewsPagePlacesOriginIds);
    }

    private OriginModel getYelpOrigin() {
        return yelpOriginService.getYelpOrigin();
    }

    private <T extends Identifiable<String>> void processAllPages(
            Function<List<T>, Integer> processPageConsumer,
            Function<String, List<T>> getNewPage
    ) {
        int totalProcessedCount = 0;
        List<T> dataPage = getNewPage.apply(null);

        String lastId = null;
        while(!CollectionUtils.isEmpty(dataPage)) {

            Integer processDataCount = 0;
            long beforeProcessing = System.currentTimeMillis();
            try {
                processDataCount = processPageConsumer.apply(dataPage);
            } catch (Exception ex) {
                logError(lastId, ex);
            }
            long tookProcessing = System.currentTimeMillis() - beforeProcessing;

            totalProcessedCount += processDataCount;
            LOG.info("Processed in total {}. Last page took {} milis. Last page size {}",
                    totalProcessedCount, tookProcessing, processDataCount);

            T lastElementInPage = dataPage.get(dataPage.size() - 1);
            lastId = lastElementInPage.getId();

            dataPage = getNewPage.apply(lastId);
        }
    }

    private void logError(String lastId, Exception ex) {
        if(lastId == null) {
            LOG.error("Error happened during first pge processing.");
        } else {
            LOG.error("Error happened during page processing. Last id {}.", lastId, ex);
        }
    }

    public List<YelpPlaceRawModel> getYelpPlacesPage(
            YelpImportModel yelpImportModel, String id) {
        return getYelpPlacesPage(yelpImportModel, id, DEFAULT_PAGE_SIZE);
    }

    public List<PlaceModel> getPlacesPage(String id) {
        return getPlacesPage(id, DEFAULT_PLACES_SIZE);
    }

    private List<PlaceModel> getPlacesPage(String id, int pageSize) {
        return placeService.getPlacesPage(getYelpOrigin(), id, pageSize);
    }

    public List<YelpPlaceRawModel> getYelpPlacesPage(
            YelpImportModel yelpImportModel, String id, int pageSize) {
        if(id == null) {
            return yelpPlaceRawRepository.findAllByIdYelpImportOrderByIdId(
                    yelpImportModel, PageRequest.of(0, pageSize)
            );
        }

        return yelpPlaceRawRepository.findAllByIdYelpImportAndIdIdGreaterThanOrderByIdId(
                yelpImportModel, id, PageRequest.of(0, pageSize)
        );
    }

    public List<YelpUserRawModel> getYelpUsersPage(
            YelpImportModel yelpImportModel, String id) {
        long currentTimeMillis = System.currentTimeMillis();
        List<YelpUserRawModel> yelpUsersPage = getYelpUsersPage(yelpImportModel, id, DEFAULT_PAGE_SIZE);
        long tookTimeToGetRawData = System.currentTimeMillis() - currentTimeMillis;

//        LOG.info("Took time to get user raw data {} milis", tookTimeToGetRawData);

        return yelpUsersPage;
    }

    public List<YelpUserRawModel> getYelpUsersPage(YelpImportModel yelpImportModel, String id, int pageSize) {
        if(id == null) {
            return yelpUserRawRepository.findAllByIdYelpImportOrderByIdId(
                    yelpImportModel, PageRequest.of(0, pageSize)
            );
        }

        return yelpUserRawRepository.findAllByIdYelpImportAndIdIdGreaterThanOrderByIdId(
                yelpImportModel, id, PageRequest.of(0, pageSize)
        );
    }

    public List<YelpReviewRawModel> getYelpReviewsPage(YelpImportModel yelpImportModel, String id) {
        return getYelpReviewsPage(yelpImportModel, id, DEFAULT_PAGE_SIZE);
    }

    public List<YelpReviewRawModel> getYelpReviewsPage(
            YelpImportModel yelpImportModel, String id, int pageSize) {
        if(id == null) {
            return yelpReviewRawRepository.findAllByIdYelpImportOrderByIdId(
                    yelpImportModel, PageRequest.of(0, pageSize)
            );
        }

        return yelpReviewRawRepository.findAllByIdYelpImportAndIdIdGreaterThanOrderByIdId(
                yelpImportModel, id, PageRequest.of(0, pageSize)
        );
    }
}
