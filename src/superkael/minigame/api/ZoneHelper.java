package superkael.minigame.api;

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

	public static void displayZone(Player player, int id){
		ZoneHandler.displayZone(player, id);
	}
	
	public static void displayZone(Player player, int id, int time){
		ZoneHandler.displayZone(player, id, time);
	}

	public static void displayAllZones(Player player){
		ZoneHandler.displayAllZones(player);
	}
	
	public static void displayAllZones(Player player, int time){
		ZoneHandler.displayAllZones(player, time);
	}

	public static void undisplayZone(Player player, int id){
		ZoneHandler.undisplayZone(player, id);
	}

	public static void undisplayAllZones(Player player){
		ZoneHandler.undisplayAllZones(player);
	}

	public static boolean destroyZone(MinigameZone zone){
		return ZoneHandler.destroyZone(zone);
	}

	public static boolean destroyZones(MinigameZone[] zones){
		return ZoneHandler.destroyZones(zones);
	}

	public static MinigameZone getZone(int id){
		return ZoneHandler.getZone(id);
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

	public static MinigameZone[] getWorldZonesForGame(String game){
		return ZoneHandler.getWorldZonesForGame(game);
	}

	public static MinigameZone[] getLocalZones(Location loc){
		return ZoneHandler.getLocalZones(loc);
	}

	public static MinigameZone getWorldZone(String world){
		return ZoneHandler.getWorldZone(world);
	}

	public static int getZoneCount(){
		return ZoneHandler.getZoneCount();
	}	
}
