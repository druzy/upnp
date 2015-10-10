package druzy.upnp;

import java.util.EventObject;
import java.util.Hashtable;

@SuppressWarnings("serial")
public class WatchServiceEvent extends EventObject {

	private Hashtable<String,Object> values=null;
	
	public WatchServiceEvent(Object source, String argumentName, Object value) {
		this(source,giveHashtable(argumentName,value));
	}
	
	public WatchServiceEvent(Object source, Hashtable<String,Object> values){
		super(source);
		this.values=values;
	}

	private static Hashtable<String,Object> giveHashtable(String argumentName, Object value){
		Hashtable<String,Object> res=new Hashtable<String,Object>();
		res.put(argumentName, value);
		return res;
	}

	public Hashtable<String, Object> getValues() {
		return values;
	}
	
}
