package com.placy.placycore.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "city")
public class CityModel {
    @Id
    @Column(name = "c_id", nullable = false)
    private int id;

    @Column(name = "c_city_name", nullable = false)
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "c_division_id", nullable = false)
    private DivisionModel division;

    public CityModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public DivisionModel getDivision() {
        return division;
    }

    public void setDivision(DivisionModel division) {
        this.division = division;
    }
}
