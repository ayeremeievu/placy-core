package com.placy.placycore.reviewslearning.exceptions;

public class LearningProcessException extends RuntimeException {

    public LearningProcessException() {
        super();
    }

    public LearningProcessException(String message) {
        super(message);
    }

    public LearningProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LearningProcessException(Throwable cause) {
        super(cause);
    }
}
