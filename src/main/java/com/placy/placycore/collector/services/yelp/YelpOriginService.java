package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.constants.CollectorConstants;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.services.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YelpOriginService {

    @Autowired
    private OriginService originService;

    public OriginModel getYelpOrigin() {
        return originService.getFirstByCodeMandatory(CollectorConstants.Yelp.ORIGIN_CODE);
    }
}
