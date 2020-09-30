package com.placy.placycore.reviewslearning.executables.data;

public class CityReviewsLearningParams {
    private String cityName;
    private String divisionCode;
    private String countryIso;

    public CityReviewsLearningParams() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }
}
