package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.collector.model.yelp.YelpImportStatusEnum;
import com.placy.placycore.collector.services.yelp.YelpImportService;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class CleanupYelpRawDataExecutable implements ExecutableBean {

    @Autowired
    private YelpImportService yelpImportService;

    @Override
    public Object execute(Map<String, Object> params) {
        yelpImportService.removeAllYelpImportsWithStatus(YelpImportStatusEnum.FINISHED);

        return null;
    }
}
