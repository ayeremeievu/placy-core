package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.core.processes.executable.ExecutableBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component(value = "importPlacyDataExecutable")
public class ImportPlacyDataExecutable implements ExecutableBean {
    @Override
    public Object execute(Map<String, Object> params) {
        return null;
    }
}
