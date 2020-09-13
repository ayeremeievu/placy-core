package com.placy.placycore.collector.repository.yelp;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface YelpPlaceRawRepository extends JpaRepository<YelpPlaceRawModel, String> {

    List<YelpPlaceRawModel> findAllByIdYelpImportOrderByCreatedAtDescGreaterThanEqualCreatedAt(
            YelpImportModel yelpImportModel, Date startingFrom, Pageable pageable);
}
