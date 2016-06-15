package superkael.minigame.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import superkael.minigame.core.*;

public class MinigameZone {
	
	private int x1, y1, z1, x2, y2, z2;
	private boolean global;
	private String world;
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<String> minigames = new ArrayList<String>();
	private Hashtable<String, String> settings = new Hashtable<String, String>();
	
	public MinigameZone(int x1, int y1, int z1, int x2, int y2, int z2, String world){
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.z1 = Math.min(z1, z2);
		this.x2 = Math.max(x1, x2);
		this.y2 = Math.max(y1, y2);
		this.z2 = Math.max(z1, z2);
		this.world = world;
		this.name = "";
	}
	
	public MinigameZone(int x1, int y1, int z1, int x2, int y2, int z2, String world, String name){
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.z1 = Math.min(z1, z2);
		this.x2 = Math.max(x1, x2);
		this.y2 = Math.max(y1, y2);
		this.z2 = Math.max(z1, z2);
		this.world = world;
		this.name = name;
	}
	
	public MinigameZone(String world){
		this.global = true;
		this.world = world;
	}
	
	public boolean addGame(String gameID){
		if(minigames.contains(gameID.toLowerCase())){
			return false;
		}else{
			minigames.add(gameID.toLowerCase());
			String[] dependencies = MinigameHandler.getGameByID(gameID).dependencies();
			for(String dependency : dependencies){
				minigames.add(dependency.toLowerCase());
			}
			Collections.sort(minigames);
			MinigameCore.instance.saveZoneConfigFile();
			return true;
		}
	}
	
