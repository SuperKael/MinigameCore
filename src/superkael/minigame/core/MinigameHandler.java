package superkael.minigame.core;

import java.util.Hashtable;
import org.bukkit.entity.Player;

import superkael.minigame.api.*;
import superkael.minigame.api.interfaces.IStateBasedMinigame;

public class MinigameHandler {
	
	private static Hashtable<String, IMinigame> minigames = new Hashtable<String, IMinigame>();
	private static Hashtable<IStateBasedMinigame, Hashtable<GameState, Runnable>> stateEvents = new Hashtable<IStateBasedMinigame, Hashtable<GameState, Runnable>>();
	
	public static boolean registerGame(IMinigame plugin, boolean silent){
		if(plugin instanceof MinigamePlugin){
			if(minigames.containsKey(plugin.getID().toLowerCase())){
				if(!silent)System.out.println("[MinigameCore] Failed to load plugin " + plugin.getGameName() + ": Plugin already loaded!");
				return false;
			}else{
				minigames.put(plugin.getID().toLowerCase(), plugin);
				if(!silent)System.out.println("[MinigameCore] Successfully loaded plugin " + plugin.getGameName() + "!");
				return true;
			}
		}else if(plugin instanceof MinigameModule){
			if(minigames.containsKey(plugin.getID().toLowerCase())){
				if(!silent)System.out.println("[MinigameCore] Failed to load game module " + plugin.getGameName() + ": Plugin already loaded!");
				return false;
			}else{
				minigames.put(plugin.getID().toLowerCase(), plugin);
				if(!silent)System.out.println("[MinigameCore] Successfully loaded game module " + plugin.getGameName() + "!");
				return true;
			}
		}else{
			if(minigames.containsKey(plugin.getID().toLowerCase())){
				if(!silent)System.out.println("[MinigameCore] Failed to load game " + plugin.getGameName() + ": Plugin already loaded!");
				return false;
			}else{
				minigames.put(plugin.getID().toLowerCase(), plugin);
				if(!silent)System.out.println("[MinigameCore] Successfully loaded game " + plugin.getGameName() + "!");
				return true;
			}
		}
	}
	
	public static boolean unregisterGame(IMinigame plugin, boolean silent){
		if(plugin instanceof MinigamePlugin){
			if(minigames.containsKey(plugin.getID().toLowerCase())){
				minigames.remove(plugin.getID().toLowerCase());
				if(!silent)System.out.println("[MinigameCore] Successfully unloaded plugin " + plugin.getGameName() + "!");
				return true;
			}else{
				if(!silent)System.out.println("[MinigameCore] Failed to unload plugin " + plugin.getGameName() + ": Plugin not loaded!");
				return false;
			}
		}else if(plugin instanceof MinigameModule){
			if(minigames.containsKey(plugin.getID().toLowerCase())){
				minigames.remove(plugin.getID().toLowerCase());
				if(!silent)System.out.println("[MinigameCore] Successfully unloaded game module " + plugin.getGameName() + "!");
				return true;
			}else{
				if(!silent)System.out.println("[MinigameCore] Failed to unload game module " + plugin.getGameName() + ": Plugin not loaded!");
				return false;
			}
		}else{
			if(minigames.containsKey(plugin.getID().toLowerCase())){
				minigames.remove(plugin.getID().toLowerCase());
				if(!silent)System.out.println("[MinigameCore] Successfully unloaded game " + plugin.getGameName() + "!");
				return true;
			}else{
				if(!silent)System.out.println("[MinigameCore] Failed to unload game " + plugin.getGameName() + ": Plugin not loaded!");
				return false;
			}
		}
	}
	
	public static void loadGames(){
		for(IMinigame game : minigames.values()){
			if(game instanceof MinigameModule){
				((MinigameModule)game).loadGame();
			}
		}
	}
	
	public static void unloadGames(){
		for(IMinigame game : minigames.values()){
			if(game instanceof MinigameModule){
				((MinigameModule)game).unloadGame();
			}
		}
	}
	
	public static void verifyDependencies(){
		for(IMinigame plugin : minigames.values()){
			String[] dependencies = plugin.dependencies();
			boolean hasDependencies = true;
			for(String dependency : dependencies){
				if(!isGameRegistered(dependency)){
					hasDependencies = false;
					System.out.println("[MinigameCore] Warning: minigame plugin " + plugin.getGameName() + " is missing required dependency: " + dependency + "!");
				}
			}
			if(!hasDependencies){
				System.out.println("[MinigameCore] Minigame plugin " + plugin.getGameName() + " is being unloaded because it is missing required dependencies...");
				unregisterGame(plugin, false);
			}
		}
	}
	
	public static IMinigame getGameByID(String ID){
		return minigames.get(ID.toLowerCase());
	}
	
	public static boolean isGameRegistered(String ID){
		return minigames.containsKey(ID.toLowerCase());
	}
	
	public static Runnable registerStateEvent(IStateBasedMinigame game, GameState state, Runnable eventHandler){
		Hashtable<GameState,Runnable> gameTable;
		if(stateEvents.contains(game)){
			gameTable = stateEvents.get(game);
		}else{
			gameTable = new Hashtable<GameState,Runnable>();
		}
		Runnable runnable = gameTable.put(state, eventHandler);
		stateEvents.put(game, gameTable);
		return runnable;
	}
	
	public static boolean hasStateEvent(IStateBasedMinigame game, GameState state){
		if(stateEvents.contains(game)){
			return stateEvents.get(game).contains(state);
		}else{
			return false;
		}
	}
	
	public static Runnable getStateEventHandler(IStateBasedMinigame game, GameState state){
		if(stateEvents.contains(game)){
			return stateEvents.get(game).get(state);
		}else{
			return null;
		}
	}
	
	public static boolean runStateEvent(IStateBasedMinigame game){
		Runnable eventHandler = stateEvents.get(game).get(game.getState());
		if(eventHandler != null){
			eventHandler.run();
			return true;
		}else{
			return false;
		}
	}
	
	public static void onTick(){
		for(IMinigame plugin : minigames.values()){
			if(plugin instanceof IStateBasedMinigame){
				if(!((IStateBasedMinigame)plugin).getState().isReady()){
					continue;
				}else{
					plugin.onTick();
					runStateEvent((IStateBasedMinigame)plugin);
				}
			}else{
				plugin.onTick();
			}
			MinigameZone[] zones = ZoneHandler.getZonesForGame(plugin.getID());
			boolean hasTickedActive = false;
			if(zones.length > 0){
				for(MinigameZone zone : zones){
					Player[] players = zone.getContainedPlayers();
					if(players.length > 0){
						if(!hasTickedActive){
							plugin.onActiveTick();
							hasTickedActive = true;
						}
						plugin.onZoneTick(zone);
						for(Player player : players){
							plugin.onPlayerTick(zone, player);
							plugin.onPlayerZoneTick(zone, player);
						}
					}
				}
			}
			MinigameZone[] worldZones = ZoneHandler.getWorldZonesForGame(plugin.getID());
			if(worldZones.length > 0){
				for(MinigameZone zone : worldZones){
					Player[] players = zone.getContainedPlayers();
					if(players.length > 0){
						if(!hasTickedActive){
							plugin.onActiveTick();
							hasTickedActive = true;
						}
						plugin.onWorldTick(zone);
						for(Player player : players){
							plugin.onPlayerTick(zone, player);
							plugin.onPlayerWorldTick(zone, player);
						}
					}
				}
			}
		}
	}
}
