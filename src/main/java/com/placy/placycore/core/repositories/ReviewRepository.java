package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, String> {
    Optional<ReviewModel> findFirstByOriginAndOriginCode(OriginModel originModel, String originCode);
}
