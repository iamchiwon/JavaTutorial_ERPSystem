package com.kosa.erp.system.reporter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BufferReporter extends Reporter {

	ByteArrayOutputStream baos;
	PrintStream out;
	String text;

	@Override
	protected void openWriteStream() {
		baos = new ByteArrayOutputStream();
		out = new PrintStream(baos);
	}

	@Override
	protected PrintStream getWriteMethod() {
		return out;
	}

	@Override
	protected void closeWriteStream() {
		try {
			byte[] data = baos.toByteArray();
			text = new String(data, "EUC-KR");

			out.close();
			out = null;
			baos = null;
		} catch (Exception e) {
		}
	}

	public String getText() {
		return text;
	}

}
