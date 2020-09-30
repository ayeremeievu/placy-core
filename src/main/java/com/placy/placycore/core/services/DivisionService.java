package com.placy.placycore.core.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class DivisionService {
    private static final int CACHE_MAX_SIZE = 10000;

    @Autowired
    private DivisionRepository divisionRepository;

    private LoadingCache<String, Optional<DivisionModel>> divisionsCache;

    @PostConstruct
    public void init() {
        divisionsCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(getLoader());
    }

    private CacheLoader<String, Optional<DivisionModel>> getLoader() {
        return new CacheLoader<String, Optional<DivisionModel>>() {
            @Override
            public Optional<DivisionModel> load(String code) {
                return doGetDivisionByCode(code);
            }
        };
    }


    public Optional<DivisionModel> getDivisionByCode(String code) {
        return divisionsCache.getUnchecked(code);
    }

    private Optional<DivisionModel> doGetDivisionByCode(String code) {
        return divisionRepository.findFirstByCode(code);
    }

    public DivisionModel save(DivisionModel divisionModel) {
        return divisionRepository.save(divisionModel);
    }

    public List<DivisionModel> saveAll(List<DivisionModel> divisions) {
        return divisionRepository.saveAll(divisions);
    }

    public DivisionRepository getDivisionRepository() {
        return divisionRepository;
    }

    public void setDivisionRepository(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }
}
