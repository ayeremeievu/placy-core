package com.placy.placycore.collector.model.yelp;

import com.placy.placycore.core.model.AbstractDomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "yelpReviewsRaw")
public class YelpReviewRawModel extends AbstractDomainModel<String> {
    @Id
    @Column(name = "yrr_id")
    private String id;

    @Column(name = "yrr_userId")
    private String userId;

    @Column(name = "yrr_businessId")
    private String businessId;

    @Column(name = "yrr_stars")
    private double stars;

    @ManyToOne
    @JoinColumn(name = "yrr_yelp_import_pk", nullable = false)
    private YelpImportModel yelpImport;

    public YelpReviewRawModel() {
    }

    @Override
    public String getPk() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public YelpImportModel getYelpImport() {
        return yelpImport;
    }

    public void setYelpImport(YelpImportModel yelpImport) {
        this.yelpImport = yelpImport;
    }
}
