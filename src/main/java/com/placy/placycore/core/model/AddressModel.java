package com.placy.placycore.core.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressModel extends UuidDomainModel {
    @Column(name = "a_addressLine", nullable = false)
    private String addressLine;

    @ManyToOne(optional = false)
    @JoinColumn(name = "a_city_id", nullable = false)
    private CityModel city;

    @Column(name = "a_postalCode", nullable = false)
    private String postalCode;

    public AddressModel() {
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
