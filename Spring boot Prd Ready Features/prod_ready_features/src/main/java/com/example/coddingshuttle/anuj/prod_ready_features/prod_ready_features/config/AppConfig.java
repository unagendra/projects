package com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.config;

import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.auth.AuditAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 4.Enable JPA Auditing by adding the @EnableJpaAuditing annotation to
 * a configuration class.
 * 5. Pass the reference of the AuditorAware class bean to the
 * @EnableJpaAuditing interface
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditAwareImpl")
public class AppConfig {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    @Bean
    AuditorAware<String> getAuditAwareImpl(){
        return new AuditAwareImpl();
    }
}
