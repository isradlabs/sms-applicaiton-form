package com.isradlabs.sms.applicationform.repository.applicationform;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.isradlabs.sms.model.domain.sequence.SequenceProvider;



public interface SequenceProviderRepo
    extends JpaRepository<SequenceProvider, Long>, JpaSpecificationExecutor<SequenceProvider> {

//Method to get a unique list of course names sorted by academicYear descending
  // Version lastUpdate = (Version) em.createQuery("SELECT max (c.version) FROM X c")          .setMaxResults(1).getResultList();
 @Query("SELECT s FROM SequenceProvider s where s.sequenceName= ?1")
 Optional<SequenceProvider> getSequenceNumber(String sequenceName);

}
