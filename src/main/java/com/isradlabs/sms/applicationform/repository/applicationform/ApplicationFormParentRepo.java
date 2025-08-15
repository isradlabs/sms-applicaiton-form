package com.isradlabs.sms.applicationform.repository.applicationform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.isradlabs.sms.model.domain.applicationform.ApplicationFormParent;



public interface ApplicationFormParentRepo
    extends JpaRepository<ApplicationFormParent, Long>, JpaSpecificationExecutor<ApplicationFormParent> {
 

}
