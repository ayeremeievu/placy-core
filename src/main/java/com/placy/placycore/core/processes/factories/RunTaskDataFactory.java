package com.placy.placycore.core.processes.factories;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.data.RunTaskData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
public class RunTaskDataFactory {
    public static RunTaskData createRunTaskData(String code, Map<String, String> params) {
        RunTaskData runTaskData = new RunTaskData();

        runTaskData.setCode(code);
        runTaskData.setParamValues(map(params));

        return runTaskData;
    }

    public static RunTaskData createRunTaskData(String code) {
        return createRunTaskData(code, new HashMap<>());
    }

    public static List<ParamValueData> map(Map<String, String> params) {
        return params.entrySet()
              .stream()
              .map(entry -> new ParamValueData(entry.getKey(), entry.getValue()))
              .collect(Collectors.toList());
    }

}
