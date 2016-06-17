package superkael.minigame.api;

public enum GameState {
	
	DISABLED        (false,false,false,false,false,"Disabled"),
	LOADING         (true ,false,false,false,false,"Loading"),
	UNLOADING       (true ,false,false,false,false,"Unloading"),
	STARTING        (true ,true ,false,false,false,"Starting"),
	STOPING         (true ,true ,false,false,false,"Stoping"),
	AVAILABLE       (true ,true ,true ,false,false,"Available"),
	WAITING_PLAYERS (true ,true ,true ,true ,false,"Waiting-players"),
	WAITING_TRIGGER (true ,true ,true ,true ,false,"Waiting-trigger"),
	WAITING_OTHER   (true ,true ,true ,true ,false,"Waiting-other"),
	RUNNING         (true ,true ,true ,false, true,"Running");
	
	private String name;
	
	private boolean active;
	private boolean ready;
	private boolean available;
	private boolean waiting;
	private boolean running;
	
	GameState(boolean active, boolean ready, boolean available, boolean waiting, boolean running, String name){
		this.name = name;
		this.active = active;
		this.ready = ready;
		this.available = available;
		this.waiting = waiting;
		this.running = running;
	}
	
	public String toString(){
		return name;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public boolean isWaiting(){
		return waiting;
	}
	
	public boolean isRunning(){
		return running;
	}
}
