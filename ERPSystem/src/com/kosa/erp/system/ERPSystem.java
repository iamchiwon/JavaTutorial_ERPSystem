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
	 * 싱글톤 인스턴스를 반환하는 메소드
	 * 
	 * @return ERPSystem 인스턴스 (싱글톤이다)
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
	 * 리소스를 추가한다.
	 * 
	 * @param r
	 *            Resource 파생 인스턴스가 들어간다. 여기에는 HumanResource 와 MaterialResource가
	 *            있다.
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
	 * 리소스 전체를 리턴한다.
	 * 
	 * @return 리소스의 배열
	 */
	@Deprecated
	public Resource[] readResource() {
		Resource[] r = new Resource[database.size()];
		database.values().toArray(r);
		return r;
	}

	/**
	 * HumanResource 의 이터레이터를 제공한다.
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
	 * MaterialResource 의 이터레이터를 제공한다.
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
	 * 리소스르 업데이트 한다.
	 * 
	 * @param prev
	 *            변경 대상 리소스 인스턴스 (name 으로 검색된 결과를 사용한다.)
	 * @param update
	 *            변경할 리소스 인스턴스
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
	 * 삭제한다.
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
	 * 이름으로 검색한다.
	 * 
	 * @param name
	 * @return
	 */
	public Resource searchByName(String name) {
		return database.get(name);
	}

	// report
	/**
	 * 전체를 출력한다.
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
	 * 리소스 생성 팩토리
	 * 
	 * @return 팩토리 반환
	 */
	public ResourceFactory getFactory() {
		return new ResourceFactory();
	}

	/**
	 * 전체 데이터 사이즈 반환
	 * 
	 * @return 데이터 크기 (Human+Material)
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
