package com.placy.placycore.reviewslearning.executables;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.reviewslearning.executables.data.CityReviewsLearningParams;
import org.apache.mahout.cf.taste.impl.model.jdbc.GenericJDBCDataModel;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component(value = "cityReviewsLearningExecutable")
public class CityReviewsLearningExecutable implements ExecutableBean {
    private static final ObjectMapper mapper;

    @Autowired
    private CityService cityService;

    static {
        mapper = new ObjectMapper();
    }

    @Override
    public Object execute(Map<String, Object> params) {
        CityReviewsLearningParams cityReviewsLearningParams = extractParams(params);

        List<CityModel> citiesToProcess = cityService.getAllCitiesByNameDivisionCountry(
                cityReviewsLearningParams.getCityName(),
                cityReviewsLearningParams.getDivisionCode(),
                cityReviewsLearningParams.getCountryIso()
        );

        return null;
    }

    private CityReviewsLearningParams extractParams(Map<String, Object> params) {
        return mapper.convertValue(params, CityReviewsLearningParams.class);
    }
}
