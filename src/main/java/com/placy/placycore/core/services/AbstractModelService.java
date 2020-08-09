package com.placy.placycore.core.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
public abstract class AbstractModelService<MODEL, PK> {
    public MODEL save(MODEL model) {
        Assert.notNull(model, "Model is null and cannot be saved");

        return getRepository().save(model);
    }

    public Collection<MODEL> saveAll(Collection<MODEL> models) {
        Assert.notNull(models, "Models are null and cannot be saved");

        return getRepository().saveAll(models);
    }

    public MODEL saveAndFlush(MODEL model) {
        return getRepository().saveAndFlush(model);
    }

    public Optional<MODEL> getByPkOptional(PK pk) {
        return getRepository().findById(pk);
    }

    public void delete(MODEL model) {
        getRepository().delete(model);
    }

    public void deleteAll(Collection<MODEL> models) {
        getRepository().deleteAll(models);
    }

    public void deleteAllInTable() {
        getRepository().deleteAll();
    }

    public void deleteByPk(PK pk) {
        getRepository().deleteById(pk);
    }

    public void flush() {
        getRepository().flush();
    }

    public abstract JpaRepository<MODEL, PK> getRepository();
}
