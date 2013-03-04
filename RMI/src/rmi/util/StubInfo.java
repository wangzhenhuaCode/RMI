package rmi.util;

import java.io.Serializable;

/**
 *  Class for storing stub information, which will be stored on the registry.
 *
 */
public class StubInfo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serverHost;
	private Integer port;
	private String reference;
	private String[] interfaces;

	

	public StubInfo(String serverHost, Integer port, String reference,
			String[] interfaces) {
		super();
		this.serverHost = serverHost;
		this.port = port;
		this.reference = reference;
		this.interfaces = interfaces;
	}
	public String[] getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(String[] interfaces) {
		this.interfaces = interfaces;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}

	

}
