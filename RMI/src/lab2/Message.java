package lab2;

import java.io.Serializable;

public class Message implements Serializable {
	private boolean isRemote;
	private String method;
	private Serializable[] args;
	private String reference;
	private Serializable returnVal;
	private String[] interfaceName;
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
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Serializable getReturnVal() {
		return returnVal;
	}
	public void setReturnVal(Serializable returnVal) {
		this.returnVal = returnVal;
	}
	public String[] getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String[] interfaceName) {
		this.interfaceName = interfaceName;
	}
	
}
