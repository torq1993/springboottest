package com.cts.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackController {

	@Value("${pdfFinalPath}")
	private String pdfFinalPath;
	
	@RequestMapping(value="/jsontocsv",method = RequestMethod.POST , headers = {"content-type=text/plain","auth=admin123"})
	public String firstProcessBackMS(@RequestBody String request)
	{
		System.out.println("request from FrontMS is : "+request);
		return "Returned from 1st process of BackMS: request = "+request;
	}
	
	
	@RequestMapping(value="/pdftransfer")
	public String secondProcessBackMS(@RequestBody byte[] content) throws IOException
	{
		File file = new File("C:\\karthik\\copy.pdf");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content);
		fos.flush();
		fos.close();
		return "DONE";
	}
	
}
