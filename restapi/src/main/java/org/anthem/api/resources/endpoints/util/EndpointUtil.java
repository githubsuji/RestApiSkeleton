package org.anthem.api.resources.endpoints.util;

import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EndpointUtil {	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public RecievedFile getReceivedFile(MultipartFile mpf) {
		byte[] bytes =null;
		RecievedFile rf = null;		
		if (null !=  mpf && !mpf.isEmpty()) {
			 try {
				
				bytes = mpf.getBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 rf = new RecievedFile();
			 rf.setFileAsBytes(bytes);
			 rf.setFileAsBase64(getBase64String(bytes));
			 rf.setFileName(mpf.getOriginalFilename());
			 try {
				rf.setFileInputStream(mpf.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rf;
	}
	public byte[] getEmptyProfilePicture(){
		byte[] bytes =null;
		Resource resource = resourceLoader.getResource("classpath:images/noprofile.jpg");
		try {
			bytes = Files.readAllBytes(resource.getFile().toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	
	public String getBase64String(byte[] bytes){		
		return null != bytes ? Base64.getEncoder().encodeToString(bytes) : "";
	}
	public Date stringToDate(String dateStr){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date  = sf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public Date getDate(String dateStr){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		Date now = null;
		try {
			now = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return now;
	}
	
	
	
	

}
