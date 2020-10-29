package com.placy.placycore.core.services;

import com.placy.placycore.core.data.ModelPageQueryData;
import com.placy.placycore.core.iterators.DefaultModelReaderIterator;
import com.placy.placycore.core.iterators.EmptyModelReadIterator;
import com.placy.placycore.core.iterators.ModelReadIterator;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.repositories.PlaceRepository;
import com.placy.placycore.core.repositories.ReviewRepository;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Component
public class ReviewService extends AbstractModelService<ReviewModel, Integer>   {
    private static final int PAGE_SIZE = 1000;

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

    public ModelReadIterator<ReviewModel, Integer> getReviewsByCity(CityModel cityModel) {
        ModelReadIterator<ReviewModel, Integer> result = null;

        Optional<ReviewModel> firstOrderByPk = reviewRepository.findFirstByOrderByPkAsc();

        Function<ModelPageQueryData<Integer>, List<ReviewModel>> pageSupplier = null;

        if(!firstOrderByPk.isPresent()) {
            result = new EmptyModelReadIterator<>();
        } else {
            ReviewModel firstReview = firstOrderByPk.get();

            ModelPageQueryData<Integer> firstPage = ModelPageQueryData.of(
                    firstReview.getPk(), firstReview.getCreatedAt()
            );

            pageSupplier = (pageData) -> getReviewsPageByCity(cityModel, pageData.getPk(), pageData.getStartingFrom());
            result = DefaultModelReaderIterator.of(pageSupplier, firstPage);
        }

        return result;
    }

    public List<ReviewModel> getReviewsPageByCity(CityModel cityModel, Integer pk, Date startDate) {
        return reviewRepository
                .findAllByPlaceAddressCityAndPkGreaterThanAndCreatedAtGreaterThanOrderByPkAscCreatedAtAsc(
                        cityModel, pk, startDate, PageRequest.of(0, PAGE_SIZE)
                );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<ReviewModel> saveAllTransactional(List<ReviewModel> reviews) {
        return saveAll(reviews);
    }

    @Override
    public JpaRepository<ReviewModel, Integer> getRepository() {
        return reviewRepository;
    }
}
