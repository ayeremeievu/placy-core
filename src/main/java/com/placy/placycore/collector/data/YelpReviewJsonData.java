package com.placy.placycore.collector.data;

import java.util.Objects;

/**
 * @author ayeremeiev@netconomy.net
 */
public class YelpReviewJsonData {
    private String review_id;
    private String user_id;
    private String business_id;
    private double stars;

    public YelpReviewJsonData() {
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YelpReviewJsonData that = (YelpReviewJsonData) o;
        return Double.compare(that.stars, stars) == 0 &&
            Objects.equals(review_id, that.review_id) &&
            Objects.equals(user_id, that.user_id) &&
            Objects.equals(business_id, that.business_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review_id, user_id, business_id, stars);
    }

    @Override
    public String toString() {
        return "YelpReviewJsonData{" +
            "review_id='" + review_id + '\'' +
            ", user_id='" + user_id + '\'' +
            ", business_id='" + business_id + '\'' +
            ", stars=" + stars +
            '}';
    }
}
