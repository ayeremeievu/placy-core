package com.placy.placycore.corewebservices.dto;

import java.util.Date;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ErrorDto {
    private final String type;
    private final String message;
    private final Date date = new Date();

    public ErrorDto(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
