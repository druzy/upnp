package druzy.upnp;

import java.util.EventListener;

public interface WatchServiceListener extends EventListener {
	public void update(WatchServiceEvent wse);
}
