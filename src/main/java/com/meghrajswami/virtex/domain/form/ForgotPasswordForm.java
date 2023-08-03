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
public class ForgotPasswordForm {

    /**
     * Represents the email.
     */
    @NotNull
    @Size(min = 3, max = 100, message = "email length must be 3-100 characters long")
    @Email
    private String email;
}

