package com.placy.placycore.reviewswebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.corewebservices.mappers.PlaceModelToDtoMapper;
import com.placy.placycore.corewebservices.mappers.UserModelToDtoMapper;
import com.placy.placycore.reviewswebservices.dto.RecommendationDto;
import com.placy.placycore.reviewslearning.data.PredictionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PredictionDataToRecommendationDtoMapper extends AbstractSimpleMapper<PredictionData, RecommendationDto> {

    @Autowired
    private UserModelToDtoMapper userModelToDtoMapper;

    @Autowired
    private PlaceModelToDtoMapper placeModelToDtoMapper;

    @Override
    public RecommendationDto map(PredictionData predictionData) {
        RecommendationDto recommendationDto = new RecommendationDto();

        recommendationDto.setPlaceDto(placeModelToDtoMapper.map(predictionData.getPlaceModel()));
        recommendationDto.setUserDto(userModelToDtoMapper.map(predictionData.getUserModel()));

        recommendationDto.setScore(predictionData.getEvaluatedRate());

        return recommendationDto;
    }
}
