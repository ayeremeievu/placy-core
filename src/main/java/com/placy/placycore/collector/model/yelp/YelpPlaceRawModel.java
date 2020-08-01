package com.placy.placycore.collector.model.yelp;

import com.placy.placycore.core.model.AbstractDomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "yelpPlacesRaw")
public class YelpPlaceRawModel extends AbstractDomainModel<String> {

    @Id
    @Column(name = "ypr_id")
    private String id;

    @Column(name = "ypr_name")
    private String name;

    @Column(name = "ypr_address")
    private String address;

    @Column(name = "ypr_city")
    private String city;

    @Column(name = "ypr_state")
    private String state;

    @Column(name = "ypr_postalCode")
    private String postalCode;

    @Column(name = "ypr_latitude")
    private double latitude;

    @Column(name = "ypr_longitude")
    private double longitude;

    @Column(name = "ypr_stars")
    private double stars;

    @Column(name = "ypr_reviewCount")
    private double reviewCount;

    @ManyToOne
    @JoinColumn(name = "ypr_yelp_import_pk", nullable = false)
    private YelpImportModel yelpImport;

    @Override
    public String getPk() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String businessId) {
        this.id = businessId;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public double getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(double reviewCount) {
        this.reviewCount = reviewCount;
    }

    public YelpImportModel getYelpImport() {
        return yelpImport;
    }

    public void setYelpImport(YelpImportModel yelpImportModel) {
        this.yelpImport = yelpImportModel;
    }
}
