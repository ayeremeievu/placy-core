package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.ProcessStepInstanceData;
import com.placy.placycore.core.processes.model.ProcessStepInstanceModel;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ProcessStepInstanceModelToDataMapper extends AbstractSimpleMapper<ProcessStepInstanceModel, ProcessStepInstanceData> {

    @Override
    public ProcessStepInstanceData map(ProcessStepInstanceModel processStepInstanceModel) {
        ProcessStepInstanceData processStepInstanceData = new ProcessStepInstanceData();

        processStepInstanceData.setCode(processStepInstanceModel.getCode());
        processStepInstanceData.setStartDate(processStepInstanceModel.getStartDate());
        processStepInstanceData.setFinishDate(processStepInstanceModel.getFinishDate());
        processStepInstanceData.setTaskInstanceCode(processStepInstanceModel.getTaskInstanceModel().getCode());
        processStepInstanceData.setProcessStepResult(processStepInstanceModel.getProcessStepResult());

        return processStepInstanceData;
    }
}
