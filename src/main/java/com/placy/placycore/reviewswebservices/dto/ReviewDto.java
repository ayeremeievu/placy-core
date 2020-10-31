package com.placy.placycore.reviewswebservices.dto;

import com.placy.placycore.corewebservices.dto.PlaceDto;
import com.placy.placycore.corewebservices.dto.UserDto;

public class ReviewDto {
    private UserDto userDto;

    private PlaceDto placeDto;

    private float rate;

    private String summary;

    public ReviewDto() {
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public PlaceDto getPlaceDto() {
        return placeDto;
    }

    public void setPlaceDto(PlaceDto placeDto) {
        this.placeDto = placeDto;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
