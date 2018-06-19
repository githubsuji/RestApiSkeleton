package org.anthem.api.resources;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.anthem.api.jpa.resource.entities.Attendance;
import org.anthem.api.resources.endpoints.util.EndpointUtil;
import org.anthem.api.resources.endpoints.util.RecievedFile;
import org.anthem.api.resources.services.AttendanceService;
import org.anthem.api.resources.services.util.ServiceUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@RestController
@RequestMapping("/attendance")
public class AttendanceEndpoint {
	
	@Autowired
	private EndpointUtil endpointUtil;
	@Autowired
	private ServiceUtil serviceUtil;
	@Autowired
	private AttendanceService attendanceService;


	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // consumes
	public ResponseEntity<?> uploadAttendance(@RequestPart("attendanceExcelsheetData") String attendanceExcelsheetData,
			@RequestPart("attendanceExcelsheet") @Valid @NotNull @NotBlank MultipartFile attendanceExcelsheet,

			UriComponentsBuilder ucBuilder) {
		
		RecievedFile attendanceExcelFile = endpointUtil.getReceivedFile(attendanceExcelsheet);
		
		List<Attendance> attendanceUploaded=attendanceService.processAttendanceInfo(attendanceExcelFile);
		HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/jobcontract").buildAndExpand(jobcontractJson).toUri());
		return new ResponseEntity<>(attendanceUploaded, headers, HttpStatus.OK);
	}
	@PostMapping(value = "/byUpload", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE) // consumes
	public ResponseEntity<?> getAttendanceByUploadedDateAndFileName(@RequestBody ByUploadRequest byUploadrequest,UriComponentsBuilder ucBuilder) {
		List<Attendance> attendanceList=attendanceService.getAttendanceByUploadedDateAndFileName(byUploadrequest.getUploadedFileName(),
													byUploadrequest.getUploadedDate());
		HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/jobcontract").buildAndExpand(jobcontractJson).toUri());
		return new ResponseEntity<>(attendanceList, headers, HttpStatus.OK);
	}
	@GetMapping(value = "/dropdownData", produces = MediaType.APPLICATION_JSON_VALUE) // consumes
	public ResponseEntity<?> getAttendanceFileUploadDateAndFileName(			
						  UriComponentsBuilder ucBuilder) {
		
		List<String[]> attendanceList=attendanceService.getAttendanceFileUploadDateAndFileName();
		HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/jobcontract").buildAndExpand(jobcontractJson).toUri());
		return new ResponseEntity<>(attendanceList, headers, HttpStatus.OK);
	}
	@GetMapping(value = "/employeeIdDropdownData", produces = MediaType.APPLICATION_JSON_VALUE) // consumes
	public ResponseEntity<?> getEmployeeIds(			
						  UriComponentsBuilder ucBuilder) {
		
	
		
		List<String> employeeList=attendanceService.getAttendanceFileEmployeeIds();
		HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/jobcontract").buildAndExpand(jobcontractJson).toUri());
		return new ResponseEntity<>(employeeList, headers, HttpStatus.OK);
	}
	@GetMapping(value = "/byAttendancedate/{attendanceDate}", produces = MediaType.APPLICATION_JSON_VALUE) // consumes
	public ResponseEntity<?> getAttendanceByAttendanceDate(
			 @PathVariable("attendanceDate")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date attendanceDate, UriComponentsBuilder ucBuilder) {
		
	
		
		List<Attendance> attendanceList=attendanceService.getAttendanceByAttendanceDate(attendanceDate);
		HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/jobcontract").buildAndExpand(jobcontractJson).toUri());
		return new ResponseEntity<>(attendanceList, headers, HttpStatus.OK);
	}
	@GetMapping(value = "/byEmpid/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE) // consumes
	public ResponseEntity<?> getAttendanceByEmployeeId(
			@PathVariable("employeeId")  Long employeeId, UriComponentsBuilder ucBuilder) {		
		List attendanceList=attendanceService.getAttendanceByEmployeeId(employeeId);
		HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/jobcontract").buildAndExpand(jobcontractJson).toUri());
		return new ResponseEntity<>(attendanceList, headers, HttpStatus.OK);
	}
    
	
}

@Data
@ToString
class ByUploadRequest{
	public String uploadedFileName;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date uploadedDate;
	
}
