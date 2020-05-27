package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.model.TaskInstanceModel;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface ExecutableBean {
    Object execute(Map<String, Object> params);
}
