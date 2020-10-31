package com.placy.placycore.reviewslearning.data;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredictionData that = (PredictionData) o;
        return Float.compare(that.evaluatedRate, evaluatedRate) == 0 &&
                Objects.equals(userModel, that.userModel) &&
                Objects.equals(placeModel, that.placeModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userModel, placeModel, evaluatedRate);
    }
}
