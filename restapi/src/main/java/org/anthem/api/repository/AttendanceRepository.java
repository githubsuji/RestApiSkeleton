package org.anthem.api.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.anthem.api.jpa.resource.entities.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends PagingAndSortingRepository<Attendance, Long>{
	 public List<Attendance> findByUploadedDateAndUploadedFileName(Date uploadedDate,String uploadedFileName);
	 public List<Attendance> findByEmployeeId(Long employeeId);
	 public List<Attendance> findByAttendanceDate(@Temporal(TemporalType.DATE) Date attendanceDate);
	 
	 public List<Attendance> findByAttendanceDateBetween( Date attendanceDate,Date attendance);
	 
	 @Query("SELECT DISTINCT uploadedDate, uploadedFileName,uploadedDate FROM Attendance")
	public List<Object[]> findDistinctUploadedDateAndUploadedFileName();
	
	 @Query("SELECT DISTINCT employeeId FROM Attendance")
		public List<String> findDistinctEmployeeId();
	
	

}
