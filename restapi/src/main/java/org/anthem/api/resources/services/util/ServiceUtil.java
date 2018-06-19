package org.anthem.api.resources.services.util;

import java.io.ByteArrayOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class ServiceUtil {
	// @Autowired
	// private ResourceLoader resourceLoader;
	// private Resource resource;
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	@PostConstruct
	public void init() {
		// resource = resourceLoader.getResource("classpath:blank.pdf");
	}

	public Direction getSortDirection(String orderBy) {
		Direction direction = null;
		switch (orderBy) {
		case "desc":
			direction = Sort.Direction.DESC;
			break;
		default:
			direction = Sort.Direction.ASC;
			break;
		}
		return direction;
	}

	public byte[] createNoCertificateFoundPDF() {
		byte[] bytes = null;
		Document document = null;
		ByteArrayOutputStream outputStream = null;
		
		try {
			document = new Document();
			outputStream = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, outputStream);
			document.open();
			PdfPTable table = new PdfPTable(1);
			PdfPCell c1 = new PdfPCell(new Phrase("No Certificate Found", redFont));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBorder(0);
			table.addCell(c1);
			document.add(table);
			document.close();
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != document)
				document.close();
		}
		bytes = outputStream.toByteArray();
		return bytes;
	}

	@PreDestroy
	public void preDestroy() {
		
	}
}
