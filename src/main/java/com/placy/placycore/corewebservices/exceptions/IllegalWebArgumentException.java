package com.placy.placycore.corewebservices.exceptions;

import com.placy.placycore.core.processes.exceptions.BusinessException;

public class IllegalWebArgumentException extends BusinessException {

    public IllegalWebArgumentException(String message) {
        super(message);
    }

    public IllegalWebArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalWebArgumentException(Throwable cause) {
        super(cause);
    }
}
