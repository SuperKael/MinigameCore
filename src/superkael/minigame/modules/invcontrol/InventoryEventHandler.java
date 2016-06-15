package superkael.minigame.modules.invcontrol;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

import superkael.minigame.api.MinigameUtils;
import superkael.minigame.api.MinigameZone;
import superkael.minigame.api.ZoneHelper;

public class InventoryEventHandler {
    public void onClickEvent(InventoryInteractEvent e){
    	Player player = (Player)e.getWhoClicked();
    	MinigameZone[] zones = ZoneHelper.getZonesWithPlayerAndGame(player, InventoryControl.instance.getID());
        if(zones.length > 0){
        	boolean shouldCancel = false;
        	for(MinigameZone zone : zones){
        		if(MinigameUtils.parseAsBoolean(zone.getSetting("forceinventory"))){
        			shouldCancel = true;
        		}
        	}
        	if(shouldCancel)e.setCancelled(true);
        }
    }
}
