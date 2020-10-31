package com.placy.placycore.reviewslearning.strategies.impl;

import com.placy.placycore.core.data.AverageRatedPlace;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.PlaceService;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.mapper.PlaceAvgRatedToPredictionDataMapper;
import com.placy.placycore.reviewslearning.strategies.RecommendationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StaticAverageReviewsRecommendationsStrategy implements RecommendationStrategy {
    private static final int PREDICTIONS_SIZE = 10;
    private static final int MIN_REVIEWS_COUNT = 60;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceAvgRatedToPredictionDataMapper placeAvgRatedToPredictionDataMapper;

    @Override
    public List<PredictionData> getPredictionsForUser(UserModel userModel) {
        CityModel city = userModel.getCity();

        List<AverageRatedPlace> topRatedPlaces = placeService.getTopXReviewsByCityWithHighestRates(
                PREDICTIONS_SIZE, MIN_REVIEWS_COUNT, city);

        return placeAvgRatedToPredictionDataMapper.mapAll(userModel, topRatedPlaces);
    }

    @Override
    public boolean canPredict(UserModel userModel) {
        return userModel.getCity() != null;
    }

    @Override
    public int getCoeffiecient() {
        return 1;
    }
}
