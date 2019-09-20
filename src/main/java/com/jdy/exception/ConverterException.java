package com.jdy.exception;

public class ConverterException extends Exception {

	private static final long serialVersionUID = 5401800455810428235L;

	public ConverterException() {
		super();
	}

	public ConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConverterException(String message, Object... objects) {
		super(String.format(message, objects));
	}

	public ConverterException(Throwable cause) {
		super(cause);
	}
}
