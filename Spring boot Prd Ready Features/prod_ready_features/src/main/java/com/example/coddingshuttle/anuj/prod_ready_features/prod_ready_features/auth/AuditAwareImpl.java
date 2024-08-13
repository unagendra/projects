package com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.auth;


import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    //        get security context
//        get authentication
//        get the principle
//        get the username
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("U NAGENDRA");
    }
}
