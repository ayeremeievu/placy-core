package com.placy.placycore.core.validation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class PropertiesValidator {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        if(constraintViolations.size() != 0) {
            throw new ValidationException(formatConstraintValidations(constraintViolations));
        }
    }

    public static final String formatConstraintValidations(Set<ConstraintViolation<Object>> constraintViolations) {
        StringBuilder builder = new StringBuilder();

        builder.append("Object constraints validation : ");

        constraintViolations.forEach(
            objectConstraintViolation -> {
                Path variablePath = objectConstraintViolation.getPropertyPath();
                Annotation annotation = objectConstraintViolation.getConstraintDescriptor().getAnnotation();

                builder.append(String.format("Variable path : [%s], annotation [%s];", variablePath, annotation));
            }
        );

        return builder.toString();
    }
}
