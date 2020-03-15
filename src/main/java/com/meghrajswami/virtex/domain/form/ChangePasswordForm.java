/*
 * Copyright (c) 2019 TopCoder, Inc. All rights reserved.
 */
package com.meghrajswami.virtex.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The new password request.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Getter
@Setter
public class ChangePasswordForm {

    /**
     * Represents the email.
     */
    @NotNull
    @Size(min = 2, max = 100)
    @Email
    private String email;

    /**
     * The token.
     */
    @NotNull
    private String forgotPasswordToken;

    /**
     * Represents the old password.
     */
    @NotNull
    @Size(min = 2, max = 50)
    private String oldPassword;

    /**
     * The new password.
     */
    @NotNull
    @Size(min = 2, max = 50)
    private String newPassword;
}

