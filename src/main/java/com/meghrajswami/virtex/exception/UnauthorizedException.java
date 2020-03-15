/*
 * Copyright (c) 2019 TopCoder, Inc. All rights reserved.
 */
package com.meghrajswami.virtex.exception;

/**
 * This is exception is thrown if a principal is not authorized to perform an action.
 *
 * @author Meghraj
 * @version 1.0
 */
public class UnauthorizedException extends VirtexException {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 4698569801881701171L;

    /**
     * This is the constructor of <code>AccessDeniedException</code> class with message argument.
     *
     * @param message the error message.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * This is the constructor of <code>AccessDeniedException</code> class with message and cause arguments.
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

