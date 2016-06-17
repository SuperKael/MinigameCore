package superkael.minigame.api;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import superkael.minigame.core.ZoneHandler;

public class ZoneHelper {

	public static int addZone(MinigameZone zone){
		return ZoneHandler.addZone(zone);
	}

	public static boolean delZone(int id){
		return ZoneHandler.delZone(id);
	}

	public static boolean displayZone(Player player, int id, int time){
		return ZoneHandler.displayZone(player, id, time);
	}
	
	public static boolean displayZone(Player player, String name, int time){
		return ZoneHandler.displayZone(player, name, time);
	}
	
	public static boolean displayAllZones(Player player, int time){
		return ZoneHandler.displayAllZones(player, time);
	}
	
	public static boolean displayZone(Player player, int id){
		return ZoneHandler.displayZone(player, id);
	}
	
	public static boolean displayZone(Player player, String name){
		return ZoneHandler.displayZone(player, name);
	}
	
	public static boolean displayAllZones(Player player){
		return ZoneHandler.displayAllZones(player);
	}
	
	public static boolean undisplayZone(Player player, int id){
		return ZoneHandler.undisplayZone(player, id);
	}
	
	public static boolean undisplayAllZones(Player player){
		return ZoneHandler.undisplayAllZones(player);
	}
	
	public static boolean undisplayZone(Player player, String name){
		return ZoneHandler.undisplayZone(player, name);
	}

	public static boolean destroyZone(MinigameZone zone){
		return ZoneHandler.destroyZone(zone);
	}
	
	public static boolean destroyZones(MinigameZone[] zones){
		return ZoneHandler.destroyZones(zones);
	}
	
	public static boolean destroyZoneAtLocation(Location loc){
		return ZoneHandler.destroyZoneAtLocation(loc);
	}
	
	public static boolean destroyZone(MinigameZone zone, boolean save){
		return ZoneHandler.destroyZone(zone, save);
	}
	
	public static boolean destroyZones(MinigameZone[] zones, boolean save){
		return ZoneHandler.destroyZones(zones, save);
	}
	
	public static boolean destroyZoneAtLocation(Location loc, boolean save){
		return ZoneHandler.destroyZoneAtLocation(loc, save);
	}

	public static MinigameZone getZone(int id){
		return ZoneHandler.getZone(id);
	}
	
	public static int getZoneID(MinigameZone zone){
		return ZoneHandler.getZoneID(zone);
	}

	public static MinigameZone[] getZones(){
		return ZoneHandler.getZones();
	}

	public static MinigameZone[] getZonesForGame(String game){
		return ZoneHandler.getZonesForGame(game);
	}

	public static MinigameZone[] getZonesForWorld(String world){
		return ZoneHandler.getZonesForWorld(world);
	}
	
	public static MinigameZone[] getZonesContainingPlayer(Player player){
		return ZoneHandler.getZonesContainingPlayer(player);
	}
	
	public static MinigameZone[] getZonesWithPlayerAndGame(Player player, String game){
		return ZoneHandler.getZonesWithPlayerAndGame(player, game);
	}
	
	public static MinigameZone getZonesForLocation(Location loc){
		return ZoneHandler.getZonesForLocation(loc);
	}
	
	public static MinigameZone getZoneForName(String name){
		return ZoneHandler.getZoneForName(name);
	}

	public static MinigameZone[] getWorldZonesForGame(String game){
		return ZoneHandler.getWorldZonesForGame(game);
	}
	
	public static MinigameZone[] getWorldZonesContainingPlayer(Player player){
		return ZoneHandler.getWorldZonesContainingPlayer(player);
	}
	
	public static MinigameZone[] getWorldZonesWithPlayerAndGame(Player player, String game){
		return ZoneHandler.getWorldZonesWithPlayerAndGame(player, game);
	}

	public static MinigameZone[] getLocalZones(Location loc){
		return ZoneHandler.getLocalZones(loc);
	}

	public static MinigameZone getWorldZone(String world, boolean createIfNotPresent){
		return ZoneHandler.getWorldZone(world, createIfNotPresent);
	}

	public static int getZoneCount(){
		return ZoneHandler.getZoneCount();
	}
	
	public static Player[] getPlayersInGame(String game){
		MinigameZone[] zones = getAllZonesForGame(game);
		ArrayList<Player> players = new ArrayList<Player>();
		for(MinigameZone zone : zones){
			Player[] playersInZone = zone.getContainedPlayers();
			for(Player player : playersInZone){
				if(!players.contains(player)){
					players.add(player);
				}
			}
		}
		return Arrays.copyOf(players.toArray(), players.size(), Player[].class);
	}
	
	public static MinigameZone[] getAllZonesForGame(String game){
		MinigameZone[] zones = getZonesForGame(game);
		MinigameZone[] worldZones = getWorldZonesForGame(game);
		MinigameZone[] allZones = new MinigameZone[zones.length + worldZones.length];
		for(int i = 0;i < zones.length;i++){
			allZones[i] = zones[i];
		}
		for(int i = 0;i < worldZones.length;i++){
			allZones[i + zones.length] = worldZones[i];
		}
		return allZones;
	}
}
