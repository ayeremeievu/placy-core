package com.placy.placycore.reviewslearning.services;

import com.placy.placycore.core.processes.exceptions.WrongExecutableParamException;
import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class LearningProcessModelParamService {

    private static final String LEARNING_PROCESS_PK_PARAM_NAME = "learningProcessPk";

    @Autowired
    private LearningProcessesService learningProcessesService;

    public LearningProcessModel getLearningProcessModel(Map<String, Object> params) {
        int learningProcessPk = extractParam(params);

        Optional<LearningProcessModel> lpOptional = learningProcessesService.getByPkOptional(learningProcessPk);

        if(!lpOptional.isPresent()) {
            throw new WrongExecutableParamException(
                    String.format("There is no learning process model with specified pk '%s'", learningProcessPk)
            );
        } else {
            return lpOptional.get();
        }
    }

    private int extractParam(Map<String, Object> params) {
        if(params.containsKey(LEARNING_PROCESS_PK_PARAM_NAME)) {
            try {
                String processPkString = (String) params.get(LEARNING_PROCESS_PK_PARAM_NAME);

                return Integer.parseInt(processPkString);
            } catch (RuntimeException ex) {
                throw new WrongExecutableParamException(String.format("Exception occurred during parsing of the " +
                        "param '%s'", ex));
            }
        }

        throw new WrongExecutableParamException(
                String.format("No param '%s' is specified", LEARNING_PROCESS_PK_PARAM_NAME)
        );
    }
}
