package com.placy.placycore.sparklearner.data;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;

public class PredictionData {

    private final UserModel userModel;
    private final PlaceModel placeModel;
    private final float evaluatedRate;

    public PredictionData(UserModel userModel, PlaceModel placeModel, float evaluatedRate) {
        this.userModel = userModel;
        this.placeModel = placeModel;
        this.evaluatedRate = evaluatedRate;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public PlaceModel getPlaceModel() {
        return placeModel;
    }

    public float getEvaluatedRate() {
        return evaluatedRate;
    }
}
