package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.ProcessInstanceData;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ProcessInstanceModelToDataMapper extends AbstractSimpleMapper<ProcessInstanceModel, ProcessInstanceData> {
    @Autowired
    private ProcessParamValuesToDataMapper processParamValuesToDataMapper;

    @Autowired
    private ProcessStepInstanceModelToDataMapper processStepInstanceModelToDataMapper;

    @Override
    public ProcessInstanceData map(ProcessInstanceModel processInstanceModel) {
        ProcessInstanceData processInstanceData = new ProcessInstanceData();

        processInstanceData.setCode(processInstanceModel.getCode());
        processInstanceData.setStartDate(processInstanceModel.getStartDate());
        processInstanceData.setFinishDate(processInstanceModel.getFinishDate());
        processInstanceData.setStatus(processInstanceModel.getStatus());
        processInstanceData.setProcessCode(processInstanceModel.getProcess().getCode());

        processInstanceData.setProcessStepsInstances(processStepInstanceModelToDataMapper.mapAll(
            processInstanceModel.getProcessStepsInstances()
        ));

        processInstanceData.setParamValues(processParamValuesToDataMapper.mapAll(
            processInstanceModel.getParamValues()
        ));

        return processInstanceData;
    }
}
