package superkael.minigame.core;

public interface CommandOption {
	
	public String getOption();
	
	public String[] getHelp();
	
	public CommandOption[] getChildren();
	
	public void runCommand(String[] args);
	
}
