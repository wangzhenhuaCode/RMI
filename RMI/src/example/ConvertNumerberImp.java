package example;

import rmi.util.RemoteException;

public class ConvertNumerberImp implements IConvertNumber {

	@Override
	public Integer getNum(String str) throws RemoteException {
		// TODO Auto-generated method stub
		if(str==null) throw new RemoteException("Null String");
		char[] array=str.toCharArray();
		int num=0;
		for(int i=0;i<array.length;i++){
			num*=10;
			num+=Integer.valueOf(String.valueOf(array[i]));
		}
		
		return num;
	}

}
