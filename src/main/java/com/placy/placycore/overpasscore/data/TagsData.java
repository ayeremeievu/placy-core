package com.placy.placycore.overpasscore.data;

import java.util.Objects;

/**
 * @author ayeremeiev@netconomy.net
 */
public class TagsData {
    private String is_in;
    private String name;
    private String place;
    private String population;

    public TagsData() {
    }

    public String getIs_in() {
        return is_in;
    }

    public void setIs_in(String is_in) {
        this.is_in = is_in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TagsData tagsData = (TagsData) o;
        return Objects.equals(is_in, tagsData.is_in) &&
            Objects.equals(name, tagsData.name) &&
            Objects.equals(place, tagsData.place) &&
            Objects.equals(population, tagsData.population);
    }

    @Override
    public int hashCode() {
        return Objects.hash(is_in, name, place, population);
    }

    @Override
    public String toString() {
        return "TagsData{" +
            "is_in='" + is_in + '\'' +
            ", name='" + name + '\'' +
            ", place='" + place + '\'' +
            ", population='" + population + '\'' +
            '}';
    }
}
