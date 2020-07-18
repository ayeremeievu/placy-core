package com.placy.placycore.collector.context;

import java.io.File;

/**
 * @author ayeremeiev@netconomy.net
 */
public class DataLoaderContext {
    private static final String DATA_FOLDER_NAME = "placy-data";
    private static final String DATA_FEED_FOLDER_NAME = "feed";

    public DataLoaderContext() {
    }

    public static String getCurrentWorkingDirPath() {
        return System.getProperty("user.dir");
    }

    public static String getDataFolderPath() {
        return new StringBuilder()
            .append(getCurrentWorkingDirPath())
            .append(File.separator)
            .append("..")
            .append(File.separator)
            .append(DATA_FOLDER_NAME)
            .toString();
    }

    public static String getFeedFolderPath() {
        return new StringBuilder(getDataFolderPath())
            .append(File.separator)
            .append(DATA_FEED_FOLDER_NAME)
            .toString();
    }
}
