package com.placy.placycore.reviewslearning.services;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.services.AbstractModelService;
import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import com.placy.placycore.reviewslearning.model.LearningProcessStatusEnum;
import com.placy.placycore.reviewslearning.repositories.LearningProcessesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class LearningProcessesService extends AbstractModelService<LearningProcessModel, Integer> {
    @Autowired
    private LearningProcessesRepository learningProcessesRepository;

    @Override
    public JpaRepository<LearningProcessModel, Integer> getRepository() {
        return learningProcessesRepository;
    }

    public LearningProcessModel createLearningProcess() {
        LearningProcessModel learningProcessModel = new LearningProcessModel();

        learningProcessModel.setStatus(LearningProcessStatusEnum.NOT_STARTED);

        return learningProcessModel;
    }
}
