package com.placy.placycore.reviewswebservices.controllers;

import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.facades.RecommendationsFacade;
import com.placy.placycore.reviewswebservices.constains.ReviewswebservicesRouteConstants;
import com.placy.placycore.reviewswebservices.dto.RecommendationDto;
import com.placy.placycore.reviewswebservices.mappers.PredictionDataToRecommendationDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ReviewswebservicesRouteConstants.URI_PREFIX)
public class RecommendationsController {

    @Autowired
    private RecommendationsFacade recommendationsFacade;

    @Autowired
    private PredictionDataToRecommendationDtoMapper predictionDataToRecommendationDtoMapper;

    @RequestMapping(path = "/{userId}/places/recommendations")
    public List<RecommendationDto> getRecommendationsForUser(
            @PathVariable("userId") Integer userId
    ) {
        List<PredictionData> predictions = recommendationsFacade.getPredictionsForUser(userId);

        return predictionDataToRecommendationDtoMapper.mapAll(predictions);
    }

    @RequestMapping(path = "/{userId}/places/static-recommendations")
    public List<RecommendationDto> getStaticRecommendationsForUser(
            @PathVariable("userId") Integer userId
    ) {
        List<PredictionData> predictions = recommendationsFacade.getStaticPredictionsBasedOnAvg(userId);

        return predictionDataToRecommendationDtoMapper.mapAll(predictions);
    }
}
