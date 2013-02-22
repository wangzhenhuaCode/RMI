package server;

import client.ITest;

public class TestImpl implements ITest {

	@Override
	public String hello(String arg) {
		// TODO Auto-generated method stub
		return "Hello"+arg;
	}



}
