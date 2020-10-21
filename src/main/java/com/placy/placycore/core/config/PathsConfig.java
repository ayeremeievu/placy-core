package com.placy.placycore.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PathsConfig {

    @Value("${PLACY_DATA}")
    private String placyDataFolderPath;

    @PostConstruct
    public void init() {
        if(placyDataFolderPath == null) {
            throw new IllegalStateException("No PLACY_DATA os environment variable present in the system");
        }
    }

    public String getPlacyDataFolderPath() {
        return placyDataFolderPath;
    }
}
