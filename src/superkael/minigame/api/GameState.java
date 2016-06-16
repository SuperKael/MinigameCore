package superkael.minigame.api;

public enum GameState {
	
	DISABLED        (false,false,false,false,false),
	LOADING         (true ,false,false,false,false),
	UNLOADING       (true ,false,false,false,false),
	STARTING        (true ,true ,false,false,false),
	STOPING         (true ,true ,false,false,false),
	AVAILABLE       (true ,true ,true ,false,false),
	WAITING_PLAYERS (true ,true ,true ,true ,false),
	WAITING_TRIGGER (true ,true ,true ,true ,false),
	WAITING_OTHER   (true ,true ,true ,true ,false),
	RUNNING         (true ,true ,true ,false, true);
	
	private boolean active;
	private boolean ready;
	private boolean available;
	private boolean waiting;
	private boolean running;
	
	GameState(boolean active, boolean ready, boolean available, boolean waiting, boolean running){
		this.active = active;
		this.ready = ready;
		this.available = available;
		this.waiting = waiting;
		this.running = running;
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
