package com.placy.placycore.core.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.placy.placycore.core.exceptions.ModelByCriteriaNotFoundException;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.OriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class OriginService extends AbstractModelService<OriginModel, String>  {
    private static final int CACHE_MAX_SIZE = 100;

    @Autowired
    private OriginRepository originRepository;

    private LoadingCache<String, OriginModel> originCache;

    @PostConstruct
    public void init() {
        originCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(getLoader());
    }

    private CacheLoader<String, OriginModel> getLoader() {
        return new CacheLoader<>() {
            @Override
            public OriginModel load(String code) {
                return getFirstByCodeMandatory(code);
            }
        };
    }

    public OriginModel geMandatoryByCodeCached(String code) {
        return originCache.getUnchecked(code);
    }

    public Optional<OriginModel> getFirstByCode(String code) {
        return originRepository.findFirstByCode(code);
    }

    public OriginModel getFirstByCodeMandatory(String code) {
        Optional<OriginModel> originByCodeOptional = getFirstByCode(code);

        return originByCodeOptional.orElseThrow(() -> new ModelByCriteriaNotFoundException("code = " + code));
    }

    @Override
    public JpaRepository<OriginModel, String> getRepository() {
        return originRepository;
    }
}
