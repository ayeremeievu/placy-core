package com.placy.placycore.core.model;

import com.placy.placycore.reviewscore.model.ReviewModel;

import javax.persistence.*;
import java.util.List;

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
}
