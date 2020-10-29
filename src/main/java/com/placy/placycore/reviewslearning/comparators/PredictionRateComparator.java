package com.placy.placycore.reviewslearning.comparators;

import com.placy.placycore.sparklearner.data.PredictionData;

import java.util.Comparator;

public class PredictionRateComparator implements Comparator<PredictionData> {
    @Override
    public int compare(PredictionData pred1, PredictionData pred2) {
        float pred1Rate = pred1.getEvaluatedRate();
        float pred2Rate = pred2.getEvaluatedRate();

        return Float.compare(pred1Rate, pred2Rate);
    }
}
