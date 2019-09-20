package com.cts.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.model.FirstFrontModel;
import com.cts.service.JsonToCsvService;
import com.cts.service.PdfTransferService;

@RestController
public class FrontController {

	@Autowired
	JsonToCsvService jsonToCsvService;
	
	@Autowired
	PdfTransferService pdfTransferService;
	
	@RequestMapping(value = "/jsontocsv",method = RequestMethod.POST, headers = {"content-type=application/json","auth=admin123"})
	public String jsonToCsv(@RequestBody FirstFrontModel modelObj)
	{
		System.out.println("Request Body : "+modelObj.toString());
		Map inputParams = new HashMap();
		inputParams.put("requestBody", modelObj);
		Map outputParams = jsonToCsvService.execute(inputParams);
		String result = (String) outputParams.get("response");
		return result;
	}
	
	
	@RequestMapping(value = "/pdfdownload",method = RequestMethod.GET)
	public ResponseEntity<byte[]> pdfDownload() throws FileNotFoundException
	{
		File file = new File("C:\\karthik\\2011103608_104567-2019_06_22_09_29_39_.pdf");
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try{
			for(int readNum; (readNum = fis.read(buf)) != -1;)
			{
				bos.write(buf, 0, readNum);
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		String fileName = "random.pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData(fileName, fileName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<>(bytes,headers,HttpStatus.OK);
		return response;
	}
	@RequestMapping(value = "/pdftransfer/{id}",method = RequestMethod.POST,headers = {"content-type=application/json","auth=admin123"})
	public String pdfTransfer(@PathVariable String id ) throws FileNotFoundException
	{
		Map inputParams = new HashMap();
		String fileName = id+".pdf";
		inputParams.put("fileName", fileName);
		Map outputParams = pdfTransferService.execute(inputParams);
		String result = (String) outputParams.get("response");
		return result;
//		pdfTransferService.execute(inputParams);
//		File file = new File("C:\\karthik\\sample.pdf");
//		String url = "http://localhost:8480/BackMS/secondprocess";
//		FileInputStream fis = new FileInputStream(file);
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		byte[] buf = new byte[1024];
//		try{
//			for(int readNum; (readNum = fis.read(buf)) != -1;)
//			{
//				bos.write(buf, 0, readNum);
//			}
//		}
//		catch(IOException ex)
//		{
//			ex.printStackTrace();
//		}
//		byte[] bytes = bos.toByteArray();
//		String fileName = "random.pdf";
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_PDF);
//		headers.setContentDispositionFormData(fileName, fileName);
//		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//		HttpEntity<byte[]> request = new HttpEntity<byte[]>(bytes,headers);
//		RestTemplate restTemplate = new RestTemplate();
//		String response = restTemplate.postForObject(url, request, String.class);
//		return response;
	}
	
}
