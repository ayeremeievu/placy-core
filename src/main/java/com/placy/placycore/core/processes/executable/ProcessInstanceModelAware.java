package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.model.ProcessInstanceModel;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface ProcessInstanceModelAware {
    void setProcessInstanceModel(ProcessInstanceModel processInstanceModel);
}
