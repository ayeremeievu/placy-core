package com.placy.placycore.reviewslearning.model;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.IncrementalDomainModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "learningProcess")
public class LearningProcessModel extends IncrementalDomainModel {
    @ManyToOne
    @JoinColumn(name = "lp_city_pk")
    private CityModel cityModel;

    @Column(name = "lp_startDate")
    private Date startDate;

    @Column(name = "lp_finishDate")
    private Date finishDate;

    @Column(name = "lp_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LearningProcessStatusEnum status;

    public LearningProcessModel() {
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public LearningProcessStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LearningProcessStatusEnum status) {
        this.status = status;
    }

}
