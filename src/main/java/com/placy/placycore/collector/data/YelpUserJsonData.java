package com.placy.placycore.collector.data;

import java.util.Objects;

/**
 * @author ayeremeiev@netconomy.net
 */
public class YelpUserJsonData {
    private String user_id;
    private String name;
    private int review_count;
    private String yelping_since;

    public YelpUserJsonData() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public String getYelping_since() {
        return yelping_since;
    }

    public void setYelping_since(String yelping_since) {
        this.yelping_since = yelping_since;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YelpUserJsonData that = (YelpUserJsonData) o;
        return review_count == that.review_count &&
            Objects.equals(user_id, that.user_id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(yelping_since, that.yelping_since);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, name, review_count, yelping_since);
    }

    @Override
    public String toString() {
        return "YelpUserJsonData{" +
            "user_id='" + user_id + '\'' +
            ", name='" + name + '\'' +
            ", review_count=" + review_count +
            ", yelping_since='" + yelping_since + '\'' +
            '}';
    }
}
