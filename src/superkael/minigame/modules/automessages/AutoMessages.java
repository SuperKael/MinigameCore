package superkael.minigame.modules.automessages;

import org.bukkit.entity.Player;

import superkael.minigame.api.*;

public class AutoMessages extends MinigameModule {

	private static final String ID = "automessages";
	private static final String Name = "Automatic Messages";
	
	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getGameName() {
		return Name;
	}
	
	@Override
	public void onPlayerEnterGame(MinigameZone zone, Player player) {
		if(zone.hasSetting("MessageOnEnter")){
			player.sendMessage(MinigameUtils.parseAsFormatedString(zone.getSetting("MessageOnEnter")));
		}
	}
	
	@Override
	public void onPlayerExitGame(MinigameZone zone, Player player) {
		if(zone.hasSetting("MessageOnExit")){
			player.sendMessage(MinigameUtils.parseAsFormatedString(zone.getSetting("MessageOnExit")));
		}
	}

}
