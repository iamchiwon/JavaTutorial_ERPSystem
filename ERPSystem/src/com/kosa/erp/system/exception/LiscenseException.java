package com.kosa.erp.system.exception;

public class LiscenseException extends Exception {

	private static final long serialVersionUID = -6058006435116039510L;

	public String toString() {
		return "제한범위가 넘었습니다. 라이선스 추가 구매해 주세요";
	}
}
