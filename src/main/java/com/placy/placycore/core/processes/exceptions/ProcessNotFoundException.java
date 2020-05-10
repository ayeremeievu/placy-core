package com.placy.placycore.core.processes.exceptions;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Process with the code : %s, was not found";

    public ProcessNotFoundException(String code) {
        super(String.format(MESSAGE_TEMPLATE,code));
    }

    public ProcessNotFoundException(String code, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE,code), cause);
    }

    public ProcessNotFoundException(Throwable cause) {
        super(cause);
    }

    private String formatMessage(String code) {
        return String.format(MESSAGE_TEMPLATE, code);
    }
}
