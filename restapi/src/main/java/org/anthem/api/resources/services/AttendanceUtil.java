package org.anthem.api.resources.services;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.anthem.api.jpa.resource.entities.Attendance;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

@Component
public class AttendanceUtil {

	public  List<String> getExcelSheetData(InputStream excelIStream)
			throws IOException, EncryptedDocumentException, InvalidFormatException {

		Workbook workbook = WorkbookFactory.create(excelIStream);

		workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);

		Sheet sheet = workbook.getSheetAt(0);

		DataFormatter dataFormatter = new DataFormatter();

		List<String> list = new ArrayList<String>();

		for (Row row : sheet) {

			// System.out.println("-------Row Number=="+row.getRowNum());

			StringBuilder sb = new StringBuilder();

			boolean isValue = false;

			for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {

				Cell cell = row.getCell(columnNumber);

				if (cell == null) {

					sb.append("BLANK");

					sb.append("|");

					// System.out.print (" BLANK ");

					// System.out.println("------Column
					// Number==="+columnNumber);

					// This cell is empty/blank/un-used, handle as needed

				} else {

					isValue = true;

					// System.out.println("------Column
					// Number==="+columnNumber);

					String cellValue = dataFormatter.formatCellValue(cell);

					sb.append(cellValue);

					sb.append("|");

					// System.out.print (cellValue+" ");

					// Do something with the value

				}

			}

			if (sb.length() > 0 && isValue) {

				list.add(sb.toString());

			}

			// System.out.println();

		}

		return list;

	}

	public  Date formatAsDate(String date) {
		DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
		Date formattedDate = null;
		try {
			formattedDate = df.parse(date);
		} catch (ParseException e) {
		}
		// df = new SimpleDateFormat("yyyy-MM-dd");
		// String dateStr = df.format(formattedDate);
		// try {
		// formattedDate = df.parse(dateStr);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return formattedDate;
	}

	public  Date nowDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(new Date());
		Date now = null;
		try {
			now = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return now;
	}

	public  String formatDateStr(String date) {
		DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
		Date formattedDate = null;
		String formattedDateStr = null;
		try {
			formattedDate = df.parse(date);
			formattedDateStr = df.format(formattedDate);
		} catch (ParseException e) {
		}
		return formattedDateStr;
	}

	public  ArrayList<Attendance> parseExcelInfo(List<String> list, String fileName) {
		Date uploadedDate = nowDate();

		ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
		String date = null;
		Date dateObj = null;
		String holidayName = "";
		String dayType = "";
		for (String str : list) {

			// System.err.println(str.length()+"-----------> "+str);
			if (str.contains("Date|BLANK|")) {
				String[] dArr = str.split("\\|");

				date = dArr[2];
				dayType = getDayType(dArr);
				holidayName = getHolidayName(dArr);
				dateObj = formatAsDate(date);
				System.err.println(dateObj);
				date = formatDateStr(date);

				// System.err.println(date);

			}
			if (!str.contains("Date|BLANK|")
					&& !str.contains("Daily Attendance IN/OUT Punch Report (IN/OUT Punch Report)|BLANK")
					&& str.length() != 150 && !str.contains("Company:|BLANK|MBM|")
					&& !str.contains("SNo|E. Code|Name|BLANK|Department|Shift|")) {
				Attendance attendanceObj = new Attendance();
				String[] sArr = str.split("\\|");
				attendanceObj.setSNo(sArr[0]);
				attendanceObj.setEmployeeId(new Long(sArr[1].trim()));
				attendanceObj.setEmployeeName(sArr[2]);
				attendanceObj.setDepartment(sArr[4]);
				attendanceObj.setShift(sArr[5]);
				attendanceObj.setIn1(sArr[7].replaceAll("BLANK", ""));
				attendanceObj.setOut1(sArr[9].replaceAll("BLANK", ""));
				attendanceObj.setIn2(sArr[11].replaceAll("BLANK", ""));
				attendanceObj.setOut2(sArr[12].replaceAll("BLANK", ""));
				attendanceObj.setIn3(sArr[13].replaceAll("BLANK", ""));
				attendanceObj.setOut3(sArr[15].replaceAll("BLANK", ""));
				attendanceObj.setIn4(sArr[16].replaceAll("BLANK", ""));
				attendanceObj.setOut4(sArr[17].replaceAll("BLANK", ""));
				attendanceObj.setIn5(sArr[18].replaceAll("BLANK", ""));
				attendanceObj.setOut5(sArr[19].replaceAll("BLANK", ""));
				attendanceObj.setUploadedFileName(fileName);
				attendanceObj.setUploadedDate(new java.sql.Date(uploadedDate.getTime()));
				attendanceObj.setAttendanceDate(new java.sql.Date(dateObj.getTime()));
				attendanceObj.setHolidayName(holidayName);
				attendanceObj.setDayType(dayType);
				attendanceObj.setAttendanceSatus(getAttendanceStatus(attendanceObj));
				attendanceList.add(attendanceObj);
			}

		}
		return attendanceList;

	}

	public static String getAttendanceStatus(Attendance attendance) {
		String in1 = attendance.getIn1();
		String in2 = attendance.getIn2();
		String in3 = attendance.getIn3();
		String in4 = attendance.getIn4();
		String in5 = attendance.getIn5();
		String attendanceStatus = null;
		if (StringUtils.isNotBlank(in1) || StringUtils.isNotBlank(in2) || StringUtils.isNotBlank(in3)
				|| StringUtils.isNotBlank(in4) || StringUtils.isNotBlank(in5)) {
			attendanceStatus = "PRESENT";

		} else {
			
			attendanceStatus = "VACATION";
		}
		return attendanceStatus;
	}

	public static String getDayType(String[] dArr) {
		String dayInfo = dArr[4];
		String dayType = "WORKING DAY";
		if (dayInfo.contains("HOLIDAY")) {
			dayType = "HOLIDAY";
		} else if (!dayInfo.contains("BLANK")) {
			dayType = dayInfo;
		}
		return dayType;
	}

	public static String getHolidayName(String[] dArr) {
		String dayInfo = dArr[4];
		String holidayName = "";
		if (dayInfo.contains("HOLIDAY")) {
			String[] dInfoArr = dayInfo.split("\\s");
			holidayName = dInfoArr[0];
		}
		return holidayName;
	}

}
