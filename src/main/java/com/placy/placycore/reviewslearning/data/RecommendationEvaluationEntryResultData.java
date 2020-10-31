package com.placy.placycore.reviewslearning.data;

import com.placy.placycore.reviewslearning.strategies.RecommendationStrategy;

public class RecommendationEvaluationEntryResultData {

    private PredictionData predictionData;

    int coefficient;

    private RecommendationStrategy recommendationStrategy;

    public RecommendationEvaluationEntryResultData(PredictionData predictionData, int coefficient, RecommendationStrategy recommendationStrategy) {
        this.predictionData = predictionData;
        this.coefficient = coefficient;
        this.recommendationStrategy = recommendationStrategy;
    }

    public PredictionData getPredictionData() {
        return predictionData;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public RecommendationStrategy getRecommendationStrategy() {
        return recommendationStrategy;
    }
}
