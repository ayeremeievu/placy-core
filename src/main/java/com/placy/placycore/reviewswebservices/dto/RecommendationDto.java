package com.placy.placycore.reviewswebservices.dto;

import com.placy.placycore.corewebservices.dto.PlaceDto;
import com.placy.placycore.corewebservices.dto.UserDto;

public class RecommendationDto {
    private PlaceDto placeDto;
    private UserDto userDto;
    private float score;

    public RecommendationDto() {
    }

    public PlaceDto getPlaceDto() {
        return placeDto;
    }

    public void setPlaceDto(PlaceDto placeDto) {
        this.placeDto = placeDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
