package com.placy.placycore.reviewscore.model;

import com.placy.placycore.core.model.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "places")
public class PlaceModel extends IncrementalDomainModel {
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
    @JoinColumn(name = "p_origin_pk")
    private OriginModel origin;

    @Column(name = "p_originCode")
    private String originCode;

    @ManyToMany(mappedBy = "places")
    private List<CategoryModel> categories;

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

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceModel that = (PlaceModel) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(description, that.description) &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(originCode, that.originCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, address, description, origin, originCode);
    }
}
