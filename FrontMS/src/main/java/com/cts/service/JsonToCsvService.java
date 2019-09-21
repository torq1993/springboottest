package com.cts.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.utils.FrontUtils;

@Service
public class JsonToCsvService implements BaseService{

	@Autowired
	FrontUtils utils;
	
	@Override
	public Map execute(Map inputParams) {
		
		Map outputParams = new HashMap();
		String jsonString = (String) inputParams.get("requestBody");
		String csvString;
		csvString = utils.jsonToCsvStringHM(jsonString);
		utils.setEndpoint("jsontocsv");
		String response = utils.postToBackMS(csvString);
		outputParams.put("response", response);
		return outputParams;
	}

	
}
