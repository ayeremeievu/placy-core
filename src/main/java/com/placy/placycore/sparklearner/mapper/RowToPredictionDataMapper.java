package com.placy.placycore.sparklearner.mapper;

import com.placy.placycore.core.mappers.AbstractMapper;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.PlaceService;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewslearning.exceptions.LearningProcessException;
import com.placy.placycore.sparklearner.data.PredictionData;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RowToPredictionDataMapper extends AbstractSimpleMapper<Row, PredictionData> {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @Override
    public PredictionData map(Row row) {
        float prediction = row.getFloat(2);

        UserModel userModel = getUserModel(row);
        PlaceModel placeModel = getPlaceModel(row);

        return new PredictionData(userModel, placeModel, prediction);
    }

    private UserModel getUserModel(Row row) {
        int userPk = row.getInt(0);
        Optional<UserModel> userOptional = userService.getByPkOptional(userPk);
        if(!userOptional.isPresent()) {
            throw new LearningProcessException(
                    String.format("No user taken from spark row with pk '%s' found", userPk)
            );
        }
        return userOptional.get();
    }

    private PlaceModel getPlaceModel(Row row) {
        int placePk = row.getInt(1);
        Optional<PlaceModel> placeOptional = placeService.getByPkOptional(placePk);
        if(!placeOptional.isPresent()) {
            throw new LearningProcessException(
                    String.format("No place taken from spark row with pk '%s' found", placePk)
            );
        }
        return placeOptional.get();
    }
}
