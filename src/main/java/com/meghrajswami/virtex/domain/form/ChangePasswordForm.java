package com.meghrajswami.virtex.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The new password request.
 *
 * @author Meghraj
 * @version 1.0
 */
@Getter
@Setter
public class ChangePasswordForm {

    /**
     * Represents the email.
     */
    @NotNull
    @Size(min = 3, max = 100, message = "email length must be 3-100 characters long")
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
    @NotNull
    @Size(min = 2, max = 50, message = "password size must be 2-50 characters long")
    private String oldPassword;

    /**
     * The new password.
     */
    @NotNull
    @Size(min = 2, max = 50, message = "password size must be 2-50 characters long")
    private String newPassword;
}
