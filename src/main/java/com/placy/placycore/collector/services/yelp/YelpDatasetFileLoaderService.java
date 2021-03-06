package com.placy.placycore.collector.services.yelp;

import com.placy.placycore.collector.context.DataLoaderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class YelpDatasetFileLoaderService {
    private final static Logger LOG = LoggerFactory.getLogger(YelpDatasetFileLoaderService.class);

    private static final String YELP_FEED_FOLDER_NAME = "yelp-feed";

    private static final int CHUNK_SIZE = 10000;

    public void loadFileLineByLine(String filename, Consumer<String> processor) {
        String yelpFeedFilePath = getYelpFeedFilePath(filename);
        LOG.info("Parsing : {}", yelpFeedFilePath);

        int i = doLoadFileLineByLine(filename, processor, yelpFeedFilePath);

        LOG.info("File loading finished. {} of objects from {} are processed.", i, filename);
    }

    private int doLoadFileLineByLine(String filename, Consumer<String> processor, String yelpFeedFilePath) {
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(yelpFeedFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                processor.accept(line);

                if(i != 0 && i % CHUNK_SIZE == 0) {
                    LOG.info("{} of objects from {} are processed.", i, filename);
                }

                i++;
            }
        } catch (IOException ex) {
            LOG.error("Error occurred during parsing file {} ", yelpFeedFilePath, ex);
        }
        return i;
    }

    private String getYelpFeedFolderPath() {
        return new StringBuilder(DataLoaderContext.getFeedFolderPath())
            .append(File.separator)
            .append(YELP_FEED_FOLDER_NAME)
            .toString();
    }

    private String getYelpFeedFilePath(String filename) {
        return new StringBuilder(getYelpFeedFolderPath())
            .append(File.separator)
            .append(filename)
            .toString();
    }
}
