package com.placy.placycore.overpasscore.data;

import java.util.Objects;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ElementData {
    private String type;
    private String id;
    private double latitude;
    private double longitude;
    private TagsData tags;

    public ElementData() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TagsData getTags() {
        return tags;
    }

    public void setTags(TagsData tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ElementData that = (ElementData) o;
        return Double.compare(that.latitude, latitude) == 0 &&
            Double.compare(that.longitude, longitude) == 0 &&
            Objects.equals(type, that.type) &&
            Objects.equals(id, that.id) &&
            Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, latitude, longitude, tags);
    }

    @Override
    public String toString() {
        return "ElementData{" +
            "type='" + type + '\'' +
            ", id='" + id + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", tags=" + tags +
            '}';
    }
}
