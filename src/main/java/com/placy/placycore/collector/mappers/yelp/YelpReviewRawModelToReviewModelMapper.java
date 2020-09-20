package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.collector.services.yelp.YelpOriginService;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class YelpReviewRawModelToReviewModelMapper {
    private static final Logger LOG = LoggerFactory.getLogger(YelpReviewRawModelToReviewModelMapper.class);

    @Autowired
    private YelpOriginService yelpOriginService;

    public ReviewModel map(YelpReviewRawModel yelpReviewRawModel, List<UserModel> prefetchedExistingUsers, List<PlaceModel> placeModels) {
        long beforeMapMilis = System.currentTimeMillis();
        ReviewModel reviewModel = new ReviewModel();

        String businessId = yelpReviewRawModel.getBusinessId();
        String userId = yelpReviewRawModel.getUserId();

        OriginModel yelpOrigin = yelpOriginService.getYelpOrigin();

        long beforeUserFinding = System.currentTimeMillis();
        Optional<UserModel> userOptional = findUserByOriginCode(prefetchedExistingUsers, userId);
        long tookToFindUser = System.currentTimeMillis() - beforeUserFinding;

        Optional<PlaceModel> placeModelOptional = findPlaceByOriginCode(placeModels, businessId);

        if(userOptional.isEmpty() || placeModelOptional.isEmpty()) {

            return null;
        }

        UserModel userModel = userOptional.get();
        PlaceModel placeModel = placeModelOptional.get();

        reviewModel.setOrigin(yelpOrigin);
        reviewModel.setOriginCode(yelpReviewRawModel.getId());

        reviewModel.setPlace(placeModel);
        reviewModel.setUser(userModel);
        reviewModel.setRate(yelpReviewRawModel.getStars());

        long tookToMap = System.currentTimeMillis() - beforeMapMilis;

//        if(tookToMap > 2) {
//            LOG.info("Took to map {} milis. Took to find user {} milis.", tookToMap, tookToFindUser);
//        }

        return reviewModel;
    }

    private Optional<PlaceModel> findPlaceByOriginCode(List<PlaceModel> prefetchedExistingPlaces, String originCode) {
        return prefetchedExistingPlaces.stream()
                .filter(prefetchedExistingPlace -> prefetchedExistingPlace.getOrigin().getCode()
                        .equals(yelpOriginService.getYelpOrigin().getCode()))
                .filter(prefetchedExistingPlace -> prefetchedExistingPlace.getOriginCode().equals(originCode))
                .findFirst();
    }

    private Optional<UserModel> findUserByOriginCode(List<UserModel> prefetchedExistingUsers, String originCode) {
        return prefetchedExistingUsers.stream()
                .filter(prefetchedExistingUser -> prefetchedExistingUser.getOrigin().getCode()
                        .equals(yelpOriginService.getYelpOrigin().getCode()))
                .filter(prefetchedExistingUser -> prefetchedExistingUser.getOriginCode().equals(originCode))
                .findFirst();
    }
}
