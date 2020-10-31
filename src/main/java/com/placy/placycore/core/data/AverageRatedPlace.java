package com.placy.placycore.core.data;

import com.placy.placycore.reviewscore.model.PlaceModel;

public class AverageRatedPlace {

    private PlaceModel placeModel;

    private float avgRate;

    public AverageRatedPlace(PlaceModel placeModel, float avgRate) {
        this.placeModel = placeModel;
        this.avgRate = avgRate;
    }

    public PlaceModel getPlaceModel() {
        return placeModel;
    }

    public float getAvgRate() {
        return avgRate;
    }
}
