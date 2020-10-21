package com.placy.placycore.reviewscore.model;

import com.placy.placycore.core.model.AddressModel;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.UuidDomainModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryModel extends UuidDomainModel {
    @Column(name = "c_code", nullable = false)
    private String code;

    @Column(name = "c_name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "categoryToPlacesRel",
            joinColumns = @JoinColumn(name = "ctp_place_pk", columnDefinition = "int4"),
            inverseJoinColumns = @JoinColumn(name = "ctp_category_pk", columnDefinition = "varchar")
    )
    private List<PlaceModel> places;

    public CategoryModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaceModel> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceModel> places) {
        this.places = places;
    }
}
