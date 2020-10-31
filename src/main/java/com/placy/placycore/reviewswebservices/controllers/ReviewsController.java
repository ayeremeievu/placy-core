package com.placy.placycore.reviewswebservices.controllers;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.ReviewService;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.dto.UserCreationDto;
import com.placy.placycore.corewebservices.dto.UserDto;
import com.placy.placycore.corewebservices.mappers.UserCreationDtoToUserModelMapper;
import com.placy.placycore.corewebservices.mappers.UserModelToDtoMapper;
import com.placy.placycore.reviewscore.model.ReviewModel;
import com.placy.placycore.reviewswebservices.constains.ReviewswebservicesRouteConstants;
import com.placy.placycore.reviewswebservices.dto.ReviewCreationDto;
import com.placy.placycore.reviewswebservices.dto.ReviewDto;
import com.placy.placycore.reviewswebservices.mappers.ReviewCreationDtoToReviewModelMapper;
import com.placy.placycore.reviewswebservices.mappers.ReviewModelToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a.yeremeiev@netconomy.net
 */
@RestController
@RequestMapping(path = ReviewswebservicesRouteConstants.URI_PREFIX)
public class ReviewsController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewCreationDtoToReviewModelMapper reviewCreationDtoToReviewModelMapper;

    @Autowired
    private ReviewModelToDtoMapper reviewModelToDtoMapper;

    @PostMapping(path = "/reviews")
    public ReviewDto createReview(
            @RequestBody ReviewCreationDto reviewCreationDto
    ) {
        ReviewModel reviewModel = reviewCreationDtoToReviewModelMapper.map(reviewCreationDto);

        ReviewModel savedReview = reviewService.save(reviewModel);

        return reviewModelToDtoMapper.map(savedReview);
    }
}
