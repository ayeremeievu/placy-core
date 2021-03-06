package com.placy.placycore.core.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "countries",

       indexes = {
           @Index(columnList = "c_iso", name = "c_iso_idx"),
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "c_iso", name = "c_iso_unq_constraint")
       }
)
public class CountryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id", nullable = false)
    private Integer id;

    @Column(name = "c_iso", nullable = false, columnDefinition = " bpchar")
    private String iso;

    @Column(name = "c_name", nullable = false)
    private String name;

    @Column(name = "c_nicename", nullable = false)
    private String nicename;

    @Column(name = "c_iso3", columnDefinition = " bpchar")
    private String iso3;

    @OneToMany(mappedBy = "country")
    private List<DivisionModel> divisions;

    @Column(name = "c_numcode", columnDefinition = " int2")
    private int numcode;

    @Column(name = "c_phonecode")
    private int phonecode;

    public CountryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public int getNumcode() {
        return numcode;
    }

    public void setNumcode(int numcode) {
        this.numcode = numcode;
    }

    public int getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(int phonecode) {
        this.phonecode = phonecode;
    }

    public List<DivisionModel> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionModel> divisions) {
        this.divisions = divisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryModel that = (CountryModel) o;
        return numcode == that.numcode &&
            phonecode == that.phonecode &&
            Objects.equals(id, that.id) &&
            Objects.equals(iso, that.iso) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nicename, that.nicename) &&
            Objects.equals(iso3, that.iso3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, iso, name, nicename, iso3, numcode, phonecode);
    }

    @Override
    public String toString() {
        return "CountryModel{" +
            "id='" + id + '\'' +
            ", iso='" + iso + '\'' +
            ", name='" + name + '\'' +
            ", nicename='" + nicename + '\'' +
            ", iso3='" + iso3 + '\'' +
            ", numcode=" + numcode +
            ", phonecode=" + phonecode +
            '}';
    }
}
