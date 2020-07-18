package com.placy.placycore.collector.data;

import java.util.Map;
import java.util.Objects;

/**
 * @author ayeremeiev@netconomy.net
 */
public class YelpPlaceJsonData {
    private String business_id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private double latitude;
    private double longitude;
    private double stars;
    private double review_count;
    private int is_open;
    private Map<String, Object> attributes;
    private String categories;
    private Map<String, Object> hours;

    public YelpPlaceJsonData() {
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public double getReview_count() {
        return review_count;
    }

    public void setReview_count(double review_count) {
        this.review_count = review_count;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Map<String, Object> getHours() {
        return hours;
    }

    public void setHours(Map<String, Object> hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YelpPlaceJsonData that = (YelpPlaceJsonData) o;
        return Double.compare(that.latitude, latitude) == 0 &&
            Double.compare(that.longitude, longitude) == 0 &&
            Double.compare(that.stars, stars) == 0 &&
            Double.compare(that.review_count, review_count) == 0 &&
            is_open == that.is_open &&
            Objects.equals(business_id, that.business_id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(city, that.city) &&
            Objects.equals(state, that.state) &&
            Objects.equals(postal_code, that.postal_code) &&
            Objects.equals(attributes, that.attributes) &&
            Objects.equals(categories, that.categories) &&
            Objects.equals(hours, that.hours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(business_id,
                            name,
                            address,
                            city,
                            state,
                            postal_code,
                            latitude,
                            longitude,
                            stars,
                            review_count,
                            is_open,
                            attributes,
                            categories,
                            hours);
    }

    @Override
    public String toString() {
        return "YelpPlaceJsonData{" +
            "business_id='" + business_id + '\'' +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", post_code='" + postal_code + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", stars=" + stars +
            ", review_count=" + review_count +
            ", is_open=" + is_open +
            ", attributes=" + attributes +
            ", categories='" + categories + '\'' +
            ", hours=" + hours +
            '}';
    }
}
