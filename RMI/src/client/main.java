package client;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Registery register=Registery.getRigstery("127.0.0.1",1928,1930);
			ITest test=(ITest) register.lookUp("test");
			System.out.println(test.hello("RMI"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
