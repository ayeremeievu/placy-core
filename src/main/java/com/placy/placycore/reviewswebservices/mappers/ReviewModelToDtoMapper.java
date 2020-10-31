package com.placy.placycore.reviewswebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.corewebservices.mappers.PlaceModelToDtoMapper;
import com.placy.placycore.corewebservices.mappers.UserModelToDtoMapper;
import com.placy.placycore.reviewscore.model.ReviewModel;
import com.placy.placycore.reviewswebservices.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewModelToDtoMapper extends AbstractSimpleMapper<ReviewModel, ReviewDto> {

    @Autowired
    private PlaceModelToDtoMapper placeModelToDtoMapper;

    @Autowired
    private UserModelToDtoMapper userModelToDtoMapper;

    @Override
    public ReviewDto map(ReviewModel reviewModel) {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setUserDto(userModelToDtoMapper.map(reviewModel.getUser()));
        reviewDto.setPlaceDto(placeModelToDtoMapper.map(reviewModel.getPlace()));

        reviewDto.setRate(reviewDto.getRate());
        reviewDto.setSummary(reviewDto.getSummary());

        return reviewDto;
    }
}
