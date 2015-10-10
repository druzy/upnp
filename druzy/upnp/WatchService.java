package druzy.upnp;

import java.util.Hashtable;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.RemoteService;

public class WatchService extends Thread{
	private ControlPoint controlPoint=null;
	private ActionInvocation<RemoteService> invocation=null;
	private String[] argumentsName=null;
	private int delay=0;
	private WatchServiceListener listener=null;
	private Thread t=null;
	
	
	public WatchService(ControlPoint controlPoint, ActionInvocation<RemoteService> invocation, String argumentName, int delay, WatchServiceListener listener){
		this(controlPoint,invocation,new String[]{argumentName},delay,listener);
	}
	
	public WatchService(ControlPoint controlPoint, ActionInvocation<RemoteService> invocation, String argumentName, WatchServiceListener watchServiceListener){
		this(controlPoint, invocation, argumentName, 1000, watchServiceListener);
	}
	
	public WatchService(ControlPoint controlPoint, ActionInvocation<RemoteService> invocation, String[] argumentsName, WatchServiceListener watchServiceListener){
		this(controlPoint, invocation, argumentsName, 1000, watchServiceListener);
	}
	
	public WatchService(ControlPoint controlPoint, ActionInvocation<RemoteService> invocation, String[] argumentsName, int delay, WatchServiceListener listener){
		super();
		this.controlPoint=controlPoint;
		this.invocation=invocation;
		this.argumentsName=argumentsName;
		this.delay=delay;
		this.listener=listener;
	}

	public void run(){
		while (!isInterrupted()){
			new Thread(){
				public void run(){
					new ActionCallback.Default(invocation, controlPoint).run();
					Hashtable<String,Object> table=new Hashtable<String,Object>();
					for (int i=0;i<argumentsName.length;i++) table.put(argumentsName[i], invocation.getOutput(argumentsName[i]).getValue());
					listener.update(new WatchServiceEvent(WatchService.this,table));
				}
			}.start();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}
	
}
