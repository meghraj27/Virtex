package com.meghrajswami.virtex.exception;

import com.meghrajswami.virtex.aop.LogAspect;
import com.meghrajswami.virtex.util.Helper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The global exception handler.
 *
 * @author Meghraj
 * @version 1.0
 */
//@EnableWebMvc
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle controller exception.
     *
     * @param exception the exception.
     * @return the error response entity.
     */
    @ExceptionHandler(Throwable.class)
    public void handleControllerException(HttpServletResponse response, Throwable exception) throws IOException {
        if (!(exception instanceof EmptyResultException)) {
            Helper.logException(LogAspect.LOGGER, "com.meghrajswami.virtex.exception"
                    + ".ServiceExceptionHandler#handleControllerException", exception);
        }

        HttpStatus status;
        if (exception instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof EmptyResultException) {
            status = HttpStatus.NO_CONTENT;
        } else if (exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        } else if (exception instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof ConfigurationException) {
            status = HttpStatus.CONFLICT;
        } else if (exception instanceof org.springframework.security.access.AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        response.sendError(status.value());
    }

    /**
     * Error handle for @Validate.
     * For Validating Path Variables and Request Parameters.
     *
     * @param response
     * @throws IOException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Error handle for @Valid
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

}
