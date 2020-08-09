package com.placy.placycore.collector.repository.yelp;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpImportStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Repository
public interface YelpImportRepository extends JpaRepository<YelpImportModel, Integer> {
    List<YelpImportModel> findAllByStatus(YelpImportStatusEnum statusEnum);
}
