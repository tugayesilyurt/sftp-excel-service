package com.sftp.service.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sftp.service.config.SftpConfig;
import com.sftp.service.request.CustomerRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelService {

	private final static String[] columns = { "Name", "Surname", "Age" };
	
	private final SftpConfig sftpConfig;
	
	private final String localWritePath = "/local/path";
	private final String remoteUploadPath = "/remote/path";

	public void uploadCustomerExcel(CustomerRequest customerRequest) throws IOException {

		FileOutputStream fileOut = null;
		XSSFWorkbook workbook=null;
		try {
			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Customer");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowNum = 1;

			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(customerRequest.getName());
			row.createCell(1).setCellValue(customerRequest.getSurname());
			row.createCell(2).setCellValue(customerRequest.getAge());

			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}

			String localPath = localWritePath.concat("sftppoi-generated-file.xlsx");
			fileOut = new FileOutputStream(localPath);
			workbook.write(fileOut);
			
			//UPLOAD FILE
			sftpConfig.uploadFile(localPath, remoteUploadPath);
			//UPLOAD FILE
			
		} catch (Exception e) {
			log.error("uploadCustomerExcel error " + e.getMessage());
		} finally {
			if(null!=fileOut)
				fileOut.close();
			if(null!=workbook)
				workbook.close();
		}

	}

}