	public boolean delGame(String gameID){
		if(minigames.contains(gameID.toLowerCase())){
			minigames.remove(gameID.toLowerCase());
			MinigameCore.instance.saveZoneConfigFile();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean addSetting(String settingName, String settingValue){
		settings.put(settingName.toLowerCase(), settingValue);
		Arrays.sort(settings.keySet().toArray());
		MinigameCore.instance.saveZoneConfigFile();
		return true;
	}
	
	public boolean delSetting(String settingName){
		if(settings.containsKey(settingName.toLowerCase())){
			settings.remove(settingName.toLowerCase());
			MinigameCore.instance.saveZoneConfigFile();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasName(){
		if(global || name.equals("")){
			return false;
		}else{
			return true;
		}
	}
	
	public String getName(){
		if(!global){
			if(name.equals("")){
				int ID = ZoneHandler.getZoneID(this);
				if(ID == -1){
					return null;
				}else{
					return "Zone" + ID;
				}
			}else{
				return name;
			}
		}else{
			return world;
		}
	}
	
	public void setName(String name){
		if(!global){
			this.name = name;
		}
		MinigameCore.instance.saveZoneConfigFile();
	}
	
	public List<String> getGames(){
		return Collections.unmodifiableList(minigames);
	}
	
	public Map<String, String> getSettings(){
		return Collections.unmodifiableMap(settings);
	}
	
	public List<String> getSettingsAsStrings(){
		ArrayList<String> settingStrings = new ArrayList<String>();
		for(int i = 0;i < settings.size();i++){
			settingStrings.add(settings.keySet().toArray()[i] + ": " + settings.values().toArray()[i]);
		}
		return settingStrings;
	}
	
	public String getSetting(String setting){
		return settings.get(setting.toLowerCase());
	}
	
	public boolean hasGame(String game){
		return minigames.contains(game.toLowerCase());
	}
	
	public boolean hasSetting(String setting){
		return settings.containsKey(setting.toLowerCase());
	}
	
	public void display(Player player, int time){
		if(global)return;
		display(player);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinigameCore.instance, new Runnable(){
			public void run(){
				undisplay(player);
			}
		},6000);
	}
	
	@SuppressWarnings("deprecation")
	public void display(Player player){
		if(global)return;
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y1,z1),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y1,z1),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y2,z1),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y1,z2),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y2,z1),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y2,z2),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y1,z2),Material.GLOWSTONE,(byte)0);
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y2,z2),Material.GLOWSTONE,(byte)0);
	}
	
	@SuppressWarnings("deprecation")
	public void undisplay(Player player){
		if(global)return;
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y1,z1),Bukkit.getWorld(world).getBlockAt(x1,y1,z1).getType(),Bukkit.getWorld(world).getBlockAt(x1,y1,z1).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y1,z1),Bukkit.getWorld(world).getBlockAt(x2,y1,z1).getType(),Bukkit.getWorld(world).getBlockAt(x2,y1,z1).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y2,z1),Bukkit.getWorld(world).getBlockAt(x1,y2,z1).getType(),Bukkit.getWorld(world).getBlockAt(x1,y2,z1).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y1,z2),Bukkit.getWorld(world).getBlockAt(x1,y1,z2).getType(),Bukkit.getWorld(world).getBlockAt(x1,y1,z2).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y2,z1),Bukkit.getWorld(world).getBlockAt(x2,y2,z1).getType(),Bukkit.getWorld(world).getBlockAt(x2,y2,z1).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x1,y2,z2),Bukkit.getWorld(world).getBlockAt(x1,y2,z2).getType(),Bukkit.getWorld(world).getBlockAt(x1,y2,z2).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y1,z2),Bukkit.getWorld(world).getBlockAt(x2,y1,z2).getType(),Bukkit.getWorld(world).getBlockAt(x2,y1,z2).getData());
		player.sendBlockChange(new Location(Bukkit.getWorld(world),x2,y2,z2),Bukkit.getWorld(world).getBlockAt(x2,y2,z2).getType(),Bukkit.getWorld(world).getBlockAt(x2,y2,z2).getData());
	}
	
	public void onTick(){
		@SuppressWarnings("unchecked")
		ArrayList<Player> oldPlayers = (ArrayList<Player>)players.clone();
 		updateContainedPlayers();
 		if(!players.equals(oldPlayers)){
 			ArrayList<Player> playersEntered = new ArrayList<Player>();
 			ArrayList<Player> playersExited = new ArrayList<Player>();
 			for(Player player : players){
 				if(!oldPlayers.contains(player)){
 					playersEntered.add(player);
 				}
 			}
 			for(Player player : oldPlayers){
 				if(!players.contains(player)){
 					playersExited.add(player);
 				}
 			}
 			if(playersEntered.size() > 0){
 				for(String game : minigames){
 					IMinigame plugin = MinigameHandler.getGameByID(game);
 					for(Player player : playersEntered){
 						plugin.onPlayerEnterGame(this, player);
 						if(global){
 							plugin.onPlayerEnterWorld(this, player);
 						}else{
 							plugin.onPlayerEnterZone(this, player);
 						}
 					}
 				}
 			}
 			if(playersExited.size() > 0){
 				for(String game : minigames){
 					IMinigame plugin = MinigameHandler.getGameByID(game);
 					for(Player player : playersExited){
 						plugin.onPlayerExitGame(this, player);
 						if(global){
 							plugin.onPlayerExitWorld(this, player);
 						}else{
 							plugin.onPlayerExitZone(this, player);
 						}
 					}
 				}
 			}
 		}
	}
	
	public boolean isInZone(Location loc){
		if(!loc.getWorld().getName().equals(world))return false;
		if(global)return true;
		if((x1 >= loc.getX()) || (loc.getX() >= x2+1))return false;
		if((y1 >= loc.getY()) || (loc.getY() >= y2+1))return false;
		if((z1 >= loc.getZ()) || (loc.getZ() >= z2+1))return false;
		return true;
	}
	
	public boolean containsPlayer(Player player){
		return players.contains(player);
	}
	
	public Player[] getContainedPlayers(){
		return Arrays.copyOf(players.toArray(), players.size(), Player[].class);
	}
	
	public void destroy(){
		for(Player player : Bukkit.getServer().getWorld(world).getPlayers()){
			undisplay(player);
		}
		minigames.clear();
		players.clear();
		settings.clear();
	}
	
	public int getX1(){
		return x1;
	}
	
	public int getY1(){
		return y1;
	}
	
	public int getZ1(){
		return z1;
	}
	
	public int getX2(){
		return x2;
	}
	
	public int getY2(){
		return y2;
	}
	
	public int getZ2(){
		return z2;
	}
	
	public String getWorld(){
		return world;
	}
	
	public String getHumanReadableWorld(){
		switch(world){
			case "world":
				return "Overworld";
			case "world_nether":
				return "The Nether";
			case "world_the_end":
				return "The End";
			default:
				return world;
		}
	}
	
	private void updateContainedPlayers(){
		ArrayList<Player> containedPlayers = new ArrayList<Player>();
		for(World world : Bukkit.getServer().getWorlds())
		for(Player player : world.getPlayers()){
			if(isInZone(player.getLocation()))containedPlayers.add(player);
		}
		players = containedPlayers;
	}
	
}
