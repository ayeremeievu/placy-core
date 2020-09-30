package com.placy.placycore.core.data;

import java.util.Date;

public class ModelPageQueryData<PK> {
    private final PK pk;
    private final Date startingFrom;

    public ModelPageQueryData(PK pk, Date startingFrom) {
        this.pk = pk;
        this.startingFrom = startingFrom;
    }

    public PK getPk() {
        return pk;
    }

    public Date getStartingFrom() {
        return startingFrom;
    }

    public static <PK> ModelPageQueryData<PK> of(PK pk, Date startingFrom) {
        return new ModelPageQueryData<PK>(pk, startingFrom);
    }
}
