package com.placy.placycore.reviewswebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.services.PlaceService;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.reviewscore.model.ReviewModel;
import com.placy.placycore.reviewswebservices.dto.ReviewCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewCreationDtoToReviewModelMapper extends AbstractSimpleMapper<ReviewCreationDto, ReviewModel> {
    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @Override
    public ReviewModel map(ReviewCreationDto reviewCreationDto) {
        ReviewModel reviewModel = new ReviewModel();

        reviewModel.setUser(userService.getUserByPkMandatory(reviewCreationDto.getUserId()));
        reviewModel.setSummary(reviewCreationDto.getSummary());
        reviewModel.setRate(reviewCreationDto.getRate());
        reviewModel.setPlace(placeService.getPlaceByIdMandatory(reviewCreationDto.getPlaceId()));

        return reviewModel;
    }
}
