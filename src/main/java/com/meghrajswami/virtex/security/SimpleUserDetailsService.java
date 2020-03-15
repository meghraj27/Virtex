package com.meghrajswami.virtex.security;

import com.meghrajswami.virtex.domain.Role;
import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.exception.ConfigurationException;
import com.meghrajswami.virtex.service.UserService;
import com.meghrajswami.virtex.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

/**
 * The simple implementation of the UserDetailsService.
 *
 * @author Meghraj
 * @version 1.0
 */
@Service
@Qualifier("simpleUserDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {
    /**
     * The UserService to load user by username.
     */
    @Autowired
    private UserService userService;

    /**
     * Check if all required fields are initialized properly.
     *
     * @throws ConfigurationException if any required field is not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(userService, "userService");
    }

    /**
     * Locates the user based on the username.
     *
     * @param username the user email.
     * @return the UserDetails
     * @throws UsernameNotFoundException if there is no match or invalid user found.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("No user found with username %s", username));
            }
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                throw new UsernameNotFoundException(
                        String.format("The user with fullName %s does not have roles defined", username));
            }
            List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to get user data", e);
        }
    }

    /**
     * Build the user authority.
     *
     * @param roles the user roles
     * @return the authority
     */
    private List<GrantedAuthority> buildUserAuthority(Collection<Role> roles) {
        Set<GrantedAuthority> auths = new HashSet<>();
        for (Role role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(auths);
    }

    /**
     * Build the user details entity.
     *
     * @param user        the user
     * @param authorities the authorities
     * @return user details entity
     */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new CustomUserDetails(user, user.getEnabled(), true, true, true, authorities);
    }
}
