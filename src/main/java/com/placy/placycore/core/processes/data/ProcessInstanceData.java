package com.placy.placycore.core.processes.data;

import com.placy.placycore.core.processes.model.ProcessInstanceStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ProcessInstanceData {
    private String code;
    private Date startDate;
    private Date finishDate;
    private ProcessInstanceStatusEnum status;
    private String processCode;

    private List<ProcessStepInstanceData> processStepsInstances;
    private List<ParamValueData> paramValues;

    public ProcessInstanceData() {
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

    public List<ProcessStepInstanceData> getProcessStepsInstances() {
        return processStepsInstances;
    }

    public void setProcessStepsInstances(List<ProcessStepInstanceData> processStepsInstances) {
        this.processStepsInstances = processStepsInstances;
    }

    public List<ParamValueData> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueData> paramValues) {
        this.paramValues = paramValues;
    }
}
