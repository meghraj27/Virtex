package com.meghrajswami.virtex.service;

import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.domain.form.ForgotPasswordForm;
import com.meghrajswami.virtex.domain.form.RegisterForm;

public interface UserService {

    /**
     * Create new user.
     *
     * @param registerForm The register form object
     * @return new {@link User} object
     */
    User createUser(RegisterForm registerForm);

    /**
     * Get a user by username.
     *
     * @param username The username of the user
     * @return the {@link User} object
     */
    User getUserByUsername(String username);

    void forgotPassword(ForgotPasswordForm forgotPasswordForm);
}
