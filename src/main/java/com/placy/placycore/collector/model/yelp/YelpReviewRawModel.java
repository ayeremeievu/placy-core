package com.placy.placycore.collector.model.yelp;

import com.placy.placycore.core.model.AbstractDomainModel;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
public class YelpReviewRawModel extends AbstractDomainModel<YelpReviewRawId> {
    @EmbeddedId
    private YelpReviewRawId id;

    @Column(name = "yrr_userId")
    private String userId;

    @Column(name = "yrr_businessId")
    private String businessId;

    @Column(name = "yrr_stars")
    private double stars;

    public YelpReviewRawModel() {
        id = new YelpReviewRawId();
    }

    @Override
    public YelpReviewRawId getPk() {
        return id;
    }

    public String getId() {
        return id.getId();
    }

    public void setId(String id) {
        this.id.setId(id);
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
        return id.getYelpImport();
    }

    public void setYelpImport(YelpImportModel yelpImport) {
        this.id.setYelpImport(yelpImport);
    }
}
