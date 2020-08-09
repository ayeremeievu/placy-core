package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.data.YelpReviewJsonData;
import com.placy.placycore.collector.model.yelp.YelpReviewRawModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpReviewDataToModelSimpleMapper extends AbstractSimpleMapper<YelpReviewJsonData, YelpReviewRawModel> {

    @Override
    public YelpReviewRawModel map(YelpReviewJsonData yelpReviewJsonData) {
        YelpReviewRawModel yelpReviewRawModel = new YelpReviewRawModel();

        yelpReviewRawModel.setId(yelpReviewJsonData.getReview_id());
        yelpReviewRawModel.setUserId(yelpReviewJsonData.getUser_id());
        yelpReviewRawModel.setBusinessId(yelpReviewJsonData.getBusiness_id());
        yelpReviewRawModel.setStars(yelpReviewRawModel.getStars());

        return yelpReviewRawModel;
    }
}
