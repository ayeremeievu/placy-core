package com.placy.placycore.reviewscore.model;

import com.placy.placycore.core.model.IncrementalDomainModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.model.UuidDomainModel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "reviews")
public class ReviewModel extends IncrementalDomainModel {
    @ManyToOne
    @JoinColumn(name = "r_user_pk")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "r_origin_pk")
    private OriginModel origin;

    @Column(name = "r_originCode")
    private String originCode;

    @Column(name = "r_summary")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "r_place_pk")
    private PlaceModel place;

    @Column(name = "r_rate")
    @Max(value = 5)
    @Min(value = 0)
    private double rate;

    public ReviewModel() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public PlaceModel getPlace() {
        return place;
    }

    public void setPlace(PlaceModel place) {
        this.place = place;
    }
}
