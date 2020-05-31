package com.placy.placycore.core.processes.data;

import com.placy.placycore.core.processes.model.ProcessStepResultEnum;

import java.util.Date;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ProcessStepInstanceData {
    private String code;
    private Date startDate;
    private Date finishDate;
    private String taskInstanceCode;
    private ProcessStepResultEnum processStepResult;

    public ProcessStepInstanceData() {
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

    public String getTaskInstanceCode() {
        return taskInstanceCode;
    }

    public void setTaskInstanceCode(String taskInstanceCode) {
        this.taskInstanceCode = taskInstanceCode;
    }

    public ProcessStepResultEnum getProcessStepResult() {
        return processStepResult;
    }

    public void setProcessStepResult(ProcessStepResultEnum processStepResult) {
        this.processStepResult = processStepResult;
    }
}
