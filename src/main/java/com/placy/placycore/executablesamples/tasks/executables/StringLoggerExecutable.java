package com.placy.placycore.executablesamples.tasks.executables;

import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.processes.mappers.params.ParameterExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component("stringLoggerExecutable")
public class StringLoggerExecutable implements ExecutableBean {
    private final Logger LOG = LoggerFactory.getLogger(StringLoggerExecutable.class);

    @Autowired
    private ParameterExtractor parameterExtractor;

    @Override
    public Object execute(Map<String, Object> params) {
        String stringValue = parameterExtractor.extractParameter(params, "stringValue", String.class);

        LOG.info("The log : {}", stringValue);

        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
