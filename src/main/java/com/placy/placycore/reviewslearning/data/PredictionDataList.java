package com.placy.placycore.reviewslearning.data;

import java.util.ArrayList;
import java.util.List;

public class PredictionDataList {
    private final List<PredictionData> predictionDataList;

    public PredictionDataList(List<PredictionData> predictionDataList) {
        this.predictionDataList = predictionDataList;
    }

    public boolean isEmpty() {
        return predictionDataList == null || predictionDataList.isEmpty();
    }

    public List<PredictionData> getPredictionDataList() {
        if(predictionDataList == null) {
            return new ArrayList<>();
        }

        return predictionDataList;
    }

    public static PredictionDataList of(List<PredictionData> predictionData) {
        return new PredictionDataList(predictionData);
    }

    public static PredictionDataList empty() {
        return new PredictionDataList(new ArrayList<>());
    }
}
