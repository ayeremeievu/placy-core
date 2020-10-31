package com.placy.placycore.sparklearner.mapper;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.PlaceService;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewslearning.exceptions.LearningProcessException;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.data.PredictionDataList;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RowToPredictionDataMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    public PredictionDataList map(UserModel userModel, Row row) {
        List<PredictionData> predictionsList = convertPredictionsSubListsToPredictionDataList(row, userModel);

        return new PredictionDataList(predictionsList);
    }

    private List<PredictionData> convertPredictionsSubListsToPredictionDataList(Row row, UserModel userModel) {
        List<Object> predictionsSubRowsList = row.getList(1);
        List<PredictionData> predictionsList =
                convertPredictionSubRowsListToPredictionDataList(userModel, predictionsSubRowsList);
        return predictionsList;
    }

    private List<PredictionData> convertPredictionSubRowsListToPredictionDataList(
            UserModel userModel, List<Object> predictionsSubRowsList
    ) {
        return predictionsSubRowsList.stream()
                .map(predictionSparkSubRow -> (GenericRowWithSchema) predictionSparkSubRow)
                .map(predictionSparkSubRow -> convertPredictionSparkSubRowToPredictionList(userModel, predictionSparkSubRow))
                .collect(Collectors.toList());
    }

    private PredictionData convertPredictionSparkSubRowToPredictionList(UserModel userModel, GenericRowWithSchema predictionSparkObject) {
        Object[] predictionSubRow = predictionSparkObject.values();

        PlaceModel placeModel = getPlaceModel(predictionSubRow);
        float predictionValue = getPredictionValue(predictionSubRow);

        return new PredictionData(userModel, placeModel, predictionValue);
    }

    private float getPredictionValue(Object[] predictionSubRow) {
        return (float) predictionSubRow[1];
    }

    private PlaceModel getPlaceModel(Object[] row) {
        int placePk = (int) row[0];
        Optional<PlaceModel> placeOptional = placeService.getByPkOptional(placePk);
        if(!placeOptional.isPresent()) {
            throw new LearningProcessException(
                    String.format("No place taken from spark row with pk '%s' found", placePk)
            );
        }
        return placeOptional.get();
    }
}
