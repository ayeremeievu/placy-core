package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.collector.model.yelp.YelpImportModel;
import com.placy.placycore.collector.model.yelp.YelpImportStatusEnum;
import com.placy.placycore.collector.services.yelp.YelpImportService;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class SaveYelpDataExecutable implements ExecutableBean {

    @Autowired
    private YelpImportService yelpImportService;

    @Override
    public Object execute(Map<String, Object> params) {
        List<YelpImportModel> yelpImportsToSave = yelpImportService
                .getAllYelpImportsWithStatus(YelpImportStatusEnum.FINISHED_IMPORTING);

        yelpImportsToSave.forEach(this::saveYelpImport);


        return null;
    }

    private void saveYelpImport(YelpImportModel yelpImportModel) {

    }
}
