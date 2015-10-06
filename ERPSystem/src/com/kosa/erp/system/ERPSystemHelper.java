package com.kosa.erp.system;

import com.kosa.erp.system.data.HumanResource;
import com.kosa.erp.system.data.MaterialResource;
import com.kosa.erp.system.data.Resource;
import com.kosa.erp.system.data.ResourceType;

public class ERPSystemHelper {
	public static void addHuman(String name, double salary) throws Exception {
		ERPSystem.getInstance()
				.addResource(ERPSystem.getInstance().getFactory().create(ResourceType.HUMAN, name, salary));
	}

	public static void addMaterial(String name, double price) throws Exception {
		ERPSystem.getInstance()
				.addResource(ERPSystem.getInstance().getFactory().create(ResourceType.MATERIAL, name, price));
	}

	public static void update(String prevName, String newName, double newVal) {
		Resource r = ERPSystem.getInstance().searchByName(prevName);
		ResourceType type = ResourceType.HUMAN;
		if (r instanceof MaterialResource)
			type = ResourceType.MATERIAL;
		ERPSystem.getInstance().updateResource(r, ERPSystem.getInstance().getFactory().create(type, newName, newVal));
	}

	public static void delete(String name) {
		ERPSystem.getInstance().deleteResource(ERPSystem.getInstance().searchByName(name));
	}

	public static void report() {
		ERPSystem.getInstance().reportScreen();
		ERPSystem.getInstance().reportFile();
	}

	public static String getReportText() {
		return ERPSystem.getInstance().reportText();
	}

	public static void load() {
		ERPSystem.getInstance().load();
	}

	public static void save() {
		ERPSystem.getInstance().save();
	}
}
