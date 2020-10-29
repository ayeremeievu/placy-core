package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.collector.data.YelpSaveParameters;
import com.placy.placycore.collector.model.yelp.*;
import com.placy.placycore.collector.services.yelp.YelpImportService;
import com.placy.placycore.collector.facades.YelpRawDataFacade;
import com.placy.placycore.core.processes.exceptions.WrongExecutableParamException;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class SaveYelpDataExecutable implements ExecutableBean {
    private static final Logger LOG = LoggerFactory.getLogger(SaveYelpDataExecutable.class);

    private static final String EXCLUDE_USERS_PARAMETERS = "excludeUsers";
    private static final String EXCLUDE_PLACES_PARAMETERS = "excludePlaces";
    private static final String EXCLUDE_REVIEWS_PARAMETERS = "excludeReviews";

    @Autowired
    private YelpImportService yelpImportService;

    @Autowired
    private YelpRawDataFacade yelpRawDataFacade;

    @Override
    public Object execute(Map<String, Object> params) {
        List<YelpImportModel> yelpImportsToSave = yelpImportService
                .getAllYelpImportsWithStatus(YelpImportStatusEnum.FINISHED_IMPORTING);

        List<YelpImportModel> allYelpImportsWithErrorStatus = yelpImportService
                .getAllYelpImportsWithStatus(YelpImportStatusEnum.ERROR_SAVING);

        List<YelpImportModel> yelpImportsToProcess = Stream
                .concat(yelpImportsToSave.stream(), allYelpImportsWithErrorStatus.stream())
                .collect(Collectors.toList());

        LOG.info("Found {} imports to process. Starting processing", yelpImportsToProcess.size());

        YelpSaveParameters yelpSaveParameters = mapParams(params);

        yelpImportsToProcess.forEach(yelpImportModel -> doSaveYelpImport(yelpSaveParameters, yelpImportModel));

        // TODO add parameters to limit objects and starting date if takes to long time

        LOG.info("Finished processing {} imports", yelpImportsToProcess.size());

        return null;
    }

    private void doSaveYelpImport(YelpSaveParameters yelpSaveParameters, YelpImportModel yelpImportModel) {
        try {
            saveYelpImport(yelpImportModel, yelpSaveParameters);
        } catch (Exception ex) {
            yelpImportModel.setStatus(YelpImportStatusEnum.ERROR_SAVING);
            yelpImportService.saveAndFlushTransactional(yelpImportModel);
        }
    }

    private YelpSaveParameters mapParams(Map<String, Object> params) {
        Object excludeUsersParameter = params.getOrDefault(EXCLUDE_USERS_PARAMETERS, "false");
        validateBooleanParameter(excludeUsersParameter, EXCLUDE_USERS_PARAMETERS);
        boolean excludeUsersParameterBoolean = Boolean.valueOf((String) excludeUsersParameter);

        Object excludePlacesParameter = params.getOrDefault(EXCLUDE_PLACES_PARAMETERS, "false");
        validateBooleanParameter(excludePlacesParameter, EXCLUDE_PLACES_PARAMETERS);
        boolean excludePlacesParameterBoolean = Boolean.valueOf((String) excludePlacesParameter);

        Object excludeReviewsParameter = params.getOrDefault(EXCLUDE_REVIEWS_PARAMETERS, "false");
        validateBooleanParameter(excludeReviewsParameter, EXCLUDE_REVIEWS_PARAMETERS);
        boolean excludeReviewsParameterBoolean = Boolean.valueOf((String) excludeReviewsParameter);

        YelpSaveParameters yelpSaveParameters = new YelpSaveParameters();

        yelpSaveParameters.setExcludeUsers(excludeUsersParameterBoolean);
        yelpSaveParameters.setExcludePlaces(excludePlacesParameterBoolean);
        yelpSaveParameters.setExcludeReviews(excludeReviewsParameterBoolean);

        return yelpSaveParameters;
    }

    private void validateBooleanParameter(Object paramValue, String parameterKey) {
        if(!(paramValue instanceof String)) {
            throw new WrongExecutableParamException(
                    String.format("Param key %s is not a boolean value. The value %s", parameterKey, paramValue));
        }

        String parameterValueString = ((String)paramValue).trim();

        if(!isaBooleanString(parameterValueString)) {
            throw new WrongExecutableParamException(
                    String.format("Param key %s is not a boolean value. Param value %s", parameterKey, parameterValueString));
        }
    }

    private boolean isaBooleanString(String parameterValueString) {
        return parameterValueString.equals("true") || parameterValueString.equals("false");
    }

    private void saveYelpImport(YelpImportModel yelpImportModel, YelpSaveParameters yelpSaveParameters) {
        if(!yelpSaveParameters.isExcludeUsers()) {
            yelpRawDataFacade.saveUsers(yelpImportModel);
        }

        if(!yelpSaveParameters.isExcludePlaces()) {
            yelpRawDataFacade.savePlaces(yelpImportModel);
        }

        if(!yelpSaveParameters.isExcludeReviews()) {
            yelpRawDataFacade.saveReviews(yelpImportModel);
        }
    }
}
