package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.data.YelpPlaceJsonData;
import com.placy.placycore.collector.model.yelp.YelpPlaceRawModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpPlaceDataToModelSimpleMapper extends AbstractSimpleMapper<YelpPlaceJsonData, YelpPlaceRawModel> {

    @Override
    public YelpPlaceRawModel map(YelpPlaceJsonData yelpPlaceJsonData) {
        YelpPlaceRawModel yelpPlaceRawModel = new YelpPlaceRawModel();

        yelpPlaceRawModel.setId(yelpPlaceJsonData.getBusiness_id());
        yelpPlaceRawModel.setName(yelpPlaceJsonData.getName());
        yelpPlaceRawModel.setAddress(yelpPlaceJsonData.getAddress());
        yelpPlaceRawModel.setCity(yelpPlaceJsonData.getCity());
        yelpPlaceRawModel.setState(yelpPlaceJsonData.getState());
        yelpPlaceRawModel.setPostalCode(yelpPlaceJsonData.getPostal_code());
        yelpPlaceRawModel.setLatitude(yelpPlaceJsonData.getLatitude());
        yelpPlaceRawModel.setLongitude(yelpPlaceJsonData.getLongitude());
        yelpPlaceRawModel.setStars(yelpPlaceJsonData.getStars());
        yelpPlaceRawModel.setReviewCount(yelpPlaceJsonData.getReview_count());

        return yelpPlaceRawModel;
    }
}
