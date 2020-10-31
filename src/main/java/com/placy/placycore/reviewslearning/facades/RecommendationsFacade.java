package com.placy.placycore.reviewslearning.facades;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.corewebservices.exceptions.IllegalWebArgumentException;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.data.PredictionDataList;
import com.placy.placycore.reviewslearning.strategies.impl.DefaultRecommendationStrategy;
import com.placy.placycore.reviewslearning.strategies.impl.StaticAverageReviewsRecommendationsStrategy;
import com.placy.placycore.reviewswebservices.mappers.PredictionDataToRecommendationDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecommendationsFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultRecommendationStrategy recommendationStrategy;

    @Autowired
    private StaticAverageReviewsRecommendationsStrategy staticAverageReviewsRecommendationsStrategy;

    public List<PredictionData> getPredictionsForUser(int id) {
        UserModel userModel = getUserModel(id);

        boolean canPredict = recommendationStrategy.canPredict(userModel);

        List<PredictionData> result = new ArrayList<>();

        if(canPredict){
            result = recommendationStrategy.getPredictionsForUser(userModel);
        }

        return result;
    }

    private UserModel getUserModel(int id) {
        Optional<UserModel> userOptional = userService.getByPkOptional(id);

        if(!userOptional.isPresent()) {
            throw new IllegalWebArgumentException(
                    String.format("User with id '%s' does not exist", id)
            );
        }

        return userOptional.get();
    }

    public List<PredictionData> getStaticPredictionsBasedOnAvg(Integer userPk) {
        return staticAverageReviewsRecommendationsStrategy.getPredictionsForUser(getUserModel(userPk));
    }
}
