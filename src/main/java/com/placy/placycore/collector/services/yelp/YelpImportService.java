package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpImportStatusEnum;
import com.placy.placycore.collector.repository.yelp.YelpImportRepository;
import com.placy.placycore.core.services.AbstractModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpImportService extends AbstractModelService<YelpImportModel, Integer> {

    @Autowired
    private YelpImportRepository yelpImportRepository;

    public List<YelpImportModel> getAllYelpImportsWithStatus(YelpImportStatusEnum yelpImportStatusEnum) {
        return yelpImportRepository.findAllByStatus(yelpImportStatusEnum);
    }

    public void removeAllYelpImportsWithStatus(YelpImportStatusEnum yelpImportStatusEnum) {
        List<YelpImportModel> yelpImports = getAllYelpImportsWithStatus(yelpImportStatusEnum);

        deleteAll(yelpImports);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public YelpImportModel saveAndFlushTransactional(YelpImportModel yelpImportModel) {
        return saveAndFlush(yelpImportModel);
    }

    @Override
    public JpaRepository<YelpImportModel, Integer> getRepository() {
        return yelpImportRepository;
    }

    public YelpImportRepository getYelpImportRepository() {
        return yelpImportRepository;
    }

    public void setYelpImportRepository(YelpImportRepository yelpImportRepository) {
        this.yelpImportRepository = yelpImportRepository;
    }
}
