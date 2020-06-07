package com.placy.placycore.corewebservices.exceptions;

import com.placy.placycore.core.processes.exceptions.BusinessException;
import com.placy.placycore.corewebservices.dto.ErrorDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author ayeremeiev@netconomy.net
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBadRequestError(WebRequest webRequest, BusinessException ex) throws Exception {


        return super.handleExceptionInternal(ex, new ErrorDto(
            ex.getClass().getName(), ex.getMessage()
        ), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
