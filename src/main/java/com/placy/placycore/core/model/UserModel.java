package com.placy.placycore.core.model;

import com.placy.placycore.reviewscore.model.ReviewModel;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserModel extends IncrementalDomainModel {

    @Column(name = "u_name")
    private String name;

    @Column(name = "u_lastname")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "u_origin_pk")
    private OriginModel origin;

    @Column(name = "u_originCode")
    private String originCode;

    @OneToMany(mappedBy = "user")
    private List<ReviewModel> reviews;

    @ManyToOne
    @JoinColumn(name = "u_city_pk")
    private CityModel city;

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(name, userModel.name) &&
                Objects.equals(lastName, userModel.lastName) &&
                Objects.equals(origin, userModel.origin) &&
                Objects.equals(originCode, userModel.originCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, origin, originCode);
    }
}
