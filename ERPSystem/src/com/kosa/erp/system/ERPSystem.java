package com.kosa.erp.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kosa.erp.system.data.HumanResource;
import com.kosa.erp.system.data.MaterialResource;
import com.kosa.erp.system.data.Resource;
import com.kosa.erp.system.exception.LiscenseException;
import com.kosa.erp.system.reporter.BufferReporter;
import com.kosa.erp.system.reporter.ConsoleReporter;
import com.kosa.erp.system.reporter.FileReporter;
import com.kosa.erp.system.reporter.IReporterDatasource;
import com.kosa.erp.system.reporter.Reporter;

public class ERPSystem implements IReporterDatasource {

	/*
	 * Wrapper class
	 * 
	 * boolean -> Boolean char -> Character int -> Integer byte -> Byte short ->
	 * Short long -> Long float -> Float double -> Double
	 */

	// database
	private Map<String, Resource> database;
	private List<Resource> indexHuman, indexMaterial;

	// Singletone
	private static ERPSystem globalSystem = null;

	/**
	 * �̱��� �ν��Ͻ��� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @return ERPSystem �ν��Ͻ� (�̱����̴�)
	 */
	public static ERPSystem getInstance() {
		if (globalSystem == null) {
			globalSystem = new ERPSystem();
		}
		return globalSystem;
	}

	// constructor
	private ERPSystem() {
		database = new HashMap<String, Resource>();
		indexHuman = new ArrayList<Resource>();
		indexMaterial = new ArrayList<Resource>();
	}

	// ------------ CRUD

	// create
	/**
	 * ���ҽ��� �߰��Ѵ�.
	 * 
	 * @param r
	 *            Resource �Ļ� �ν��Ͻ��� ����. ���⿡�� HumanResource �� MaterialResource��
	 *            �ִ�.
	 */
	public void addResource(Resource r) throws Exception {
		int size = database.values().size();
		if (size >= Config.SystemCapacity) {
			throw new LiscenseException();
		}

		database.put(r.getName(), r);
		if (r instanceof HumanResource)
			indexHuman.add(r);
		if (r instanceof MaterialResource)
			indexMaterial.add(r);
	}

	// read
	/**
	 * ���ҽ� ��ü�� �����Ѵ�.
	 * 
	 * @return ���ҽ��� �迭
	 */
	@Deprecated
	public Resource[] readResource() {
		Resource[] r = new Resource[database.size()];
		database.values().toArray(r);
		return r;
	}

	/**
	 * HumanResource �� ���ͷ����͸� �����Ѵ�.
	 * @return
	 */
	public Iterator<Resource> iteratorHuman() {
		return new Iterator<Resource>() {
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < indexHuman.size();
			}

			@Override
			public Resource next() {
				return indexHuman.get(index++);
			}
		};
	}

	/**
	 * MaterialResource �� ���ͷ����͸� �����Ѵ�.
	 * @return
	 */
	public Iterator<Resource> iteratorMaterial() {
		return new Iterator<Resource>() {
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < indexMaterial.size();
			}

			@Override
			public Resource next() {
				return indexMaterial.get(index++);
			}
		};
	}

	// update
	/**
	 * ���ҽ��� ������Ʈ �Ѵ�.
	 * 
	 * @param prev
	 *            ���� ��� ���ҽ� �ν��Ͻ� (name ���� �˻��� ����� ����Ѵ�.)
	 * @param update
	 *            ������ ���ҽ� �ν��Ͻ�
	 */
	public void updateResource(Resource prev, Resource update) {
		database.remove(prev.getName());
		if (prev instanceof HumanResource)
			indexHuman.remove(prev);
		if (prev instanceof MaterialResource)
			indexMaterial.remove(prev);
		database.put(update.getName(), update);
		if (prev instanceof HumanResource)
			indexHuman.add(update);
		if (prev instanceof MaterialResource)
			indexMaterial.add(update);
	}

	// delete
	/**
	 * �����Ѵ�.
	 * 
	 * @param resource
	 */
	public void deleteResource(Resource resource) {
		database.remove(resource.getName());
		if (resource instanceof HumanResource)
			indexHuman.remove(resource);
		if (resource instanceof MaterialResource)
			indexMaterial.remove(resource);
	}

	// search
	/**
	 * �̸����� �˻��Ѵ�.
	 * 
	 * @param name
	 * @return
	 */
	public Resource searchByName(String name) {
		return database.get(name);
	}

	// report
	/**
	 * ��ü�� ����Ѵ�.
	 */
	public void reportScreen() {
		Reporter reporter = new ConsoleReporter();
		reporter.setDatasource(this);
		reporter.printAll();
	}

	public void reportFile() {
		Reporter reporter = new FileReporter();
		reporter.setDatasource(this);
		reporter.printAll();
	}

	public String reportText() {
		Reporter reporter = new BufferReporter();
		reporter.setDatasource(this);
		reporter.printAll();
		return ((BufferReporter) reporter).getText();
	}

	@Override
	public int dataSize(Class clazz) {
		if (HumanResource.class.equals(clazz)) {
			return indexHuman.size();
		}

		if (MaterialResource.class.equals(clazz)) {
			return indexMaterial.size();
		}

		return 0;
	}

	@Override
	public Object getData(Class clazz, int index) {
		if (HumanResource.class.equals(clazz)) {
			return indexHuman.get(index);
		}

		if (MaterialResource.class.equals(clazz)) {
			return indexMaterial.get(index);
		}

		return null;
	}

	// ------------- UTILS

	/**
	 * ���ҽ� ���� ���丮
	 * 
	 * @return ���丮 ��ȯ
	 */
	public ResourceFactory getFactory() {
		return new ResourceFactory();
	}

	/**
	 * ��ü ������ ������ ��ȯ
	 * 
	 * @return ������ ũ�� (Human+Material)
	 */
	public int size() {
		return database.size();
	}

	// ------------- Inner Method

	private void log(String log, boolean instantMode) {
		if (instantMode) {
			System.out.println(log);
		}
	}

	private void log(String log) {
		log(log, Config.SYSTEM_DEBUG_MODE);
	}

	public void load() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(new File("backup.dat")));
			database = (Map<String, Resource>) in.readObject();

			// re-indexing
			Iterator<Resource> i = database.values().iterator();
			while (i.hasNext()) {
				Resource r = i.next();
				if (r instanceof HumanResource)
					indexHuman.add(r);
				else if (r instanceof MaterialResource)
					indexMaterial.add(r);
			}

		} catch (Exception e) {
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	public void save() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(new File("backup.dat")));
			out.writeObject(database);
			out.close();
		} catch (Exception e) {
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

}
