package superkael.minigame.modules.invcontrol;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import superkael.minigame.api.MinigameModule;
import superkael.minigame.api.MinigameUtils;
import superkael.minigame.api.MinigameZone;

public class InventoryControl extends MinigameModule {

	private static final String ID = "invcontrol";
	private static final String Name = "Inventory Control";
	public static InventoryEventHandler eventHandler;
	
	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getGameName() {
		return Name;
	}
	
	@Override
	public void onGameLoad(){
		eventHandler = new InventoryEventHandler();
	}
	
	@Override
	public void onPlayerTick(MinigameZone zone, Player player){
		if(MinigameUtils.parseAsBoolean(zone.getSetting("forceinventory"))){
			for(int i = 0;i < 40;i++){
				if(zone.getSetting("inventory." + player.getName().toLowerCase() + ".slot." + i) == null){
					String setting = zone.getSetting("inventory.slot." + i);
					if(setting == null || setting.equals("")){
						if(player.getInventory().getItem(i) != null)player.getInventory().setItem(i, null);
					}else{
						ItemStack itemstack = MinigameUtils.parseAsItemStack(setting);
						if(player.getInventory().getItem(i) == null){
							player.getInventory().setItem(i, itemstack);
						}else
						if(!player.getInventory().getItem(i).equals(itemstack)){
							player.getInventory().setItem(i, itemstack);
						}
					}
				}else{
					String setting = zone.getSetting("inventory." + player.getName().toLowerCase() + ".slot." + i);
					if(setting != null){
						ItemStack itemstack = MinigameUtils.parseAsItemStack(setting);
						if(player.getInventory().getItem(i) == null){
							player.getInventory().setItem(i, itemstack);
						}else
						if(!player.getInventory().getItem(i).equals(itemstack)){
							player.getInventory().setItem(i, itemstack);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void onPlayerEnterZone(MinigameZone zone, Player player){
		if(MinigameUtils.parseAsBoolean(zone.getSetting("clearonenter"))){
			player.getInventory().clear();
		}
		if(!MinigameUtils.parseAsBoolean(zone.getSetting("forceinventory"))){
			for(int i = 0;i < 40;i++){
				if(zone.getSetting("inventory." + player.getName().toLowerCase() + ".slot." + i) == null){
					String setting = zone.getSetting("inventory.slot." + i);
					if(setting != null && !setting.equals("")){
						player.getInventory().addItem(MinigameUtils.parseAsItemStack(setting));
					}
				}else{
					String setting = zone.getSetting("inventory." + player.getName().toLowerCase() + ".slot." + i);
					if(setting != null){
						player.getInventory().addItem(MinigameUtils.parseAsItemStack(setting));
					}
				}
			}
		}
	}
	
	@Override
	public void onPlayerExitZone(MinigameZone zone, Player player){
		if(MinigameUtils.parseAsBoolean(zone.getSetting("clearonexit"))){
			player.getInventory().clear();
		}else{
			if(zone.hasSetting("clearonexit"))return;
		}
		if(!MinigameUtils.parseAsBoolean(zone.getSetting("forceinventory"))){
			for(int i = 0;i < 40;i++){
				String setting = zone.getSetting("inventory.slot." + i);
				if(!(setting == null || setting.equals(""))){
					player.getInventory().removeItem(MinigameUtils.parseAsItemStack(setting));
				}
			}
			for(int i = 0;i < 40;i++){
				String setting = zone.getSetting("inventory." + player.getName() + ".slot." + i);
				if(!(setting == null || setting.equals(""))){
					player.getInventory().removeItem(MinigameUtils.parseAsItemStack(setting));
				}
			}
		}
	}

}
