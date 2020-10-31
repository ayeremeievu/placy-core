package com.placy.placycore.core.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.placy.placycore.core.data.AverageRatedPlace;
import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.PlaceRepository;
import com.placy.placycore.reviewscore.model.PlaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class PlaceService extends AbstractModelService<PlaceModel, Integer>  {
    private static final int CACHE_MAX_SIZE = 10000;
    @Autowired
    private PlaceRepository placeRepository;

    private LoadingCache<CachePlaceByOriginKey, Optional<PlaceModel>> placesByOriginCodeCache;

    @PostConstruct
    public void init() {
        placesByOriginCodeCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(getLoader());
    }

    private CacheLoader<CachePlaceByOriginKey, Optional<PlaceModel>> getLoader() {
        return new CacheLoader<CachePlaceByOriginKey, Optional<PlaceModel>>() {
            @Override
            public Optional<PlaceModel> load(CachePlaceByOriginKey originKey) {
                return doGetPlaceByOrigin(originKey.originModel, originKey.originCode);
            }
        };
    }

    public boolean existsPlaceByOrigin(OriginModel originModel, String originCode) {
        return getPlaceByOrigin(originModel, originCode).isPresent();
    }

    public Optional<PlaceModel> getPlaceByOrigin(OriginModel originModel, String originCode) {
        CachePlaceByOriginKey cacheKey = new CachePlaceByOriginKey(originModel, originCode);

        return placesByOriginCodeCache.getUnchecked(cacheKey);
    }

    private Optional<PlaceModel> doGetPlaceByOrigin(OriginModel originModel, String originCode) {
        return placeRepository.findFirstByOriginAndOriginCode(originModel, originCode);
    }

    public List<PlaceModel> getPlacesByOriginCodes(OriginModel originModel, List<String> originCodes) {
        return placeRepository.findByOriginAndOriginCodeIn(originModel, originCodes);
    }

    public List<PlaceModel> getPlacesPage(OriginModel originModel, Integer id, int pageSize) {
        if(id == null) {
            return placeRepository.findByOriginOrderByPk(
                    originModel, PageRequest.of(0, pageSize)
            );
        }

        return placeRepository.findByOriginAndPkGreaterThanOrderByPk(
                originModel, id, PageRequest.of(0, pageSize)
        );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<PlaceModel> saveAllTransactional(List<PlaceModel> places) {
        Collection<PlaceModel> savedPlaces = saveAll(places);

        savedPlaces.forEach(placeModel -> {
            CachePlaceByOriginKey cachePlaceByOriginKey = new CachePlaceByOriginKey(
                    placeModel.getOrigin(), placeModel.getOriginCode()
            );

            placesByOriginCodeCache.put(cachePlaceByOriginKey, Optional.of(placeModel));
        });

        return savedPlaces;
    }

    public List<AverageRatedPlace> getTopXReviewsByCityWithHighestRates(int top, int minReviewsCount, CityModel cityModel) {
        List<Object[]> resultObject = placeRepository.getTopXPlacesByCityWithHighestRates(top, minReviewsCount, cityModel.getId());

        return convertTopRatedResultObject(resultObject);
    }

    private List<AverageRatedPlace> convertTopRatedResultObject(List<Object[]> resultObject) {
        return resultObject.stream()
                .map(columns -> new AverageRatedPlace(
                        getPlaceByIdMandatory((int) columns[0]),
                        ((Double) columns[1]).floatValue()
                )).collect(Collectors.toList());
    }

    public Optional<PlaceModel> getPlaceById(int id) {
        return placeRepository.findById(id);
    }

    public PlaceModel getPlaceByIdMandatory(int id) {
        Optional<PlaceModel> placeById = getPlaceById(id);

        if(!placeById.isPresent()) {
            throw new NoSuchElementException(
                    String.format("No place with id '%s' is found", id)
            );
        }

        return placeById.get();
    }

    @Override
    public JpaRepository<PlaceModel, Integer> getRepository() {
        return placeRepository;
    }

    private class CachePlaceByOriginKey {
        private OriginModel originModel;
        private String originCode;

        public CachePlaceByOriginKey(OriginModel originModel, String originCode) {
            this.originModel = originModel;
            this.originCode = originCode;
        }

        public OriginModel getOriginModel() {
            return originModel;
        }

        public String getOriginCode() {
            return originCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CachePlaceByOriginKey that = (CachePlaceByOriginKey) o;
            return Objects.equals(originModel, that.originModel) &&
                    Objects.equals(originCode, that.originCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(originModel, originCode);
        }
    }
}
