package com.kosa.erp.system.data;

public class HumanResource extends Resource {

	public HumanResource(String name) {
		super(name);
		super.value= 50;
	}
	
	public HumanResource(String name, double value) {
		super(name);
		super.value= value;
	}
	
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("이름:");
		sb.append(name);
		sb.append(" / 월급:");
		sb.append(value);
		
		return sb.toString();
	}
}
