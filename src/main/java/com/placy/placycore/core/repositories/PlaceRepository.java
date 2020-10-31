package com.placy.placycore.core.repositories;

import com.placy.placycore.core.data.AverageRatedPlace;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewscore.model.PlaceModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceModel, Integer> {
    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    Optional<PlaceModel> findFirstByOriginAndOriginCode(OriginModel originModel, String originCode);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<PlaceModel> findByOriginAndOriginCodeIn(OriginModel originModel, List<String> originCodes);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<PlaceModel> findByOriginOrderByPk(OriginModel originModel, Pageable pageable);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<PlaceModel> findByOriginAndPkGreaterThanOrderByPk(OriginModel originModel, Integer pk, Pageable pageable);


    @Query(
            nativeQuery = true,
            value =
                    "SELECT pl_inner.pk AS pl_inner_pk, p_grouped.pl_r_avg\n" +
                    "FROM core.places AS pl_inner JOIN \n" +
                        "(\n" +
                        "SELECT pl_count.pk AS pl_pk, COUNT(r_count.r_rate) AS pl_r_count, AVG(r_count.r_rate) AS pl_r_avg\n" +
                        "FROM core.reviews AS r_count\n" +
                            "JOIN core.places AS pl_count ON pl_count.pk = r_count.r_place_pk\n" +
                            "JOIN core.addresses AS adr_count ON adr_count.pk = pl_count.p_address_pk\n" +
                        "WHERE adr_count.a_city_id = :cityId\n" +
                        "GROUP BY pl_count.pk\n" +
                        ") AS p_grouped ON p_grouped.pl_pk = pl_inner.pk\n" +
                    "WHERE p_grouped.pl_r_count >= :minReviewsCount\n" +
                    "ORDER BY p_grouped.pl_r_avg DESC\n" +
                    "LIMIT :top"
    )
    List<Object[]> getTopXPlacesByCityWithHighestRates(
            @Param("top") int top, @Param("minReviewsCount") int minReviewsCount, @Param("cityId") int cityId);
}
