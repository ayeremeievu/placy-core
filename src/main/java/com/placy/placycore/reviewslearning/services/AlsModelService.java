package com.placy.placycore.reviewslearning.services;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.reviewslearning.exceptions.LearningProcessException;
import com.placy.placycore.sparklearner.data.PredictionData;
import com.placy.placycore.sparklearner.data.PredictionDataList;
import com.placy.placycore.sparklearner.mapper.RowToPredictionDataMapper;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlsModelService {

    private static final int COUNTER_STOP_LOGGING_SIZE = 5000;

    private static final Logger LOG = LoggerFactory.getLogger(AlsModelService.class);

    @Autowired
    private TrainedRecommendationsInMemoryStorageService recommendationsStorageService;

    @Autowired
    private RowToPredictionDataMapper rowToPredictionDataMapper;

    @Autowired
    private UserService userService;

    public void processAlsModel(CityModel cityModel, ALSModel alsModel) {
        Dataset<Row> usersRecommendations = alsModel.recommendForAllUsers(10);

        List<Row> userRecommendations = usersRecommendations.collectAsList();

        int totalSize = userRecommendations.size();

        LOG.info("Processing '{}' users for the city with pk '{}'", totalSize, cityModel.getId());

        int count = 0;

        for (Row recommendationRow : userRecommendations) {
            processRecommendationRow(recommendationRow);

            count++;

            if(count % COUNTER_STOP_LOGGING_SIZE == 0) {
                LOG.info("Already processed '{}' for the city with pk '{}'. In total {}",
                        count, cityModel.getId(), totalSize
                );
            }
        }
    }

    private void processRecommendationRow(Row recommendationRow) {
        UserModel userModel = getUserModel(recommendationRow);

        PredictionDataList predictionDataList = rowToPredictionDataMapper.map(userModel, recommendationRow);

        recommendationsStorageService.putPredictions(userModel, predictionDataList);
    }

    public UserModel getUserModel(Row row) {
        int userPk = row.getInt(0);
        Optional<UserModel> userOptional = userService.getByPkOptional(userPk);
        if(!userOptional.isPresent()) {
            throw new LearningProcessException(
                    String.format("No user taken from spark row with pk '%s' found", userPk)
            );
        }
        return userOptional.get();
    }
}
