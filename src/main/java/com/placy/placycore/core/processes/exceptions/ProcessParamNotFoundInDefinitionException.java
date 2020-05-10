package com.placy.placycore.core.processes.exceptions;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessParamNotFoundInDefinitionException extends RuntimeException {
    private final static String MESSAGE_TEMPLATE = "Process param in definition with code : '%s' for process with code '%s' not found";

    public ProcessParamNotFoundInDefinitionException(String paramCode, String processCode) {
        super(String.format(MESSAGE_TEMPLATE, paramCode, processCode));
    }

    public ProcessParamNotFoundInDefinitionException(String paramCode, String processCode, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, paramCode, processCode), cause);
    }
}
