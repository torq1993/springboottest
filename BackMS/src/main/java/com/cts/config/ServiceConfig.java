package com.cts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

	@Value("${authUsername}")
	public String authUsername;
	
	@Value("${authPassword}")
	public String authPassword;
	
	@Value("${pdfFinalPath}")
	public String pdfFinalPath;
	
	
}
