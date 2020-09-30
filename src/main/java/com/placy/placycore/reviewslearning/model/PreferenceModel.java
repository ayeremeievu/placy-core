package com.placy.placycore.reviewslearning.model;

import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.model.UuidDomainModel;
import com.placy.placycore.reviewscore.model.PlaceModel;

import javax.persistence.*;

@Entity
@Table(name = "preferences")
public class PreferenceModel extends UuidDomainModel {

    @ManyToOne
    @JoinColumn(name = "p_user_pk", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "p_place_pk", nullable = false)
    private PlaceModel place;

    @Column(name = "p_value", nullable = false)
    private long value;

    public PreferenceModel() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public PlaceModel getPlace() {
        return place;
    }

    public void setPlace(PlaceModel place) {
        this.place = place;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
