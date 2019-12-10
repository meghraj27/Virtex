package com.meghrajswami.virtex;

import com.meghrajswami.virtex.repository.TradeOrderRepository;
import com.meghrajswami.virtex.repository.TradeTransactionRepository;
import com.meghrajswami.virtex.repository.UserRepository;
import com.paritytrading.parity.match.OrderBook;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
public class VirtexApplication {

    private static final Logger logger = LoggerFactory.getLogger(VirtexApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VirtexApplication.class, args);
    }

    @Bean
    public ParityConfig parityConfigProvider() throws IOException {
        Config config = ConfigFactory.load("example.conf");
        return new ParityConfig(config);
    }

    @Bean
    public OrderBook orderBook(ParityConfig parityConfig, TradeOrderRepository tradeOrderRepository,
                               TradeTransactionRepository tradeTransactionRepository,
                               UserRepository userRepository) throws Exception {
        MatchOrderBook matchOrderBook = new MatchOrderBook(parityConfig, tradeOrderRepository,
                tradeTransactionRepository, userRepository);
        //load all the pending order from database into memory for matching
        matchOrderBook.regenerateOrderBook();
        return matchOrderBook.getOrderBook();
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public String getCurrentAuditor() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }
            try {
                User user = (User) auth.getPrincipal();
                return user.getUsername();
            } catch (Exception e) {
                return null;
            }
        }
    }

    //    private static final Logger logger = LoggerFactory.getLogger(VirtexApplication.class);
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
            //            auth
            //                    .inMemoryAuthentication()
            //                    .withUser("test@liferay.com").password("test").roles("USER");
            auth.jdbcAuthentication()
                    .dataSource(this.dataSource)
                    .usersByUsernameQuery("select username,password, enabled from user where username=?")
                    .authoritiesByUsernameQuery("SELECT u.username, r.name as authority " +
                            "FROM user as u " +
                            "INNER JOIN user_roles as ur " +
                            "INNER JOIN role as r " +
                            "ON u.id = ur.user_id AND r.id = ur.roles_id " +
                            "WHERE u.username = ?");
        }
    }

}
