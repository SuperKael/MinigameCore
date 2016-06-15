package superkael.minigame.api;

import superkael.minigame.core.MinigameHandler;

public class PluginRegistry {
	
	public static boolean registerPlugin(MinigamePlugin plugin, boolean silent){
		return MinigameHandler.registerGame(plugin, silent);
	}
	
	public static IMinigame getPlugin(String ID){
		return MinigameHandler.getGameByID(ID);
	}
	
}
