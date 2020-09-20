package com.placy.placycore.collector.repository.yelp;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface YelpUserRawRepository extends JpaRepository<YelpUserRawModel, String> {

    List<YelpUserRawModel> findAllByIdYelpImportAndIdIdGreaterThanOrderByIdId(
            YelpImportModel yelpImportModel, String id, Pageable pageable);

    List<YelpUserRawModel> findAllByIdYelpImportOrderByIdId(
            YelpImportModel yelpImportModel, Pageable pageable);
}
