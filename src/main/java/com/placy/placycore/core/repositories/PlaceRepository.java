package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceModel, String> {
    Optional<PlaceModel> findFirstByOriginAndOriginCode(OriginModel originModel, String originCode);
}
