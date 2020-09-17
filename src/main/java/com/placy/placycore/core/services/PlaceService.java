package com.placy.placycore.core.services;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.PlaceRepository;
import com.placy.placycore.reviewscore.model.PlaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlaceService extends AbstractModelService<PlaceModel, String>  {
    @Autowired
    private PlaceRepository placeRepository;

    public boolean existsPlaceByOrigin(OriginModel originModel, String originCode) {
        return getPlaceByOrigin(originModel, originCode).isPresent();
    }

    public Optional<PlaceModel> getPlaceByOrigin(OriginModel originModel, String originCode) {
        return placeRepository.findFirstByOriginAndOriginCode(originModel, originCode);
    }

    @Override
    public JpaRepository<PlaceModel, String> getRepository() {
        return placeRepository;
    }
}
