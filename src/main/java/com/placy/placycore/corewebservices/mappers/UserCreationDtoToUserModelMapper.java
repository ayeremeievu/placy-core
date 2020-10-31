package com.placy.placycore.corewebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.corewebservices.dto.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreationDtoToUserModelMapper extends AbstractSimpleMapper<UserCreationDto, UserModel> {

    @Autowired
    private CityService cityService;

    @Override
    public UserModel map(UserCreationDto userCreationDto) {
        UserModel userModel = new UserModel();

        userModel.setName(userCreationDto.getFirstName());
        userModel.setLastName(userCreationDto.getLastName());
        userModel.setCity(cityService.getCityByIdMandatory(userCreationDto.getCityId()));

        return userModel;
    }
}
