package com.placy.placycore.reviewslearning.strategies.impl;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.strategies.RecommendationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultRecommendationStrategy implements RecommendationStrategy {

    @Autowired
    private MLRecommendationStrategy mlRecommendationStrategy;

    @Autowired
    private StaticAverageReviewsRecommendationsStrategy staticAverageReviewsRecommendationsStrategy;

    @Override
    public List<PredictionData> getPredictionsForUser(UserModel userModel) {
        boolean canPredictMl = mlRecommendationStrategy.canPredict(userModel);

        if(canPredictMl) {
            return mlRecommendationStrategy.getPredictionsForUser(userModel);
        }

        return staticAverageReviewsRecommendationsStrategy.getPredictionsForUser(userModel);
    }

    @Override
    public boolean canPredict(UserModel userModel) {
        boolean mlRecommendationsCanPredict = mlRecommendationStrategy.canPredict(userModel);

        if(mlRecommendationsCanPredict){
            return true;
        } else {
            return staticAverageReviewsRecommendationsStrategy.canPredict(userModel);
        }
    }

    @Override
    public int getCoeffiecient() {
        return 10;
    }
}
