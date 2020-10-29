package com.placy.placycore.core.iterators;

import com.placy.placycore.core.model.AbstractDomainModel;

import java.util.function.Consumer;

public class EmptyModelReadIterator<MODEL extends AbstractDomainModel<PK>, PK> implements ModelReadIterator<MODEL, PK> {
    public EmptyModelReadIterator() {
    }

    @Override
    public MODEL next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<MODEL> consumer) {
    }
}
