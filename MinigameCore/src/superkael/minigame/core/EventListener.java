package superkael.minigame.core;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

import superkael.minigame.api.MinigameZone;
import superkael.minigame.modules.invcontrol.InventoryControl;

import java.util.Hashtable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class EventListener implements Listener {

	protected static Hashtable<String, Integer> zoneX = new Hashtable<String, Integer>();
	protected static Hashtable<String, Integer> zoneY = new Hashtable<String, Integer>();
	protected static Hashtable<String, Integer> zoneZ = new Hashtable<String, Integer>();
	
	protected static Hashtable<String, String> zoneName = new Hashtable<String, String>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND){
			Block block = event.getClickedBlock();
			if(MinigameCore.instance.zoneSelectCorner.get(player.getName()) != null){
				if(MinigameCore.instance.zoneSelectCorner.get(player.getName()) == 2){
					int zoneID;
					if(zoneName.get(player.getName()) == null){
						zoneID = ZoneHandler.addZone(new MinigameZone(zoneX.get(player.getName()),zoneY.get(player.getName()),zoneZ.get(player.getName()),block.getX(),block.getY(),block.getZ(),player.getWorld().getName()));
						player.sendMessage(ChatColor.GREEN + "A new game zone has been successfully defined");
					}else{
						zoneID = ZoneHandler.addZone(new MinigameZone(zoneX.get(player.getName()),zoneY.get(player.getName()),zoneZ.get(player.getName()),block.getX(),block.getY(),block.getZ(),player.getWorld().getName(),zoneName.get(player.getName())));
						player.sendMessage(ChatColor.GREEN + "The game zone \"" + zoneName.get(player.getName()) + "\" has been successfully defined");
					}
					MinigameCore.instance.zoneSelectCorner.put(player.getName(), 0);
					zoneName.remove(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinigameCore.instance, new Runnable(){
						public void run(){
							ZoneHandler.displayZone(player,zoneID,200);
						}
					},1);
					MinigameCore.instance.saveZoneConfigFile();
				}
				if(MinigameCore.instance.zoneSelectCorner.get(player.getName()) == 1){
					zoneX.put(player.getName(), block.getX());
					zoneY.put(player.getName(), block.getY());
					zoneZ.put(player.getName(), block.getZ());
					player.sendMessage(ChatColor.GREEN + "Now select the second corner");
					player.sendBlockChange(new Location(player.getWorld(),zoneX.get(player.getName()),zoneY.get(player.getName()),zoneZ.get(player.getName())),Material.GLOWSTONE,(byte)0);
					MinigameCore.instance.zoneSelectCorner.put(player.getName(), 2);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinigameCore.instance, new Runnable(){
						public void run(){
							player.sendBlockChange(new Location(player.getWorld(),zoneX.get(player.getName()),zoneY.get(player.getName()),zoneZ.get(player.getName())),Material.GLOWSTONE,(byte)0);
						}
					},1);
				}
			}
		}
    }
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e){
		InventoryControl.eventHandler.onClickEvent(e);
	}
}
