package com.placy.placycore.core.processes.exceptions;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Task with the code : %s, was not found";

    public TaskNotFoundException(String code) {
        super(String.format(MESSAGE_TEMPLATE,code));
    }

    public TaskNotFoundException(String code, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE,code), cause);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }

    private String formatMessage(String code) {
        return String.format(MESSAGE_TEMPLATE, code);
    }
}
