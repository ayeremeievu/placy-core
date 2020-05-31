package com.placy.placycore.corewebservices.processes.dto;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.data.ProcessStepInstanceData;
import com.placy.placycore.core.processes.model.ProcessInstanceStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ProcessInstanceDto {
    private String code;
    private Date startDate;
    private Date finishDate;
    private ProcessInstanceStatusEnum status;
    private String processCode;

    private List<ProcessStepInstanceDto> processStepsInstances;
    private List<ParamValueDto> paramValues;

    public ProcessInstanceDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public ProcessInstanceStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProcessInstanceStatusEnum status) {
        this.status = status;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public List<ProcessStepInstanceDto> getProcessStepsInstances() {
        return processStepsInstances;
    }

    public void setProcessStepsInstances(List<ProcessStepInstanceDto> processStepsInstances) {
        this.processStepsInstances = processStepsInstances;
    }

    public List<ParamValueDto> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueDto> paramValues) {
        this.paramValues = paramValues;
    }
}
