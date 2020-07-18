package com.placy.placycore.collector.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpDatasetFileParserService {
    private final static Logger LOG = LoggerFactory.getLogger(YelpDatasetFileParserService.class);

    @Autowired
    private YelpDatasetFileLoaderService yelpDatasetFileLoaderService;

    public <T> void parse(String filename, Class<T> jsonClass, Consumer<T> processor) {
        ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

        yelpDatasetFileLoaderService.loadFileLineByLine(filename, (line) -> {
            try {
                T object = parseJson(objectMapper, line, jsonClass);

                processor.accept(object);
            } catch (JsonProcessingException ex) {
                throw new IllegalStateException(
                    String.format("Exception occurred during Json processing of file '%s' and line '%s'", filename, line), ex
                );
            }
        });
    }

    private <T> T parseJson(ObjectMapper objectMapper, String line, Class<T> jsonClass) throws JsonProcessingException {
        return objectMapper.readValue(line, jsonClass);
    }

    public YelpDatasetFileLoaderService getYelpDatasetFileLoaderService() {
        return yelpDatasetFileLoaderService;
    }

    public void setYelpDatasetFileLoaderService(YelpDatasetFileLoaderService yelpDatasetFileLoaderService) {
        this.yelpDatasetFileLoaderService = yelpDatasetFileLoaderService;
    }
}
