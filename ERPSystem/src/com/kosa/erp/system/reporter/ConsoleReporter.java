package com.kosa.erp.system.reporter;

import java.io.PrintStream;

public class ConsoleReporter extends Reporter {

	@Override
	protected void openWriteStream() {
	}

	@Override
	protected PrintStream getWriteMethod() {
		return System.out;
	}

	@Override
	protected void closeWriteStream() {
	}

}
