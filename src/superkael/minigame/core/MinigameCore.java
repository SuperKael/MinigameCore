package superkael.minigame.core;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import superkael.minigame.api.*;
import superkael.minigame.modules.automessages.AutoMessages;
import superkael.minigame.modules.invcontrol.InventoryControl;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class MinigameCore extends JavaPlugin {
	
	private int onTickTaskID;
	public static MinigameCore instance;
	public static List<String> configModules;
	protected Hashtable<String, Integer> zoneSelectCorner = new Hashtable<String, Integer>();
	
	@Override
    public void onEnable(){
		instance = this;
		loadConfigFile();
		getCommand("minigame").setExecutor(new BaseCommand());
		getCommand("minigame").setUsage(ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame <mode> [args]");
		onTickTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				onTick();
			}
		},0,1);
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		for(Plugin plugin : Bukkit.getPluginManager().getPlugins()){
			if(plugin instanceof MinigamePlugin){
				MinigameHandler.registerGame((MinigamePlugin)plugin, false);
			}
		}
		if(configModules.contains("Inventory Control")){
			MinigameHandler.registerGame(new InventoryControl(), false);
		}
		if(configModules.contains("Automatic Messages")){
			MinigameHandler.registerGame(new AutoMessages(), false);
		}
		MinigameHandler.verifyDependencies();
		MinigameHandler.loadGames();
		loadZoneConfigFile();
    }
    
	@Override
    public void onDisable(){
		getServer().getScheduler().cancelTask(onTickTaskID);
		saveZoneConfigFile();
		MinigameHandler.unloadGames();
		ZoneHandler.destroyZones(ZoneHandler.getZones(),false);
    }
	
	public void onTick(){
		MinigameHandler.onTick();
		ZoneHandler.onTick();
	}
	
	public boolean loadConfigFile(){
		saveDefaultConfig();
		YamlConfiguration configData = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
		configModules = configData.getStringList("Enabled Modules");
		return true;
	}
	
	public boolean saveZoneConfigFile(){
		YamlConfiguration zoneData = new YamlConfiguration();
		zoneData.createSection("ZoneData");
		for(World world : getServer().getWorlds()){
			zoneData.createSection("ZoneData." + world.getName());
			zoneData.createSection("ZoneData." + world.getName() + ".Global");
			zoneData.set("ZoneData." + world.getName() + ".Global.Plugins", ZoneHandler.getWorldZone(world.getName()).getGames().toArray(new String[ZoneHandler.getWorldZone(world.getName()).getGames().size()]));
			zoneData.set("ZoneData." + world.getName() + ".Global.Settings", ZoneHandler.getWorldZone(world.getName()).getSettingsAsStrings().toArray(new String[ZoneHandler.getWorldZone(world.getName()).getSettings().size()]));
			MinigameZone[] worldZones = ZoneHandler.getZonesForWorld(world.getName());
			zoneData.set("ZoneData." + world.getName() + ".ZoneCount", worldZones.length);
			for(int i = 0;i < worldZones.length;i++){
				zoneData.createSection("ZoneData." + world.getName() + ".zone_" + i);
				if(worldZones[i].hasName())zoneData.set("ZoneData." + world.getName() + ".zone_" + i + ".Name", worldZones[i].getName());
				zoneData.set("ZoneData." + world.getName() + ".zone_" + i + ".Plugins", worldZones[i].getGames().toArray(new String[worldZones[i].getGames().size()]));
				zoneData.set("ZoneData." + world.getName() + ".zone_" + i + ".Settings", worldZones[i].getSettingsAsStrings().toArray(new String[worldZones[i].getSettings().size()]));
				zoneData.set("ZoneData." + world.getName() + ".zone_" + i + ".Corner1", new int[]{worldZones[i].getX1(),worldZones[i].getY1(),worldZones[i].getZ1()});
				zoneData.set("ZoneData." + world.getName() + ".zone_" + i + ".Corner2", new int[]{worldZones[i].getX2(),worldZones[i].getY2(),worldZones[i].getZ2()});
			}
		}
		try {
			zoneData.save(new File(getDataFolder(), "zones.yml"));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean loadZoneConfigFile(){
		YamlConfiguration zoneData = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "zones.yml"));
		for(World world : getServer().getWorlds()){
			for(String game : zoneData.getStringList("ZoneData." + world.getName() + ".Global.Plugins")){
				ZoneHandler.getWorldZone(world.getName()).addGame(game);
			}
			for(String game : zoneData.getStringList("ZoneData." + world.getName() + ".Global.Settings")){
				String[] settingData = game.split(": ",2);
				if(settingData.length == 2){
					ZoneHandler.getWorldZone(world.getName()).addSetting(settingData[0],settingData[1]);
				}
			}
			int worldZones = zoneData.getInt("ZoneData." + world.getName() + ".ZoneCount");
			for(int i = 0;i < worldZones;i++){
				List<Integer> Corner1 = zoneData.getIntegerList("ZoneData." + world.getName() + ".zone_" + i + ".Corner1");
				List<Integer> Corner2 = zoneData.getIntegerList("ZoneData." + world.getName() + ".zone_" + i + ".Corner2");
				MinigameZone zone;
				if(zoneData.contains("ZoneData." + world.getName() + ".zone_" + i + ".Name")){
					String name = zoneData.getString("ZoneData." + world.getName() + ".zone_" + i + ".Name");
					zone = new MinigameZone(Corner1.get(0),Corner1.get(1),Corner1.get(2),Corner2.get(0),Corner2.get(1),Corner2.get(2),world.getName(),name);
				}else{
					zone = new MinigameZone(Corner1.get(0),Corner1.get(1),Corner1.get(2),Corner2.get(0),Corner2.get(1),Corner2.get(2),world.getName());
				}
				for(String game : zoneData.getStringList("ZoneData." + world.getName() + ".zone_" + i + ".Plugins")){
					zone.addGame(game);
				}
				for(String game : zoneData.getStringList("ZoneData." + world.getName() + ".zone_" + i + ".Settings")){
					String[] settingData = game.replaceAll(": ", ":").split(":");
					if(settingData.length == 2){
						zone.addSetting(settingData[0], settingData[1]);
					}
				}
				ZoneHandler.addZone(zone);
			}
		}
		saveZoneConfigFile();
		return true;
	}
	
}
