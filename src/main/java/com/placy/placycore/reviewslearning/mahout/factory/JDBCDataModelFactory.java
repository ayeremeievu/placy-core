package com.placy.placycore.reviewslearning.mahout.factory;

import javax.sql.DataSource;

public class JDBCDataModelFactory {

    public class Builder {
        private DataSource dataSource;
        private String preferenceTable;
    }
}
