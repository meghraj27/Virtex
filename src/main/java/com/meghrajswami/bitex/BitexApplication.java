package com.meghrajswami.bitex;

import com.meghrajswami.bitex.repository.UserRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SpringBootApplication
@EnableJpaAuditing
public class BitexApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitexApplication.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<Long> {
        @Override
        public Long getCurrentAuditor() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }
            try {
                return (Long) auth.getPrincipal();
            } catch (Exception e) {
                return null;
            }
        }
    }

//    private static final Logger logger = LoggerFactory.getLogger(BitexApplication.class);
//
//    @Bean
//    public CommandLineRunner commandLineRunner(UserRepository ur) {
//        return (args) -> {
//            User user = new User(null, "meghraj27@gmail.com", "");
//            user.setUsername("megh");
//            user.setPassword("meghraj2");
//            logger.info("" + ur.save(user));
//        };
//    }

    @Configuration
//    @EnableWebSecurity
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/js/**", "/img/**", "/fonts/**", "/font-awesome/**", "/views/**", "/register", "/forgot-password").permitAll()
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
//            auth
//                    .inMemoryAuthentication()
//                    .withUser("test@liferay.com").password("test").roles("USER");
            auth.jdbcAuthentication()
                    .dataSource(this.dataSource)
                    .usersByUsernameQuery("select username,password, enabled from user where username=?")
                    .authoritiesByUsernameQuery("select u.username, r.name as authority from user as u INNER JOIN user_roles as ur INNER JOIN role as r ON u.id = ur.user_id AND r.id = ur.roles_id WHERE u.username = ?");
        }
    }

    static class UserDetailsServiceImpl implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            return null;
        }
    }
}
