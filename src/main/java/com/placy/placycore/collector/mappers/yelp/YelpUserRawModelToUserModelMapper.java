package com.placy.placycore.collector.mappers.yelp;

import com.placy.placycore.collector.constants.CollectorConstants;
import com.placy.placycore.collector.model.yelp.YelpUserRawModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.OriginService;
import org.springframework.beans.factory.annotation.Autowired;

public class YelpUserRawModelToUserModelMapper extends AbstractSimpleMapper<YelpUserRawModel, UserModel> {
    @Autowired
    private OriginService originService;

    @Override
    public UserModel map(YelpUserRawModel yelpUserRawModel) {
        UserModel userModel = new UserModel();

        populateName(yelpUserRawModel, userModel);

        userModel.setOrigin(originService.getFirstByCodeMandatory(CollectorConstants.Yelp.ORIGIN_CODE));
        userModel.setOriginCode(yelpUserRawModel.getId());

        return null;
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
