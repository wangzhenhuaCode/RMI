package util;

import java.io.Serializable;
import java.lang.reflect.Proxy;

public class Message implements Serializable {
	private boolean isRemote;
	private String method;
	private Serializable[] args;
	private Serializable returnVal;
	private String errorMessage;
	private String reference;
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


	public Serializable getReturnVal() {
		return returnVal;
	}
	public void setReturnVal(Serializable returnVal) {
		this.returnVal = returnVal;
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
