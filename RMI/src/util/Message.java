package util;

import java.io.Serializable;
import java.lang.reflect.Proxy;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Integer BIND_TO_REGISTERY=1;
	public static Integer LOOK_UP=2;
	public static Integer REMOTE_CALL=3;
	public static Integer RETURN_VALUE=4;
	public static Integer RETURN_LOOK_UP=5;
			
	private boolean isRemote;
	private String method;
	private Serializable[] args;
	private Serializable value;
	private String errorMessage;
	private String reference;
	private Integer messageType;
	public boolean isRemote() {
		return isRemote;
	}
	public void setRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Serializable[] getArgs() {
		return args;
	}
	public void setArgs(Serializable[] args) {
		this.args = args;
	}



	

	public Serializable getValue() {
		return value;
	}
	public void setValue(Serializable value) {
		this.value = value;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
