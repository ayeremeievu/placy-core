package com.placy.placycore.corewebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.corewebservices.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModelToDtoMapper extends AbstractSimpleMapper<UserModel, UserDto> {
    @Override
    public UserDto map(UserModel userModel) {
        UserDto userDto = new UserDto();

        userDto.setCode(userModel.getId());
        userDto.setName(userModel.getName());
        userDto.setLastName(userModel.getLastName());

        CityModel userCity = userModel.getCity();

        if(userCity != null) {
            userDto.setCityName(userCity.getCityName());
        }

        return userDto;
    }
}
