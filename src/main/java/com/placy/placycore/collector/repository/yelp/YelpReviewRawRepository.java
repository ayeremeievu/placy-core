package com.placy.placycore.collector.repository.yelp;

import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface YelpReviewRawRepository extends JpaRepository<YelpReviewRawModel, String> {

}
