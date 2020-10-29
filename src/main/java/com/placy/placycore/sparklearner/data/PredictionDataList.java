package com.placy.placycore.sparklearner.data;

import java.util.ArrayList;
import java.util.List;

public class PredictionDataList {
    private final List<PredictionData> predictionDataList;

    public PredictionDataList(List<PredictionData> predictionDataList) {
        this.predictionDataList = predictionDataList;
    }

    public List<PredictionData> getPredictionDataList() {
        return predictionDataList;
    }

    public static PredictionDataList of(List<PredictionData> predictionData) {
        return new PredictionDataList(predictionData);
    }

    public static PredictionDataList empty() {
        return new PredictionDataList(new ArrayList<>());
    }
}
