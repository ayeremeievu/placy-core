package com.placy.placycore.core.exceptions;

/**
 * @author ayeremeiev@netconomy.net
 */
public class HttpRequestResultException extends RuntimeException {

    public HttpRequestResultException() {
    }

    public HttpRequestResultException(String message) {
        super(message);
    }

    public HttpRequestResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestResultException(Throwable cause) {
        super(cause);
    }
}
