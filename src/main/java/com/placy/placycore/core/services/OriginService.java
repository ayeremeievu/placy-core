package com.placy.placycore.core.services;

import com.placy.placycore.core.exceptions.ModelByCriteriaNotFoundException;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.repositories.OriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OriginService extends AbstractModelService<OriginModel, String>  {
    @Autowired
    private OriginRepository originRepository;

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
