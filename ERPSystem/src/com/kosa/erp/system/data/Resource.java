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
	// �޼ҵ� ���¸� �پ��ϰ� ���� : �����ε� (Overloading)
	// ���� : �޼ҵ���� ����, �Ķ���ʹ� �޶���� (���� Ȥ�� Ÿ��)
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

	// �θ��� �޼ҵ带 ������ => �������̵� (Overriding)
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
