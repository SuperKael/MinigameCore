package superkael.minigame.api.interfaces;

import java.util.Hashtable;

import superkael.minigame.api.*;
import superkael.minigame.core.MinigameHandler;

public interface IStateBasedMinigame {
	
	public GameState getState();
	public void setState(GameState state);
	
	public static Hashtable<GameState, Runnable> stateEvents = new Hashtable<GameState, Runnable>();
	
	public static Runnable registerStateEvent(IStateBasedMinigame game, GameState state, Runnable eventHandler){
		return MinigameHandler.registerStateEvent(game, state, eventHandler);
	}
	
	public static boolean hasStateEvent(IStateBasedMinigame game, GameState state){
		return MinigameHandler.hasStateEvent(game, state);
	}
	
	public static Runnable getStateEventHandler(IStateBasedMinigame game, GameState state){
		return MinigameHandler.getStateEventHandler(game, state);
	}
	
}
