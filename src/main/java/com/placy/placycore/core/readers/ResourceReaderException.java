package com.placy.placycore.core.readers;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ResourceReaderException extends RuntimeException {

    public ResourceReaderException() {
        super();
    }

    public ResourceReaderException(String message) {
        super(message);
    }

    public ResourceReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceReaderException(Throwable cause) {
        super(cause);
    }

    protected ResourceReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
