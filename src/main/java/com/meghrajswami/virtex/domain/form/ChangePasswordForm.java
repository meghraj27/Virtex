package com.meghrajswami.virtex.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The change password request.
 *
 * @author Meghraj
 * @version 1.0
 */
@Getter
@Setter
public class ChangePasswordForm {

    //    /**
    //     * Represents the email.
    //     */
    //    @NotNull
    //    @Size(min = 3, max = 100, message = "email length must be 3-100 characters long")
    //    @Email
    //    private String email;

    //    /**
    //     * The token.
    //     */
    //    @NotNull
    //    private String forgotPasswordToken;

    /**
     * Represents the current password.
     */
    @NotNull
    @Size(min = 2, max = 50, message = "password size must be 2-50 characters long")
    private String currentPassword;

    /**
     * The new password.
     */
    @NotNull
    @Size(min = 2, max = 50, message = "password size must be 2-50 characters long")
    private String newPassword;
}

