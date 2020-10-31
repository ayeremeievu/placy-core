package com.placy.placycore.corewebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.AddressModel;
import com.placy.placycore.corewebservices.dto.PlaceDto;
import com.placy.placycore.reviewscore.model.PlaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceModelToDtoMapper extends AbstractSimpleMapper<PlaceModel, PlaceDto> {
    @Autowired
    private AddressModelToDtoMapper addressModelToDtoMapper;

    @Override
    public PlaceDto map(PlaceModel placeModel) {
        PlaceDto placeDto = new PlaceDto();

        placeDto.setCode(placeModel.getPk());
        placeDto.setName(placeModel.getName());
        placeDto.setLatitude(placeDto.getLatitude());
        placeDto.setLongitude(placeDto.getLongitude());

        AddressModel address = placeModel.getAddress();

        if(address != null) {
            placeDto.setAddressDto(addressModelToDtoMapper.map(address));
        }

        placeDto.setDescription(placeModel.getDescription());

        return placeDto;
    }
}
