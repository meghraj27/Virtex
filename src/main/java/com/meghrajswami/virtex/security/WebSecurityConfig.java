package com.meghrajswami.virtex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * The user detail service.
     */
    @Autowired
    @Qualifier("simpleUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/img/**", "/fonts/**", "/font-awesome/**",
                        "/views/**", "/register", "/forgot-password").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
                .and()
                .logout().permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //        auth
        //                .inMemoryAuthentication()
        //                .withUser("test@liferay.com").password("{noop}test").roles("USER");
        //        auth.jdbcAuthentication()
        //                .dataSource(this.dataSource)
        //                .passwordEncoder(new BCryptPasswordEncoder())
        //                .usersByUsernameQuery("select username,password, enabled from user where username=?")
        //                .authoritiesByUsernameQuery("SELECT u.username, r.name as authority " +
        //                        "FROM user as u " +
        //                        "INNER JOIN user_roles as ur " +
        //                        "INNER JOIN role as r " +
        //                        "ON u.id = ur.user_id AND r.id = ur.roles_id " +
        //                        "WHERE u.username = ?");

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userDetailsService);
    }
}