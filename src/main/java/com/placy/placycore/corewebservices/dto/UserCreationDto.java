package com.placy.placycore.corewebservices.dto;

public class UserCreationDto {
    private String firstName;
    private String lastName;
    private int cityId;

    public UserCreationDto(String firstName, String lastName, int cityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityId = cityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCityId() {
        return cityId;
    }
}
