package com.example.demo.model;

/*
 * Custom Response Object for user friendly response
 */
final public class ResponseDefObject<T> {
	private int statusCode;
	private String message;
	private Object data;

	public ResponseDefObject() {
		super();
	}

	public ResponseDefObject(int statusCode, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseDefObject [statusCode=");
		builder.append(statusCode);
		builder.append(", message=");
		builder.append(message);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}

}
