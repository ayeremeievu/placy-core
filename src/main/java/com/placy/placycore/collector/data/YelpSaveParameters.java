package com.placy.placycore.collector.data;

public class YelpSaveParameters {
    private boolean excludeUsers = false;
    private boolean excludePlaces = false;
    private boolean excludeReviews = false;

    public YelpSaveParameters() {
    }

    public boolean isExcludeUsers() {
        return excludeUsers;
    }

    public void setExcludeUsers(boolean excludeUsers) {
        this.excludeUsers = excludeUsers;
    }

    public boolean isExcludePlaces() {
        return excludePlaces;
    }

    public void setExcludePlaces(boolean excludePlaces) {
        this.excludePlaces = excludePlaces;
    }

    public boolean isExcludeReviews() {
        return excludeReviews;
    }

    public void setExcludeReviews(boolean excludeReviews) {
        this.excludeReviews = excludeReviews;
    }
}
