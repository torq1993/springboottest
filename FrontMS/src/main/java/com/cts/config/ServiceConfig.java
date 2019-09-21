package com.cts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

	@Value("${authUsername}")
	public String authUsername;
	
	@Value("${authPassword}")
	public String authPassword;
	
	@Value("${backMSBaseUrl}")
	public String backMSBaseUrl;
	
	@Value("${pdfInitPath}")
	public String pdfInitPath;
	
	@Value("${backMSUsername}")
	public String backMSUsername;
	
	@Value("${backMSPassword}")
	public String backMSPassword;
}
