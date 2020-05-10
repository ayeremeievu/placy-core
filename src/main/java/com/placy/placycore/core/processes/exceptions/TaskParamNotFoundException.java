package com.placy.placycore.core.processes.exceptions;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskParamNotFoundException extends RuntimeException {
    private final static String MESSAGE_TEMPLATE = "Parameter with code : %s for task with code : %s not found";

    public TaskParamNotFoundException(String paramCode, String taskCode) {
        super(String.format(MESSAGE_TEMPLATE, paramCode, taskCode));
    }

    public TaskParamNotFoundException(String paramCode, String taskCode, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, paramCode, taskCode), cause);
    }
}
