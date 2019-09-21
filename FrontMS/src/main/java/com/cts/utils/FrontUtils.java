package com.cts.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cts.config.ServiceConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FrontUtils {
	
	@Autowired
	ServiceConfig serviceConfig;
	
	public String endpoint;
	
	public byte[] pdfToByteArray(String fileName) throws IOException
	{
		File file = new File(serviceConfig.pdfInitPath+fileName);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		for(int readNum;(readNum = fis.read(buff))!= -1;)
		{
			bos.write(buff,0,readNum);
		}
		return bos.toByteArray();
	}
	
	public String jsonToCsvString(String jsonString)
	{
		System.out.println("jsonString : "+jsonString);
		ObjectMapper mapper = new ObjectMapper();
		String [] paramsArray = null;
		StringBuilder csvBuilder = new StringBuilder();
		try {
			List csvList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Object.class));
			System.out.println("csvList : "+csvList);
			for(Object entry : csvList)
			{
				if(paramsArray == null)
				{
					String[] paramValue = entry.toString().split(",");
					
//					System.out.println("paramValue : "+paramValue[0]);
//					System.out.println("paramValue : "+paramValue[1]);
//					System.out.println("paramValue : "+paramValue[2]);
				}
				String entryString = entry.toString();
				System.out.println("Entry : "+entryString);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "asssdaasd";
	}
	
	public String jsonToCsvStringHM(String jsonString)
	{
		System.out.println("jsonString : "+jsonString);
		ObjectMapper mapper = new ObjectMapper();
		String [] paramsArray = null;
		StringBuilder csvBuilder = new StringBuilder();
		try {
			List csvList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Map.class));
			System.out.println("csvList : "+csvList);
			for(Object entry : csvList)
			{
				Map entryMap = (Map) entry;
//				System.out.println("keySet : "+entryMap.keySet());				
//				System.out.println("valueSet : "+entryMap.values());
				if(csvBuilder.length() == 0)
				{
					csvBuilder.append("#");
					for(Object key : entryMap.keySet())
					{
						csvBuilder.append(key.toString()+",");
					}
					csvBuilder.deleteCharAt(csvBuilder.length()-1);
					csvBuilder.append("\n");
				}
				for(Object value : entryMap.values())
				{
					csvBuilder.append(value.toString()+",");
				}
				csvBuilder.deleteCharAt(csvBuilder.length()-1);
				csvBuilder.append("\n");
			}
			csvBuilder.deleteCharAt(csvBuilder.length()-1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("csvBuilderString : \n"+csvBuilder.toString());
		return csvBuilder.toString();
	}
	public String postToBackMS(Object requestBody)
	{
		String url = serviceConfig.backMSBaseUrl+endpoint;
		System.out.println("BackMS URL : "+url);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		//headers.set("auth", "admin123");
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
		Encoder encoder = Base64.getEncoder();
		String basicAuthCred = serviceConfig.backMSUsername+":"+serviceConfig.backMSPassword;
		System.out.println("basicAuthCred before encoding : "+basicAuthCred);
		basicAuthCred = encoder.encodeToString(basicAuthCred.getBytes());
		System.out.println("basicAuthCred after encoding : "+basicAuthCred);
		String basicAuthValue = "Basic "+basicAuthCred;
		headers.set("Authorization", basicAuthValue);
		HttpEntity<Object> request = new HttpEntity<>(requestBody,headers);
		String response = restTemplate.postForObject(url, request, String.class);
		return response;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public static void main(String args[])
	{
		FrontUtils obj = new FrontUtils();
		Map<String,String> mapObj = new HashMap<String,String>();
		mapObj.put("param3", "value3");
		mapObj.put("param2", "value2");
		mapObj.put("param1", "value1");
		mapObj.put("paramC", "valueC");
		mapObj.put("paramB", "valueB");
		mapObj.put("paramA", "valueA");
		System.out.println(mapObj);
		for(Map.Entry<String,String> entry : mapObj.entrySet())
		{
			System.out.println("Key = " + entry.getKey() + 
			                             ", Value = " + entry.getValue()); 
		}
		ObjectMapper mapper = new ObjectMapper();

		String json = "[{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"},{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"},{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"}]";
		try {
			List csvList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Map.class));
			System.out.println("csvList : "+csvList);
			for(Object entry : csvList)
			{
				Map entryMap = (Map) entry;
				System.out.println("MapEntry : "+entryMap);
				System.out.println("keySet : "+entryMap.keySet());
				System.out.println("valueSet : "+entryMap.values());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
