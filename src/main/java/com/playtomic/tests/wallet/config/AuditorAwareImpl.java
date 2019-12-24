package com.playtomic.tests.wallet.config;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Long getCurrentAuditor() {
        // A real implementation will get the user from the security context...
        return 0L;
    }
}
