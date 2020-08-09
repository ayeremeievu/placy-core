package com.placy.placycore.collector.repository.yelp;

import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface YelpUserRawRepository extends JpaRepository<YelpUserRawModel, String> {

}
