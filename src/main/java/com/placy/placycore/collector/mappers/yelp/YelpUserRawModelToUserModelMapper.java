package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.constants.CollectorConstants;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.collector.services.yelp.YelpOriginService;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.OriginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YelpUserRawModelToUserModelMapper extends AbstractSimpleMapper<YelpUserRawModel, UserModel> {
    private static final Logger LOG = LoggerFactory.getLogger(YelpUserRawModelToUserModelMapper.class);

    @Autowired
    private YelpOriginService yelpOriginService;

    @Override
    public UserModel map(YelpUserRawModel yelpUserRawModel) {
        UserModel userModel = new UserModel();

        populateName(yelpUserRawModel, userModel);

        userModel.setOrigin(yelpOriginService.getYelpOrigin());
        userModel.setOriginCode(yelpUserRawModel.getId());

        return userModel;
    }

    private void populateName(YelpUserRawModel yelpUserRawModel, UserModel userModel) {
        String rawName = yelpUserRawModel.getName();

        if(rawName != null && !rawName.isBlank()) {
            String[] nameParts = rawName.split(" ");

            String name = nameParts[0];
            userModel.setName(name);

            if(nameParts.length == 2) {
                String lastName = nameParts[1];

                userModel.setLastName(lastName);
            }
        }
    }
}
