package com.placy.placycore.core.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "division",
       indexes = {
           @Index(columnList = "d_name", name = "d_name_idx"),
       }
)
public class DivisionModel {
    @Id
    @Column(name = "d_id", nullable = false)
    private int id;

    @Column(name = "d_name", nullable = false)
    private String name;

    @Column(name = "d_code", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "d_country_id", nullable = false)
    private CountryModel country;

    @OneToMany(mappedBy = "division")
    private List<CityModel> citites;

    public DivisionModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String d_code) {
        this.code = d_code;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel countryModel) {
        this.country = countryModel;
    }

    public List<CityModel> getCitites() {
        return citites;
    }

    public void setCitites(List<CityModel> citites) {
        this.citites = citites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DivisionModel that = (DivisionModel) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, country);
    }

    @Override
    public String toString() {
        return "DivisionModel{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", d_code='" + code + '\'' +
            ", countryModel=" + country +
            '}';
    }
}
