package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.collector.services.YelpPlacesCollectorService;
import com.placy.placycore.core.processes.executable.ExecutableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component(value = "importYelpDataExecutable")
public class ImportYelpDataExecutable implements ExecutableBean {
    @Autowired
    private YelpPlacesCollectorService yelpPlacesCollectorService;

    @Override
    public Object execute(Map<String, Object> params) {
        yelpPlacesCollectorService.collect();

        return null;
    }
}
