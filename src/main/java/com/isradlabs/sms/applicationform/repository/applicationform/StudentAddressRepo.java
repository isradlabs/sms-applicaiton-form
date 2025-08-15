package com.isradlabs.sms.applicationform.repository.applicationform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.isradlabs.sms.model.domain.student.StudentAddress;



public interface StudentAddressRepo
    extends JpaRepository<StudentAddress, Long>, JpaSpecificationExecutor<StudentAddress> {


}
