package com.placy.placycore.corewebservices.processes.dto;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class TaskInstanceDto {
    private String taskCode;
    private TaskInstanceStatusEnum status;
    private List<ParamValueDto> params;
    private Date startDate;
    private Date finishDate;

    public TaskInstanceDto() {
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public TaskInstanceStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskInstanceStatusEnum status) {
        this.status = status;
    }

    public List<ParamValueDto> getParams() {
        return params;
    }

    public void setParams(List<ParamValueDto> params) {
        this.params = params;
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
}
