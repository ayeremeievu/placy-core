package com.placy.placycore.core.iterators;

import com.placy.placycore.core.data.ModelPageQueryData;
import com.placy.placycore.core.model.AbstractDomainModel;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Takes the supplier which uses then to query new pages. Starts from the specified page. Can be considered
 * as effective iterator over data returned by query using date and id and caching the whole page
 * as soon as requested. When the page finished, requests new.
 *
 * Implementation of supplier lies on the shoulders of client code. Some flexibility remained to
 * make it possible to specify any query with any conditions you need.
 *
 * The possible usage can be defining of the method inside the service which would construct such iterator
 * for custom query and then be used to iterate over all the items which can be returned by the supplier.
 *
 * */
public class DefaultModelReaderIterator<MODEL extends AbstractDomainModel<PK>, PK> implements ModelReadIterator<MODEL, PK> {
    private final Function<ModelPageQueryData<PK>, List<MODEL>> pageSupplier;
    private final ModelPageQueryData<PK> firstPageData;

    private Iterator<MODEL> currentPageIterator;

    private MODEL lastRequestedItem;

    private boolean finished = false;

    /**
     * @param pageSupplier supplier to get pages based on {@link ModelPageQueryData}.
     *                     Must be idempotent for proper usage.
     * @param firstPageData the first pointer where to start with.
     * */
    public DefaultModelReaderIterator(
            Function<ModelPageQueryData<PK>, List<MODEL>> pageSupplier,
            ModelPageQueryData<PK> firstPageData
    ) {
        Assert.notNull(pageSupplier, "Page supplier cannot be null");
        Assert.notNull(firstPageData, "firstPageData cannot be null");

        this.pageSupplier = pageSupplier;
        this.firstPageData= firstPageData;
    }

    @Override
    public MODEL next() {
        MODEL result = null;

        if(finished) {
            return null;
        }

        if(isCurSubIteratorNotFinished()) {
            result = getNextInternal();
        } else {
            result = queryNewPageAndGetItem(result);
        }

        return result;
    }

    private boolean isCurSubIteratorNotFinished() {
        return !isCurSubIteratorFinished();
    }

    private boolean isCurSubIteratorFinished() {
        return currentPageIterator == null || !currentPageIterator.hasNext();
    }
    /**
     * Not idempotent
     * */
    private MODEL queryNewPageAndGetItem(MODEL result) {
        List<MODEL> supplyResult = peekNextPage();

        if(!supplyResult.isEmpty()) {
            // There are still elements returned from supplier
            currentPageIterator = supplyResult.iterator();

            result = getNextInternal();
        } else {
            // There are no elements anymore. Close iterator and return null all next calls
            close();
        }

        return result;
    }

    /**
     * Idempotent
     * */
    private List<MODEL> peekNextPage() {
        ModelPageQueryData<PK> modelPageQueryData = getModelNextPageQueryData();
        return pageSupplier.apply(modelPageQueryData);
    }

    private ModelPageQueryData<PK> getModelNextPageQueryData() {
        ModelPageQueryData<PK> modelPageQueryData;

        if(lastRequestedItem == null) {
            modelPageQueryData = firstPageData;
        } else {
            PK pk = lastRequestedItem.getPk();
            Date createdAt = lastRequestedItem.getCreatedAt();

            modelPageQueryData = ModelPageQueryData.of(pk, createdAt);
        }
        return modelPageQueryData;
    }

    private void close() {
        finished = true;
        currentPageIterator = null;
        lastRequestedItem = null;
    }

    private MODEL getNextInternal() {
        lastRequestedItem = currentPageIterator.next();
        return lastRequestedItem;
    }

    @Override
    public boolean hasNext() {
        if(finished) {
            return false;
        }

        if(isCurSubIteratorNotFinished()) {
            return true;
        } else {
            return !peekNextPage().isEmpty();
        }
    }

    @Override
    public void forEachRemaining(Consumer<MODEL> consumer) {
        while (hasNext()) {
            MODEL curElement = next();

            consumer.accept(curElement);
        }
    }

    public static <MODEL extends AbstractDomainModel<PK>, PK> DefaultModelReaderIterator<MODEL, PK> of(
            Function<ModelPageQueryData<PK>, List<MODEL>> pageSupplier,
            ModelPageQueryData<PK> firstPageData
    ) {
        return new DefaultModelReaderIterator<>(pageSupplier, firstPageData);
    }
}
