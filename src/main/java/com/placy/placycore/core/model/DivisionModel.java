package com.placy.placycore.core.model;

import java.util.Objects;

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
@Table(name = "division",
       indexes = {
           @Index(columnList = "d_name", name = "d_name_idx"),
       }
)
public class DivisionModel {
    @Id
    @Column(name = "d_id", nullable = false)
    private String id;

    @Column(name = "d_name", nullable = false)
    private String name;

    @Column(name = "d_code", nullable = false)
    private String d_code;

    @ManyToOne
    @JoinColumn(name = "d_country_id", nullable = false)
    private CountryModel countryModel;

    public DivisionModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public CountryModel getCountryModel() {
        return countryModel;
    }

    public void setCountryModel(CountryModel countryModel) {
        this.countryModel = countryModel;
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
            Objects.equals(d_code, that.d_code) &&
            Objects.equals(countryModel, that.countryModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, d_code, countryModel);
    }

    @Override
    public String toString() {
        return "DivisionModel{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", d_code='" + d_code + '\'' +
            ", countryModel=" + countryModel +
            '}';
    }
}
