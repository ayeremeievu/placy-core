package com.placy.placycore.core.services;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.repositories.PlaceRepository;
import com.placy.placycore.core.repositories.ReviewRepository;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    public List<ReviewModel> getReviewsByOriginCodes(OriginModel originModel, List<String> originCodes) {
        return reviewRepository.findByOriginAndOriginCodeIn(originModel, originCodes);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<ReviewModel> saveAllTransactional(List<ReviewModel> reviews) {
        return saveAll(reviews);
    }

    @Override
    public JpaRepository<ReviewModel, String> getRepository() {
        return reviewRepository;
    }
}
