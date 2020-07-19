package com.placy.placycore.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
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
    private String id;

    @Column(name = "c_city_name", nullable = false)
    private String cityName;
}
