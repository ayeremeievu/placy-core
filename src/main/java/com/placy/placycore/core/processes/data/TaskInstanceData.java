package com.placy.placycore.core.processes.data;

import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class TaskInstanceData {
    private String code;
    private String taskCode;
    private TaskInstanceStatusEnum status;
    private List<ParamValueData> params;
    private Date startDate;
    private Date finishDate;

    public TaskInstanceData() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<ParamValueData> getParams() {
        return params;
    }

    public void setParams(List<ParamValueData> params) {
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
