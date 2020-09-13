package com.placy.placycore.reviewscore.model;

import com.placy.placycore.core.model.AddressModel;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UuidDomainModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "places")
public class PlaceModel extends UuidDomainModel {
    @Column(name = "p_name", nullable = false)
    private String name;

    @Column(name = "p_latitude", nullable = false)
    private double latitude;

    @Column(name = "p_longitude", nullable = false)
    private double longitude;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_address_pk", nullable = false)
    private AddressModel address;

    @Column(name = "p_description", nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "p_origin")
    private OriginModel origin;

    @Column(name = "p_originCode")
    private String originCode;

    @ManyToMany(mappedBy = "places")
    private List<CategoryModel> places;

    @OneToMany(mappedBy = "place")
    private List<ReviewModel> reviews;

    public PlaceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OriginModel getOrigin() {
        return origin;
    }

    public void setOrigin(OriginModel origin) {
        this.origin = origin;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public List<CategoryModel> getPlaces() {
        return places;
    }

    public void setPlaces(List<CategoryModel> places) {
        this.places = places;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }
}
