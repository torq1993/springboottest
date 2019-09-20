package com.cts.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.model.FirstFrontModel;
import com.cts.utils.FrontUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonToCsvService implements BaseService{

	@Autowired
	FrontUtils utils;
	
	@Override
	public Map execute(Map inputParams) {
		
		Map outputParams = new HashMap();
		FirstFrontModel model = (FirstFrontModel) inputParams.get("requestBody");
		String csvString;
		csvString = utils.modelToCsvString(model);
		utils.setEndpoint("jsontocsv");
		String response = utils.postToBackMS(csvString);
		outputParams.put("response", response);
		return outputParams;
	}

	
}
