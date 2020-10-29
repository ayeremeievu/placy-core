package com.placy.placycore.core.services;

import com.placy.placycore.core.iterators.ModelReadIterator;
import com.placy.placycore.core.model.AddressModel;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLocatorService {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    public void locateUsers(CityModel cityModel) {
        ModelReadIterator<ReviewModel, Integer> reviewsByCityIterator = reviewService.getReviewsByCity(cityModel);

        while(reviewsByCityIterator.hasNext()) {
            ReviewModel curReview = reviewsByCityIterator.next();

            UserModel user = curReview.getUser();

            CityModel city = getReviewCity(curReview);

            user.setCity(city);
            userService.save(user);
        }
    }

    private CityModel getReviewCity(ReviewModel curReview) {
        PlaceModel place = curReview.getPlace();
        AddressModel address = place.getAddress();
        return address.getCity();
    }
}
