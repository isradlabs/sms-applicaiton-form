package com.isradlabs.sms.applicationform.repository.applicationform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.isradlabs.sms.model.domain.student.Student;



public interface StudentRepo
    extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {


}
