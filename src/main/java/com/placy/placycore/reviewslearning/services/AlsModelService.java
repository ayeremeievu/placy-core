package com.placy.placycore.reviewslearning.services;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.sparklearner.data.PredictionData;
import com.placy.placycore.sparklearner.data.PredictionDataList;
import com.placy.placycore.sparklearner.services.SparkPredictionsDatasetService;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlsModelService {

    @Autowired
    private TrainedRecommendationsInMemoryStorageService recommendationsStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private SparkPredictionsDatasetService sparkPredictionsDatasetService;

    public void processAlsModel(CityModel cityModel, ALSModel alsModel) {
        Dataset<Row> usersRecommendations = alsModel.recommendForAllUsers(10);

        List<UserModel> users = userService.getUsersByCity(cityModel);

        users.forEach(userModel -> processUser(userModel, usersRecommendations));
    }

    private void processUser(UserModel userModel, Dataset<Row> usersRecommendations) {
        List<PredictionData> predictionData = sparkPredictionsDatasetService
                .selectPredictionsByUserPk(usersRecommendations, userModel);

        recommendationsStorageService.putPredictions(userModel, PredictionDataList.of(predictionData));
    }
}
