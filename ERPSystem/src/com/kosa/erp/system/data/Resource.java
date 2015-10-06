package com.kosa.erp.system.data;

import java.io.Serializable;

public class Resource implements Serializable {

	private static final long serialVersionUID = 1095914620074729520L;

	protected String name;
	protected double value;

	// constructor
	public Resource() {
	}

	public Resource(String name) {
		this.name = name;
	}

	// setter
	// 메소드 형태를 다양하게 정의 : 오버로딩 (Overloading)
	// 조건 : 메소드명은 같고, 파라미터는 달라야함 (갯수 혹은 타입)
	public Resource setName(String name) {
		this.name = name;
		return this;
	}

	public Resource setName(String name, double value) {
		this.value = value;
		return setName(name);
	}

	// getter
	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	// 부모의 메소드를 재정의 => 오버라이딩 (Overriding)
	public String toString() {
		return name;
	}

	public boolean equals(Object o) {
		if (o instanceof Resource) {
			Resource r = (Resource) o;
			return this.name.equals(r.name);
		}
		return false;
	}
}
