package com.placy.placycore.core.processes.exceptions;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ExecutiveEntityLoadingException extends RuntimeException {

    public ExecutiveEntityLoadingException() {
    }

    public ExecutiveEntityLoadingException(String message) {
        super(message);
    }

    public ExecutiveEntityLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutiveEntityLoadingException(Throwable cause) {
        super(cause);
    }

    public ExecutiveEntityLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
