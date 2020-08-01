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
@Table(name = "yelpUsersRaw")
public class YelpUserRawModel extends AbstractDomainModel<String> {
    @Id
    @Column(name = "yur_id")
    private String id;

    @Column(name = "yur_name")
    private String name;

    @Column(name = "yur_reviewCount")
    private int reviewCount;

    @Column(name = "yur_yelpingSince")
    private String yelpingSince;

    @ManyToOne
    @JoinColumn(name = "yur_yelp_import_pk", nullable = false)
    private YelpImportModel yelpImport;

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
        return yelpImport;
    }

    public void setYelpImport(YelpImportModel yelpImport) {
        this.yelpImport = yelpImport;
    }
}
