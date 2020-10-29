package com.placy.placycore.reviewslearning.executables;

import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.reviewslearning.exceptions.LearningProcessException;
import com.placy.placycore.reviewslearning.facades.LearningFacade;
import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import com.placy.placycore.reviewslearning.model.LearningProcessStatusEnum;
import com.placy.placycore.reviewslearning.services.LearningProcessModelParamService;
import com.placy.placycore.reviewslearning.services.LearningProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component(value = "processLearningModelExecutable")
public class ProcessLearningModelExecutable implements ExecutableBean {

    @Autowired
    private LearningFacade learningFacade;

    @Autowired
    private LearningProcessModelParamService learningProcessModelParamService;

    @Autowired
    private LearningProcessesService learningProcessesService;

    @Override
    public Object execute(Map<String, Object> params) {
        LearningProcessModel learningProcessModel = learningProcessModelParamService.getLearningProcessModel(params);

        try {
            learningFacade.processModel(learningProcessModel);

            learningProcessModel.setStatus(LearningProcessStatusEnum.FINISHED);
            learningProcessModel.setFinishDate(new Date());

            learningProcessesService.save(learningProcessModel);
        } catch (Exception ex) {
            learningProcessModel.setStatus(LearningProcessStatusEnum.ERROR);
            learningProcessesService.save(learningProcessModel);

            throw new LearningProcessException(ex);
        }

        return null;
    }
}
