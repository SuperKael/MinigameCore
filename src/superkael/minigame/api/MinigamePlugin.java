package superkael.minigame.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MinigamePlugin extends JavaPlugin {
	
	public abstract String getID();
	
	public String[] dependencies(){
		return new String[]{};
	}
	
	public void onTick(){}
	public void onActiveTick(){}
	public void onZoneTick(MinigameZone zone){}
	public void onPlayerTick(MinigameZone zone, Player player){}
	public void onPlayerEnterZone(MinigameZone zone, Player player){}
	public void onPlayerExitZone(MinigameZone zone, Player player){}
	public void onWorldTick(MinigameZone zone){}
	public void onPlayerWorldTick(MinigameZone zone, Player player){}
	public void onPlayerEnterWorld(MinigameZone zone, Player player){}
	public void onPlayerExitWorld(MinigameZone zone, Player player){}
	
}
