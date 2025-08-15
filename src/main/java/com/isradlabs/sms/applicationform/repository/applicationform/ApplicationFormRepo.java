package com.isradlabs.sms.applicationform.repository.applicationform;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.isradlabs.sms.model.domain.applicationform.ApplicationFormStudent;



public interface ApplicationFormRepo
    extends JpaRepository<ApplicationFormStudent, Long>, JpaSpecificationExecutor<ApplicationFormStudent> {
  @Query("select a from ApplicationFormStudent a where a.id = ?1 and (a.softDelete = false or a.softDelete is null)")
  Optional<ApplicationFormStudent> findByIdWithActive(Long recordId);

  // Method to get a unique list of course names sorted by academicYear descending
  @Query("SELECT DISTINCT s.academicYear FROM ApplicationFormStudent s where s.academicYear is not null ORDER BY s.academicYear DESC")
  List<String> getUniqueAcademicYearsSortedByAcademicYear();


}
