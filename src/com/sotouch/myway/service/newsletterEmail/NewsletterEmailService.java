package com.sotouch.myway.service.newsletterEmail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.NewsletterEmailDAO;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.NewsletterEmail;


public class NewsletterEmailService extends BaseCrudService {

	public NewsletterEmailService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public NewsletterEmail findByEmail(String email) {
		return ((NewsletterEmailDAO) entityDao).findByEmail(email);
	}
	
	public Long countByProject(Integer projectId) {
		return ((NewsletterEmailDAO) entityDao).countByProject(projectId);
	}
	
	public InputStream export(Integer projectId, Language defaultLanguage) throws IOException {
		int PIXEL_WIDTH = 36;
		List<NewsletterEmail> newsletterEmails = ((NewsletterEmailDAO) entityDao).findByProject(projectId);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("emails");
		PrintSetup  ps = sheet.getPrintSetup();
		ps.setLandscape(true);
		sheet.setColumnWidth(0, 220*PIXEL_WIDTH);
		sheet.setColumnWidth(1, 95*PIXEL_WIDTH);
		sheet.setColumnWidth(2, 90*PIXEL_WIDTH);
		sheet.setColumnWidth(3, 90*PIXEL_WIDTH);
		sheet.setColumnWidth(4, 60*PIXEL_WIDTH);
		sheet.setColumnWidth(5, 155*PIXEL_WIDTH);
		sheet.setColumnWidth(6, 155*PIXEL_WIDTH);
		sheet.setColumnWidth(7, 155*PIXEL_WIDTH);
		
		// Fonts
		Font fontHeader = workbook.createFont();
		fontHeader.setFontHeightInPoints((short)11);
	    fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    //fontHeader.setColor(IndexedColors.WHITE.getIndex());
	    fontHeader.setItalic(false);

		Font fontBody = workbook.createFont();
		fontBody.setFontHeightInPoints((short)11);

	    // Styles
	    CellStyle headerStyle = workbook.createCellStyle();
		//headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		//headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(fontHeader);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);    headerStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN); headerStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);   headerStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);  headerStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

	    CellStyle bodyStyle = workbook.createCellStyle();
	    bodyStyle.setFont(fontBody);
	    bodyStyle.setBorderTop(CellStyle.BORDER_THIN);    bodyStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN); bodyStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);   bodyStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);  bodyStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		

		// Header
		int line = 0;
		HSSFRow row = sheet.createRow(line++);
		HSSFCell cell = row.createCell(0);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("E-mail"));
		cell = row.createCell(1);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("Name"));
		cell = row.createCell(2);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("First name"));
		cell = row.createCell(3);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("City"));
		cell = row.createCell(4);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("Gender"));
		cell = row.createCell(5);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("Categories"));
		cell = row.createCell(6);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("Items"));
		cell = row.createCell(7);cell.setCellStyle(headerStyle);cell.setCellValue(new HSSFRichTextString("Terminal"));

		// Body
		for (NewsletterEmail newsletterEmail : newsletterEmails) {
			row = sheet.createRow(line++);
			cell = row.createCell(0);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getEmail()));
			cell = row.createCell(1);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getName()));
			cell = row.createCell(2);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getFirstName()));
			cell = row.createCell(3);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getCity()));
			cell = row.createCell(4);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getGender()));
			cell = row.createCell(5);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getCategories()));
			cell = row.createCell(6);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(newsletterEmail.getItems()));
			
			cell = row.createCell(7);cell.setCellStyle(bodyStyle);cell.setCellValue(new HSSFRichTextString(""));
			if(newsletterEmail.getLicense() != null) {
				for(LicenseContent licenseContent : newsletterEmail.getLicense().getLicenseContents()) {
					if(licenseContent.getLanguage().getCode().equals(defaultLanguage.getCode())) {
						cell.setCellValue(new HSSFRichTextString(licenseContent.getName()));
						break;
					}
				}
			}
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		baos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		//return new ByteArrayInputStream(workbook.getBytes());
		return bais;
	}

}
