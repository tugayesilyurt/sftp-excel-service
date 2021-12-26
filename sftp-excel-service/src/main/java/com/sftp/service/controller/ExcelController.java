package com.sftp.service.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sftp.service.request.CustomerRequest;
import com.sftp.service.service.ExcelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/excel")
@RequiredArgsConstructor
public class ExcelController {
	
	private final ExcelService excelService;	
	
	@PostMapping(value="/upload-sftp")
	public ResponseEntity<?> uploadExcelToSftp(@RequestBody CustomerRequest customerRequest) throws IOException
	{
		excelService.uploadCustomerExcel(customerRequest);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

}
