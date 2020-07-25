package com.placy.placycore.osmcollector.data;

/**
 * @author ayeremeiev@netconomy.net
 */
public class CollectorCityData {

    private String name;
    private String division;

    public CollectorCityData(String name, String division) {
        this.name = name;
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }
}
