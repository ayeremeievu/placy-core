package com.placy.placycore.core.processes.exceptions;

/**
 * @author ayeremeiev@netconomy.net
 */
public class WrongExecutableParamException extends RuntimeException {

    public WrongExecutableParamException() {
    }

    public WrongExecutableParamException(String message) {
        super(message);
    }

    public WrongExecutableParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongExecutableParamException(Throwable cause) {
        super(cause);
    }
}
