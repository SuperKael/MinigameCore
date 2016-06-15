package superkael.minigame.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import superkael.minigame.api.*;

public class ZoneHandler {
	
	private static List<MinigameZone> zones = new ArrayList<MinigameZone>();
	private static Hashtable<String, MinigameZone> worldZones = new Hashtable<String, MinigameZone>();
	
	static{
		for(World world : Bukkit.getWorlds()){
			worldZones.put(world.getName(),new MinigameZone(world.getName()));
		}
	}
	
	public static int addZone(MinigameZone zone){
		if(!zones.contains(zone)){
			zones.add(zone);
		}
		return zones.indexOf(zone);
	}
	
	public static boolean delZone(int id){
		return destroyZone(zones.get(id));
	}
	
	public static boolean displayZone(Player player, int id, int time){
		if(zones.size() > id && zones.get(id) != null){
			zones.get(id).display(player, time);
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean displayZone(Player player, String name, int time){
		boolean success = false;
		for(MinigameZone zone : zones){
			if(zone.getName().equals(name)){
				zone.display(player, time);
				success = true;
			}
		}
		return success;
	}
	
	public static boolean displayAllZones(Player player, int time){
		boolean displayedZone = false;
		for(MinigameZone zone : zones){
			zone.display(player, time);
			displayedZone = true;
		}
		return displayedZone;
	}
	
	public static boolean displayZone(Player player, int id){
		if(zones.size() > id && zones.get(id) != null){
			zones.get(id).display(player);
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean displayZone(Player player, String name){
		boolean success = false;
		for(MinigameZone zone : zones){
			if(zone.getName().equals(name)){
				zone.display(player);
				success = true;
			}
		}
		return success;
	}
	
	public static boolean displayAllZones(Player player){
		boolean displayedZone = false;
		for(MinigameZone zone : zones){
			zone.display(player);
			displayedZone = true;
		}
		return displayedZone;
	}
	
	public static boolean undisplayZone(Player player, int id){
		if(zones.size() > id && zones.get(id) != null){
			zones.get(id).undisplay(player);
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean undisplayAllZones(Player player){
		boolean undisplayedZone = false;
		for(MinigameZone zone : zones){
			zone.undisplay(player);
			undisplayedZone = true;
		}
		return undisplayedZone;
	}
	
	public static boolean undisplayZone(Player player, String name){
		boolean success = false;
		for(MinigameZone zone : zones){
			if(zone.getName().equals(name)){
				zone.undisplay(player);
				success = true;
			}
		}
		return success;
	}
	
	public static boolean destroyZone(MinigameZone zone){
		return destroyZone(zone, true);
	}
	
	public static boolean destroyZones(MinigameZone[] zones){
		return destroyZones(zones, true);
	}
	
	public static boolean destroyZoneAtLocation(Location loc){
		return destroyZoneAtLocation(loc, true);
	}
	
	public static boolean destroyZone(MinigameZone zone, boolean save){
		if(zones.contains(zone)){
			zone.destroy();
			zones.remove(zone);
			if(save)MinigameCore.instance.saveZoneConfigFile();
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean destroyZones(MinigameZone[] zones, boolean save){
		boolean success = false;
		for(MinigameZone zone : zones){
			if(destroyZone(zone, save))success = true;
		}
		return success;
	}
	
	public static boolean destroyZoneAtLocation(Location loc, boolean save){
		MinigameZone[] worldZones = getZonesForWorld(loc.getWorld().getName());
		boolean success = false;
		for(MinigameZone zone : worldZones){
			if(zone.getX1() == loc.getBlockX()){
				if(zone.getY1() == loc.getBlockY()){
					if(zone.getZ1() == loc.getBlockZ()){
						if(destroyZone(zone, save))success  = true;
					}
				}
			}
		}
		return success;
	}
	
	public static MinigameZone getZone(int id){
		return zones.get(id);
	}
	
	public static int getZoneID(MinigameZone zone){
		return zones.indexOf(zone);
	}
	
	public static MinigameZone[] getZones(){
		return Arrays.copyOf(zones.toArray(), zones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getZonesForGame(String game){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : zones){
			if(zone.hasGame(game)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getZonesForWorld(String world){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : zones){
			if(zone.getWorld().equals(world)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getZonesContainingPlayer(Player player){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : zones){
			if(zone.containsPlayer(player)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getZonesWithPlayerAndGame(Player player, String game){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : getZonesContainingPlayer(player)){
			if(zone.hasGame(game)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone getZonesForLocation(Location loc){
		MinigameZone[] worldZones = getZonesForWorld(loc.getWorld().getName());
		for(MinigameZone zone : worldZones){
			if(zone.getX1() == loc.getBlockX()){
				if(zone.getY1() == loc.getBlockY()){
					if(zone.getZ1() == loc.getBlockZ()){
						return zone;
					}
				}
			}
		}
		return null;
	}
	
	public static MinigameZone getZoneForName(String name){
		for(MinigameZone zone : zones){
			if(zone.getName().equals(name)){
				return zone;
			}
		}
		return null;
	}
	
	public static MinigameZone[] getWorldZonesForGame(String game){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : worldZones.values()){
			if(zone.hasGame(game)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getWorldZonesContainingPlayer(Player player){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : worldZones.values()){
			if(zone.containsPlayer(player)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getWorldZonesWithPlayerAndGame(Player player, String game){
		ArrayList<MinigameZone> gameZones = new ArrayList<MinigameZone>();
		for(MinigameZone zone : getWorldZonesContainingPlayer(player)){
			if(zone.hasGame(game)){
				gameZones.add(zone);
			}
		}
		return Arrays.copyOf(gameZones.toArray(), gameZones.size(), MinigameZone[].class);
	}
	
	public static MinigameZone[] getLocalZones(Location loc){
		ArrayList<MinigameZone> zonesIn = new ArrayList<MinigameZone>();
		for(MinigameZone zone : zones){
			if(zone.isInZone(loc)){
				zonesIn.add(zone);
			}
		}
		return Arrays.copyOf(zonesIn.toArray(), zonesIn.size(), MinigameZone[].class);
	}
	
	public static MinigameZone getWorldZone(String world){
		if(worldZones.containsKey(world)){
			return worldZones.get(world);
		}else{
			return new MinigameZone(world);
		}
	}
	
	public static int getZoneCount(){
		return zones.size();
	}
	
	public static void onTick(){
		for(MinigameZone zone : zones){
			zone.onTick();
		}
		for(MinigameZone worldZone : worldZones.values()){
			worldZone.onTick();
		}
	}
}
