package com.placy.placycore.reviewslearning.executables;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.core.processes.exceptions.WrongExecutableParamException;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.processes.facades.ProcessesFacade;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import com.placy.placycore.reviewslearning.model.LearningProcessStatusEnum;
import com.placy.placycore.reviewslearning.services.LearningProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component(value = "learningTaskExecutable")
public class LearningTaskExecutable implements ExecutableBean {

    private static final String CITY_PK_PARAM_NAME = "cityPk";
    private static final String LEARNING_PROCESS_PK_PARAM_NAME = "learningProcessPk";
    private static final String LEARNING_PROCESS_CODE = "learning-process";

    @Autowired
    private CityService cityService;

    @Autowired
    private LearningProcessesService learningProcessesService;

    @Autowired
    private ProcessesFacade processesFacade;

    @Override
    public Object execute(Map<String, Object> params) {
        CityModel cityModel = getCityModel(params);

        LearningProcessModel savedLearningProcessModel = createLearningProcessModel(cityModel);
        List<ParamValueData> paramValues = constructParamValues(savedLearningProcessModel);

        RunProcessData runProcessData = new RunProcessData();

        runProcessData.setProcessCode(LEARNING_PROCESS_CODE);
        runProcessData.setParamValues(paramValues);

        savedLearningProcessModel.setStatus(LearningProcessStatusEnum.STARTED);
        learningProcessesService.saveAndFlush(savedLearningProcessModel);

        processesFacade.startProcess(runProcessData);

        return null;
    }

    private LearningProcessModel createLearningProcessModel(CityModel cityModel) {
        LearningProcessModel learningProcessModel = initLearningProcessModel(cityModel);
        return learningProcessesService.saveAndFlush(learningProcessModel);
    }

    private List<ParamValueData> constructParamValues(LearningProcessModel savedLearningProcessModel) {
        Integer processModelPk = savedLearningProcessModel.getPk();
        Map<String, String> learningProcessParams = constructParamsMap(processModelPk);

        return learningProcessParams.entrySet()
                .stream()
                .map(entry -> new ParamValueData(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Map<String, String> constructParamsMap(Integer processModelPk) {
        Map<String, String> result = new HashMap<>();

        result.put(LEARNING_PROCESS_PK_PARAM_NAME, String.valueOf(processModelPk));

        return result;
    }

    private LearningProcessModel initLearningProcessModel(CityModel cityModel) {
        LearningProcessModel learningProcessModel = new LearningProcessModel();

        learningProcessModel.setStatus(LearningProcessStatusEnum.NOT_STARTED);
        learningProcessModel.setStartDate(new Date());
        learningProcessModel.setCityModel(cityModel);

        return learningProcessModel;
    }

    public CityModel getCityModel(Map<String, Object> params) {
        int cityPk = extractParam(params);

        Optional<CityModel> cityModelOptional = cityService.getByPkOptional(cityPk);

        if(!cityModelOptional.isPresent()) {
            throw new WrongExecutableParamException(
                    String.format("There is no learning process model with specified pk '%s'", cityPk)
            );
        } else {
            return cityModelOptional.get();
        }
    }

    private int extractParam(Map<String, Object> params) {
        if(params.containsKey(CITY_PK_PARAM_NAME)) {
            try {
                String processPkString = (String) params.get(CITY_PK_PARAM_NAME);

                return Integer.parseInt(processPkString);
            } catch (RuntimeException ex) {
                throw new WrongExecutableParamException(String.format("Exception occurred during parsing the " +
                        "param '%s'", ex));
            }
        }

        throw new WrongExecutableParamException(
                String.format("No param '%s' is specified", CITY_PK_PARAM_NAME)
        );
    }
}
