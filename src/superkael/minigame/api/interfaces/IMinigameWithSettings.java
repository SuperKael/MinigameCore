package superkael.minigame.api.interfaces;

import java.util.List;

import superkael.minigame.api.*;

public interface IMinigameWithSettings {

	public List<String> getSettings();
	public String getSettingDescription(String setting);
	public SettingType getSettingType(String setting);
	
}
