package com.placy.placycore.collector.model.yelp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author ayeremeiev@netconomy.net
 */
@Embeddable
public class YelpPlaceRawId implements Serializable {
    @Column(name = "ypr_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ypr_yelp_import_pk", nullable = false)
    private YelpImportModel yelpImport;

    public YelpPlaceRawId() {
    }

    public YelpPlaceRawId(String id, YelpImportModel yelpImport) {
        this.id = id;
        this.yelpImport = yelpImport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public YelpImportModel getYelpImport() {
        return yelpImport;
    }

    public void setYelpImport(YelpImportModel yelpImport) {
        this.yelpImport = yelpImport;
    }
}
