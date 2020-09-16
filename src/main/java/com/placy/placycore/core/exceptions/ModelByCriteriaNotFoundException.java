package com.placy.placycore.core.exceptions;

public class ModelByCriteriaNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_EXCEPTION = "Model by searching criteria '%s' not found";

    public ModelByCriteriaNotFoundException(String searchingCriteria) {
        super(formatMessage(searchingCriteria));
    }

    public ModelByCriteriaNotFoundException(String searchingCriteria, Throwable cause) {
        super(formatMessage(searchingCriteria), cause);
    }

    private static String formatMessage(String searchingCriteria) {
        return String.format(NOT_FOUND_EXCEPTION, searchingCriteria);
    }
}
