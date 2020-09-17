package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.constants.CollectorConstants;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.collector.services.yelp.YelpOriginService;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.PlaceService;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class YelpReviewRawModelToReviewModelMapper extends AbstractSimpleMapper<YelpReviewRawModel, ReviewModel> {
    @Autowired
    private YelpOriginService yelpOriginService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserService userService;

    @Override
    public ReviewModel map(YelpReviewRawModel yelpReviewRawModel) {
        ReviewModel reviewModel = new ReviewModel();

        String businessId = yelpReviewRawModel.getBusinessId();
        String userId = yelpReviewRawModel.getUserId();

        OriginModel yelpOrigin = yelpOriginService.getYelpOrigin();

        Optional<PlaceModel> placeOptional = placeService.getPlaceByOrigin(yelpOrigin, businessId);
        Optional<UserModel> userOptional = userService.getUserByOrigin(yelpOrigin, userId);

        if(placeOptional.isEmpty() || userOptional.isEmpty()) {
            return null;
        }

        PlaceModel placeModel = placeOptional.get();
        UserModel userModel = userOptional.get();

        reviewModel.setOrigin(yelpOrigin);
        reviewModel.setOriginCode(yelpReviewRawModel.getId());

        reviewModel.setPlace(placeModel);
        reviewModel.setUser(userModel);
        reviewModel.setRate(yelpReviewRawModel.getStars());

        return reviewModel;
    }
}
