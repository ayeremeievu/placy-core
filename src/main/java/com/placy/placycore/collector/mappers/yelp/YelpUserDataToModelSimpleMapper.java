package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.data.YelpUserJsonData;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpUserDataToModelSimpleMapper extends AbstractSimpleMapper<YelpUserJsonData, YelpUserRawModel> {

    @Override
    public YelpUserRawModel map(YelpUserJsonData yelpUserJsonData) {
        YelpUserRawModel yelpUserRawModel = new YelpUserRawModel();

        yelpUserRawModel.setId(yelpUserJsonData.getUser_id());
        yelpUserRawModel.setName(yelpUserJsonData.getName());
        yelpUserRawModel.setReviewCount(yelpUserJsonData.getReview_count());
        yelpUserRawModel.setYelpingSince(yelpUserJsonData.getYelping_since());

        return yelpUserRawModel;
    }
}
