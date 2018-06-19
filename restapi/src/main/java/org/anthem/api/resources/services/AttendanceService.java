package org.anthem.api.resources.services;

import java.util.Date;
import java.util.List;

import org.anthem.api.jpa.resource.entities.Attendance;
import org.anthem.api.resources.endpoints.util.RecievedFile;

public interface AttendanceService {
	List<Attendance> processAttendanceInfo(RecievedFile rFile);

	List<Attendance> getAttendanceByUploadedDateAndFileName(String uploadedFileName, Date uploadedDate);

	List<Attendance> getAttendanceByAttendanceDate(Date attendanceDate);

	List<Attendance> getAttendanceByEmployeeId(Long employeeId);

	List getAttendanceFileUploadDateAndFileName();

	List<String> getAttendanceFileEmployeeIds();

}
