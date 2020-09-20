package com.placy.placycore.collector.model.yelp;

import com.placy.placycore.core.model.AbstractDomainModel;
import com.placy.placycore.core.model.Identifiable;

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
@Table(name = "yelpUsersRaw")
public class YelpUserRawModel extends AbstractDomainModel<YelpUserRawId> implements Identifiable<String> {
    @EmbeddedId
    private YelpUserRawId id;

    @Column(name = "yur_name")
    private String name;

    @Column(name = "yur_reviewCount")
    private int reviewCount;

    @Column(name = "yur_yelpingSince")
    private String yelpingSince;

    @Override
    public YelpUserRawId getPk() {
        return id;
    }

    public YelpUserRawModel() {
        this.id = new YelpUserRawId();
    }

    @Override
    public String getId() {
        return this.id.getId();
    }

    public void setId(String id) {
        this.id.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getYelpingSince() {
        return yelpingSince;
    }

    public void setYelpingSince(String yelpingSince) {
        this.yelpingSince = yelpingSince;
    }

    public YelpImportModel getYelpImport() {
        return this.id.getYelpImport();
    }

    public void setYelpImport(YelpImportModel yelpImport) {
        this.id.setYelpImport(yelpImport);
    }
}
