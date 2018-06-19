package org.anthem.api.jpa.resource.entities;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "ATTENDANCE", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "EMPLOYEE_ID", "ATTENDANCE_DATE" }, name = "Attendance_UNIQUE") })
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long attendanceId;
	

	@Column(name = "SERIAL_NO_FROM_EXCEL")
	private String sNo;
	
	@Column(name = "EMPLOYEE_ID")	
	private Long employeeId;
	
	@Column(name = "EMPLOYEE_NAME")	
	private String employeeName;
	
	@Column(name = "DEPARTMENT")	
	private String department;
	
	@Column(name = "SHIFT")	
	private String shift;
	
	@Column(name = "IN1")	
	private String in1;
	
	@Column(name = "IN2")
	private String in2;
	
	@Column(name = "IN3")
	private String in3;
	
	@Column(name = "IN4")
	private String in4;
	
	@Column(name = "IN5")
	private String in5;
	
	@Column(name = "OUT1")
	private String out1;
	
	@Column(name = "OUT2")
	private String out2;
	
	@Column(name = "OUT3")
	private String out3;
	
	@Column(name = "OUT4")
	private String out4;
	
	@Column(name = "OUT5")
	private String out5;
	
	@Column(name = "ATTENDANCE_DATE")
	 @JsonFormat(pattern="yyyy-MM-dd")
	private Date attendanceDate;
	
	@Column(name = "DAY_TYPE")
	private String dayType;//working day, holiday, weekend
	
	@Column(name = "HOLIDAY_NAME")
	private String holidayName; 
	
	@Column(name = "ATTENDANCE_STATUS")
	private String attendanceSatus;
	
	@Column(name = "UPLOADED_FILENAME")
	private String uploadedFileName;
	@Column(name = "UPLOADED_DATE")
	 @JsonFormat(pattern="yyyy-MM-dd")
	private Date uploadedDate;
	


}
