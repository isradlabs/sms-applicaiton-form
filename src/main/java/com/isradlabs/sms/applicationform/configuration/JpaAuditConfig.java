package com.isradlabs.sms.applicationform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.isradlabs.sms.applicationform.service.JpaAuditorAwareImpl;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return new JpaAuditorAwareImpl();
  }
}
