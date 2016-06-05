package superkael.minigame.api;

import superkael.minigame.core.MinigameHandler;

public class PluginRegistry {
	
	public static boolean registerPlugin(MinigamePlugin plugin, boolean silent){
		return MinigameHandler.registerPlugin(plugin, silent);
	}
	
	public static MinigamePlugin getPlugin(String ID){
		return MinigameHandler.getGameByID(ID);
	}
	
}
