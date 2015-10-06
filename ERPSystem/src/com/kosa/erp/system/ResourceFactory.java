package com.kosa.erp.system;

import com.kosa.erp.system.data.HumanResource;
import com.kosa.erp.system.data.MaterialResource;
import com.kosa.erp.system.data.Resource;
import com.kosa.erp.system.data.ResourceType;

public class ResourceFactory {

	public static final int RESOURCE_TYPE_HUMAN = 0;
	public static final int RESOURCE_TYPE_MATERIAL = 1;

	public Resource create(ResourceType type, String name, double value) {
		Resource r = null;
		if (type == ResourceType.HUMAN) {
			r = new HumanResource(name, value);
		} else if (type == ResourceType.MATERIAL) {
			r = new MaterialResource(name, value);
		}
		return r;
	}

	ResourceFactory() {
	}
}
