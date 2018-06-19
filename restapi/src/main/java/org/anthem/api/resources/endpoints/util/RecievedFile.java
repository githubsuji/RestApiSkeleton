package org.anthem.api.resources.endpoints.util;

import java.io.InputStream;

import lombok.Data;

@Data
public class RecievedFile {
	
	private byte[] fileAsBytes;
	private String fileAsBase64;
	private String fileName;
	private InputStream fileInputStream;
	

}
