package com.cts.model;

public class FirstFrontModel {

	String param1;
	String param2;
	String param3;
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	@Override
	public String toString()
	{
		return "[param1="+this.param1+",param2="+this.param2+",param3="+this.param3+"]";
	}
	
}
