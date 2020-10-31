package com.placy.placycore.reviewslearning.mapper;

import com.placy.placycore.core.data.AverageRatedPlace;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewslearning.data.PredictionData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaceAvgRatedToPredictionDataMapper {

    public PredictionData map(UserModel userModel, AverageRatedPlace averageRatedPlace) {

        return new PredictionData(userModel, averageRatedPlace.getPlaceModel(), averageRatedPlace.getAvgRate());
    }

    public List<PredictionData> mapAll(UserModel userModel, List<AverageRatedPlace> places) {

        return places.stream()
                .map(place -> map(userModel, place))
                .collect(Collectors.toList());
    }
}
