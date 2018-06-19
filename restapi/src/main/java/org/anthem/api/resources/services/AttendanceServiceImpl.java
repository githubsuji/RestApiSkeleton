package org.anthem.api.resources.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.anthem.api.jpa.resource.entities.Attendance;
import org.anthem.api.repository.AttendanceRepository;
import org.anthem.api.resources.endpoints.util.RecievedFile;
import org.anthem.api.resources.services.util.ServiceUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private ServiceUtil serviceUtil;

	@Autowired
	private AttendanceUtil attendanceUtil;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Override
	public List<Attendance> processAttendanceInfo(RecievedFile rFile) {
		// TODO Auto-generated method stub
		List<Attendance> attendanceList = null;
		try {
			List<String> list = attendanceUtil.getExcelSheetData(rFile.getFileInputStream());
			attendanceList = attendanceUtil.parseExcelInfo(list, rFile.getFileName());
			for (Attendance attendance : attendanceList) {
				try {
					attendanceRepository.save(attendance);
				} catch (Exception e) {
					//e.printStackTrace();
				}

			}
			if (null != attendanceList && attendanceList.size() > 0) {
				Attendance attendance = attendanceList.get(0);
				attendanceList = attendanceRepository.findByUploadedDateAndUploadedFileName(
						attendance.getUploadedDate(), attendance.getUploadedFileName());
			}

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return attendanceList;
	}

	@Override
	public List<Attendance> getAttendanceByUploadedDateAndFileName(String uploadedFileName, Date uploadedDate) {
		// TODO Auto-generated method stub
		System.out.println(uploadedFileName);
		System.out.println(uploadedDate);
		
		return attendanceRepository.findByUploadedDateAndUploadedFileName(new Date(uploadedDate.getTime()), uploadedFileName);
	}

	@Override
	public List<Attendance> getAttendanceByAttendanceDate(Date attendanceDate) {
		// TODO Auto-generated method stub
		System.out.println(attendanceDate);
//		return attendanceRepository.findByAttendanceDateBetween(attendanceDate,attendanceDate);
		return attendanceRepository.findByAttendanceDate(new Date(attendanceDate.getTime()));
	
	}

	@Override
	public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
		// TODO Auto-generated method stub
		return attendanceRepository.findByEmployeeId(employeeId);
	}

	@Override
	public List getAttendanceFileUploadDateAndFileName() {
		// TODO Auto-generated method stub
		List<Object[]> list = attendanceRepository.findDistinctUploadedDateAndUploadedFileName();
		List<DropDownData> list2 = new ArrayList<DropDownData>();
		for(Object[] o:list){
			DropDownData dd = new DropDownData();
			dd.setUploadedFileName((String)o[1]);
			dd.setUploadedDate((String)o[2]);
			list2.add(dd);
			System.out.println(o);
		}
		return list2;
	}
	@Override
	public List<String> getAttendanceFileEmployeeIds() {
		List<String> list = attendanceRepository.findDistinctEmployeeId();
		return list;
		
	}

}
