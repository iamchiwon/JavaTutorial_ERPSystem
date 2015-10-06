package com.kosa.erp.system.reporter;

import java.io.PrintStream;
import java.text.DecimalFormat;

import com.kosa.erp.system.data.HumanResource;
import com.kosa.erp.system.data.MaterialResource;
import com.kosa.erp.system.data.Resource;

public abstract class Reporter {

	private IReporterDatasource datasource;

	protected abstract void openWriteStream();

	protected abstract PrintStream getWriteMethod();

	protected abstract void closeWriteStream();

	public void setDatasource(IReporterDatasource datasource) {
		this.datasource = datasource;
	}

	final String format = "  %-7s\t%15s";

	public void printAll() {
		openWriteStream();

		printHuman();
		printMaterial();

		closeWriteStream();
	}

	public void printHuman() {
		PrintStream out = getWriteMethod();

		DecimalFormat formatter = new DecimalFormat("###,###,###.0");

		printHead("인 사 관 리");

		double total = 0;
		int size = datasource.dataSize(HumanResource.class);
		for (int i = 0; i < size; i++) {
			Resource o = (Resource) datasource.getData(HumanResource.class, i);
			out.println(String.format(format, o.getName(), formatter.format(o.getValue())));
			total += o.getValue();
		}

		printFooter("인건비", total);
	}

	public void printMaterial() {
		PrintStream out = getWriteMethod();

		DecimalFormat formatter = new DecimalFormat("###,###,###.0");

		printHead("재 물 관 리");

		double total = 0;
		int size = datasource.dataSize(MaterialResource.class);
		for (int i = 0; i < size; i++) {
			Resource o = (Resource) datasource.getData(MaterialResource.class, i);
			out.println(String.format(format, o.getName(), formatter.format(o.getValue())));
			total += o.getValue();
		}

		printFooter("유지비 계", total);
	}

	private void printHead(String title) {
		PrintStream out = getWriteMethod();

		out.println("=============================");
		out.println("           " + title);
		out.println("-----------------------------");
	}

	private void printFooter(String title, double total) {
		PrintStream out = getWriteMethod();

		DecimalFormat formatter = new DecimalFormat("###,###,###.0");

		out.println("-----------------------------");
		out.println(String.format("    %s\t%15s", title, formatter.format(total)));
		out.println("-----------------------------");
	}
}
