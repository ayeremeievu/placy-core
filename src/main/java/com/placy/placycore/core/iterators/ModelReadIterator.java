package com.placy.placycore.core.iterators;

import java.util.List;
import java.util.function.Consumer;

public interface ModelReadIterator<MODEL, PK> {

    MODEL next();

    boolean hasNext();

    void forEachRemaining(Consumer<MODEL> consumer);
}
