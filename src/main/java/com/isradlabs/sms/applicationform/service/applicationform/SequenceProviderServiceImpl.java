package com.isradlabs.sms.applicationform.service.applicationform;

import java.math.BigInteger;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isradlabs.sms.applicationform.repository.applicationform.SequenceProviderRepo;
import com.isradlabs.sms.model.domain.sequence.SequenceProvider;


@Service
public class SequenceProviderServiceImpl {
  @Autowired
  SequenceProviderRepo sequenceProviderRepo;

  public String getNexSequenceNumber(String sequenceName) {
    String seqValue = null;
    Optional<SequenceProvider> sequenceProviderOptional =
        sequenceProviderRepo.getSequenceNumber(sequenceName);
    if (sequenceProviderOptional.isPresent()) {
      SequenceProvider sequenceProvider = sequenceProviderOptional.get();
      BigInteger val = sequenceProvider.getSequenceValue();
      seqValue = sequenceProvider.getSequenceName() + "-" + val;
      sequenceProvider.setSequenceValue(sequenceProvider.getSequenceValue().add(BigInteger.valueOf(1)));
          sequenceProviderRepo.save(sequenceProvider);
      // Version lastUpdate = (Version) em.createQuery("SELECT max (c.version) FROM X c")
      // .setMaxResults(1).getResultList();
    }
    return seqValue;

  }


}
