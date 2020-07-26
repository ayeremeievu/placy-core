package com.placy.placycore.overpasscore.data;

import java.util.List;
import java.util.Objects;

/**
 * @author ayeremeiev@netconomy.net
 */
public class OverpassResponseData {
    private String version;
    private String generator;
    private List<ElementData> elements;

    public OverpassResponseData() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public List<ElementData> getElements() {
        return elements;
    }

    public void setElements(List<ElementData> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OverpassResponseData that = (OverpassResponseData) o;
        return Objects.equals(version, that.version) &&
            Objects.equals(generator, that.generator) &&
            Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, generator, elements);
    }

    @Override
    public String toString() {
        return "OverpassResponseData{" +
            "version='" + version + '\'' +
            ", generator='" + generator + '\'' +
            ", elements=" + elements +
            '}';
    }
}
