package com.placy.placycore.collector.model.yelp;

import com.placy.placycore.core.model.IncrementalDomainModel;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author ayeremeiev@netconomy.net
 */
@Entity
@Table(name = "yelpImports")
public class YelpImportModel extends IncrementalDomainModel {

    @Column(name = "yi_startDate", nullable = true)
    private Date startDate;

    @Column(name = "yi_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private YelpImportStatusEnum status;

    @Column(name = "yi_finishDate", nullable = true)
    private Date finishDate;

    @OneToMany(mappedBy = "id.yelpImport", cascade = CascadeType.ALL)
    private List<YelpPlaceRawModel> yelpPlaces;

    @OneToMany(mappedBy = "id.yelpImport", cascade = CascadeType.ALL)
    private List<YelpReviewRawModel> yelpReviews;

    @OneToMany(mappedBy = "id.yelpImport", cascade = CascadeType.ALL)
    private List<YelpUserRawModel> yelpUsers;

    public YelpImportModel() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public YelpImportStatusEnum getStatus() {
        return status;
    }

    public void setStatus(YelpImportStatusEnum status) {
        this.status = status;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<YelpPlaceRawModel> getYelpPlaces() {
        return yelpPlaces;
    }

    public void setYelpPlaces(List<YelpPlaceRawModel> yelpPlaces) {
        this.yelpPlaces = yelpPlaces;
    }

    public List<YelpReviewRawModel> getYelpReviews() {
        return yelpReviews;
    }

    public void setYelpReviews(List<YelpReviewRawModel> yelpReviews) {
        this.yelpReviews = yelpReviews;
    }

    public List<YelpUserRawModel> getYelpUsers() {
        return yelpUsers;
    }

    public void setYelpUsers(List<YelpUserRawModel> yelpUsers) {
        this.yelpUsers = yelpUsers;
    }
}
