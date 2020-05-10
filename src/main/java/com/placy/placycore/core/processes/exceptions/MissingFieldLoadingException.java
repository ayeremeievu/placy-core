package com.placy.placycore.core.processes.exceptions;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class MissingFieldLoadingException extends RuntimeException {
    private final static String MESSAGE_TEMPLATE = "Property from json is missing : %s";

    public MissingFieldLoadingException(String property) {
        super(String.format(MESSAGE_TEMPLATE, property));
    }

    public MissingFieldLoadingException(String property, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, property), cause);
    }
}
