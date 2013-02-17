package util;

import java.util.Hashtable;

public class DataTable extends Hashtable<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 static DataTable instance=null;
	public static DataTable getInstance(){
		if(instance==null){
			instance=new DataTable();
		}
		return instance;
	}
}
