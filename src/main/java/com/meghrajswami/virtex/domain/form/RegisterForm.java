package com.meghrajswami.virtex.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterForm {

    @NotNull
    @Size(min = 2, max = 30, message = "name must be 2-30 characters long")
    private String name;

    @NotNull
    @Size(min = 3, max = 100, message = "email length must be 3-100 characters long")
    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 50, message = "password size must be 2-50 characters long")
    private String password;

    @AssertTrue(message = "user must agree to the terms & conditions")
    private Boolean agree;

    @Override
    public String toString() {
        return "RegisterForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password.replaceAll(".", "*") + '\'' +
                ", agree=" + agree +
                '}';
    }
}
