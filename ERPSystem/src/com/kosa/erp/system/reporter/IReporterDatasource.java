package com.kosa.erp.system.reporter;

public interface IReporterDatasource {
	
	public int dataSize(Class clazz);

	public Object getData(Class clazz, int index);
}
