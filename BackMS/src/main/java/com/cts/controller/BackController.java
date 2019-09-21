package com.cts.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.config.ServiceConfig;

@RestController
public class BackController {

	@Autowired
	ServiceConfig serviceConfig;
	
	@RequestMapping(value="/jsontocsv",method = RequestMethod.POST , headers = {"content-type=text/plain"})
	public String firstProcessBackMS(@RequestBody String request)
	{
		System.out.println("request inside BackMS is : "+request);
		return request;
	}
	
	
	@RequestMapping(value="/pdftransfer")
	public String secondProcessBackMS(@RequestBody byte[] content) throws IOException
	{
		File file = new File(serviceConfig.pdfFinalPath+"copy.pdf");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content);
		fos.flush();
		fos.close();
		return "DONE";
	}
	
}
