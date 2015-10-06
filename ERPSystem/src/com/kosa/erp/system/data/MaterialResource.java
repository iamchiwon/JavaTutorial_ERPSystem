package com.kosa.erp.system.data;

public class MaterialResource extends Resource {

	public MaterialResource(String name) {
		super(name);
	}
	
	public MaterialResource(String name, double value) {
		super(name);
		super.value= value;
	}
	
	public String toString() {
		String report= "";
		
		report += "이름:";
		report += name;
		report += "(가격:";
		report += value;
		report += ")";
		
		return report;
	}
}
