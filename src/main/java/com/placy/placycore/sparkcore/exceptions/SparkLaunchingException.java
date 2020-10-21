package com.placy.placycore.sparkcore.exceptions;

public class SparkLaunchingException extends RuntimeException {

    public SparkLaunchingException() {
    }

    public SparkLaunchingException(String message) {
        super(message);
    }

    public SparkLaunchingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SparkLaunchingException(Throwable cause) {
        super(cause);
    }
}
