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
		sb.append("�̸�:");
		sb.append(name);
		sb.append(" / ����:");
		sb.append(value);
		
		return sb.toString();
	}
}
