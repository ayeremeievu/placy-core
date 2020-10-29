package com.placy.placycore.core.repositories;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;
import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends PageableRepository<ReviewModel, Integer> {
    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    Optional<ReviewModel> findFirstByOriginAndOriginCode(OriginModel originModel, String originCode);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<ReviewModel> findByOriginAndOriginCodeIn(OriginModel originModel, List<String> originCodes);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<ReviewModel> findAllByPlaceAddressCityAndPkGreaterThanAndCreatedAtGreaterThanOrderByPkAscCreatedAtAsc(
            CityModel cityModel, Integer pk, Date createdAt, Pageable pageable);

}
