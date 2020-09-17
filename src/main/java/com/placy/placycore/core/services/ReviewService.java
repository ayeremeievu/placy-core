package com.placy.placycore.core.services;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.PlaceRepository;
import com.placy.placycore.core.repositories.ReviewRepository;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewService extends AbstractModelService<ReviewModel, String>   {
    @Autowired
    private ReviewRepository reviewRepository;

    public boolean existsReviewByOrigin(OriginModel originModel, String originCode) {
        return getReviewByOrigin(originModel, originCode).isPresent();
    }

    public Optional<ReviewModel> getReviewByOrigin(OriginModel originModel, String originCode) {
        return reviewRepository.findFirstByOriginAndOriginCode(originModel, originCode);
    }

    @Override
    public JpaRepository<ReviewModel, String> getRepository() {
        return reviewRepository;
    }
}
