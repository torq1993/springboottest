package com.cts.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	ServiceConfig serviceConfig;
	
//	@Autowired
//	DataSource datasource;
	
//	@Autowired
//	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
//	{
//		auth.jdbcAuthentication().dataSource(datasource)
//		.usersByUsernameQuery("")
//		.authoritiesByUsernameQuery("");
//	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
		.withUser(serviceConfig.authUsername).password("{noop}"+serviceConfig.authPassword).authorities("ROLE_ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		.csrf().disable()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and().httpBasic();
	}
	
}
