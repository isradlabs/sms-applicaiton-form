package com.isradlabs.sms.applicationform.service;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class JpaAuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // TODO Auto-generated method stub
    return Optional.of("Developer");
  }
}
