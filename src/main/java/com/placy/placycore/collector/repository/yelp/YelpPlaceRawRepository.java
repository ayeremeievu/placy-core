package com.placy.placycore.collector.repository.yelp;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface YelpPlaceRawRepository extends JpaRepository<YelpPlaceRawModel, String> {

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<YelpPlaceRawModel> findAllByIdYelpImportAndIdIdGreaterThanOrderByIdId(
            YelpImportModel yelpImportModel, String id, Pageable pageable);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<YelpPlaceRawModel> findAllByIdYelpImportOrderByIdId(
            YelpImportModel yelpImportModel, Pageable pageable);
}
