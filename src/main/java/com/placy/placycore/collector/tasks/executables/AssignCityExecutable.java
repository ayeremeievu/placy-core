package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.processes.exceptions.WrongExecutableParamException;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.core.services.UserLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component(value = "assignCityExecutable")
public class AssignCityExecutable implements ExecutableBean {

    private static final String CITY_PK_PARAM_NAME = "cityPk";

    @Autowired
    private CityService cityService;

    @Autowired
    private UserLocatorService userLocatorService;

    @Override
    public Object execute(Map<String, Object> params) {
        CityModel cityModel = getCityModel(params);

        userLocatorService.locateUsers(cityModel);

        return null;
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
