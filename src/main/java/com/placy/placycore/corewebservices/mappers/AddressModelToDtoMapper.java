package com.placy.placycore.corewebservices.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.model.AddressModel;
import com.placy.placycore.corewebservices.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressModelToDtoMapper extends AbstractSimpleMapper<AddressModel, AddressDto> {
    @Override
    public AddressDto map(AddressModel addressModel) {
        AddressDto addressDto = new AddressDto();

        addressDto.setAddressLine(addressModel.getAddressLine());
        addressDto.setCityName(addressModel.getCity().getCityName());
        addressDto.setDivisionName(addressModel.getCity().getDivision().getName());
        addressDto.setPostalCode(addressModel.getPostalCode());

        return addressDto;
    }
}
