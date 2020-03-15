package com.meghrajswami.virtex;

import com.meghrajswami.virtex.repository.TradeOrderRepository;
import com.meghrajswami.virtex.repository.TradeTransactionRepository;
import com.meghrajswami.virtex.repository.UserRepository;
import com.paritytrading.parity.match.OrderBook;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class VirtexApplication {

    @Value("${virtex.instruments-config}")
    private String INSTRUMENTS_CONFIG;

    private static final Logger logger = LoggerFactory.getLogger(VirtexApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VirtexApplication.class, args);
    }

    @Bean
    public ParityConfig parityConfigProvider() throws IOException {
        Config config = ConfigFactory.load(INSTRUMENTS_CONFIG);
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
        public Optional<String> getCurrentAuditor() {
            return Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal)
                    .map(User.class::cast)
                    .map(User::getUsername);
        }
    }


}
