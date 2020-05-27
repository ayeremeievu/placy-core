package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.model.TaskInstanceModel;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface TaskInstanceModelAware {
    void setTaskInstanceModel(TaskInstanceModel taskInstanceModel);
}
