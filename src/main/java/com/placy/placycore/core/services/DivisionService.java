package com.placy.placycore.core.services;

import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.repositories.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

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
