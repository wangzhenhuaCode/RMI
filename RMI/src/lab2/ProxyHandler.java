package lab2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler, Remote {
	private String reference;
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public ProxyHandler(String reference) {
		super();
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

}
