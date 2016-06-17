package superkael.minigame.api;

import superkael.minigame.api.interfaces.IStateBasedMinigame;
import superkael.minigame.core.MinigameHandler;

public class MinigameRegistry {
	
	public static boolean registerPlugin(MinigamePlugin plugin, boolean silent){
		return MinigameHandler.registerGame(plugin, silent);
	}
	
	public static IMinigame[] getGames(){
		return MinigameHandler.getGames();
	}
	
	public static String[] getGameIDs(){
		return MinigameHandler.getGameIDs();
	}
	
	public static IMinigame getGameByID(String ID){
		return MinigameHandler.getGameByID(ID);
	}
	
	public static String getGameNameByID(String ID){
		return MinigameHandler.getGameNameByID(ID);
	}
	
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
