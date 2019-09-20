package com.cts.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cts.model.FirstFrontModel;

@Component
public class FrontUtils {
	
	@Value("${BackMSBaseUrl}")
	private String baseUrl;
	
	@Value("${pdfFileName}")
	private String pdfName;
	
	@Value("${pdfInitPath}")
	private String pdfInitPath;
	
	public String endpoint;
	
	public byte[] pdfToByteArray(String fileName) throws IOException
	{
		File file = new File(pdfInitPath+fileName);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		for(int readNum;(readNum = fis.read(buff))!= -1;)
		{
			bos.write(buff,0,readNum);
		}
		return bos.toByteArray();
	}
	
	public String modelToCsvString(FirstFrontModel model)
	{
		StringBuilder csvBuilder = new StringBuilder();
		csvBuilder.append(model.getParam1());
		csvBuilder.append(",");
		csvBuilder.append(model.getParam2());
		csvBuilder.append(",");
		csvBuilder.append(model.getParam3());
		return csvBuilder.toString();
	}
	public String postToBackMS(Object requestBody)
	{
		String url = baseUrl+endpoint;
		System.out.println("BackMS URL : "+url);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("auth", "admin123");
		if(endpoint.equalsIgnoreCase("jsontocsv"))
		{
			headers.set("content-type", "text/plain");
		}
		else if(endpoint.equalsIgnoreCase("pdftransfer"))
		{
			headers.setContentType(MediaType.APPLICATION_PDF);
			//headers.setContentDispositionFormData(pdfName, pdfName);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		}
		HttpEntity<Object> request = new HttpEntity<>(requestBody,headers);
		String response = restTemplate.postForObject(url, request, String.class);
		return response;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
}
