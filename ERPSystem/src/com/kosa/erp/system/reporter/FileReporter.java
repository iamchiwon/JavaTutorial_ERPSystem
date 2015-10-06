package com.kosa.erp.system.reporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileReporter extends Reporter {

	PrintStream out;
	
	@Override
	protected void openWriteStream() {
		try {
			out= new PrintStream(new FileOutputStream(new File("resources.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected PrintStream getWriteMethod() {
		return out;
	}

	@Override
	protected void closeWriteStream() {
		out.close();
	}

}
