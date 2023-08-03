package com.meghrajswami.virtex.service.springdata;

import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.domain.form.ChangePasswordForm;
import com.meghrajswami.virtex.domain.form.ForgotPasswordForm;
import com.meghrajswami.virtex.domain.form.RegisterForm;
import com.meghrajswami.virtex.exception.AccessDeniedException;
import com.meghrajswami.virtex.repository.UserRepository;
import com.meghrajswami.virtex.service.TradeService;
import com.meghrajswami.virtex.service.UserService;
import com.meghrajswami.virtex.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    private UserRepository userRepository;

    public User createUser(RegisterForm username) {
        if (checkUserExist(username.getEmail()))
            throw new EntityExistsException("The user '" + username.getEmail() + "' already exists");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(username.getPassword());
        User newUser = new User(username.getName(), username.getEmail(), encodedPassword);

        return userRepository.save(newUser);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void changePassword(ChangePasswordForm changePasswordForm) throws AccessDeniedException {
        String username = Helper.getAuthUser().getUsername();
        User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(changePasswordForm.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new AccessDeniedException("Enter correct current password");
        }

    }

    @Override
    public void forgotPassword(ForgotPasswordForm forgotPasswordForm) {
        checkUserExist(forgotPasswordForm.getEmail());
        //Todo: password reset procedure
    }

    private boolean checkUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }


}
