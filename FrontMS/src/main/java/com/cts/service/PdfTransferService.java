package com.cts.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.utils.FrontUtils;

@Service
public class PdfTransferService implements BaseService{

	@Autowired
	FrontUtils utils;
	
	@Override
	public Map execute(Map inputParams)
	{	
		String fileName = (String) inputParams.get("fileName");
		byte[] pdfBytes = null;
		try {
			pdfBytes = utils.pdfToByteArray(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utils.setEndpoint("pdftransfer");
		
		String response = utils.postToBackMS(pdfBytes);
		Map outputParams = new HashMap();
		outputParams.put("response", response);
		return outputParams;
	}
	

}
