package superkael.minigame.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import superkael.minigame.api.*;

import java.util.Arrays;
import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class BaseCommand implements CommandExecutor{

	protected Hashtable<Player, Location> confirmDestroy = new Hashtable<Player, Location>();
	protected Hashtable<Player, Boolean> specificZone = new Hashtable<Player, Boolean>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("minigame.cmd")){
			sender.sendMessage(new String[]{
				ChatColor.RED + "You do not have permission to use this command",
			});
			return true;
		}
		Player player = null;
		if(sender instanceof Player)player = (Player)sender;
		if(args.length > 0){
			switch(args[0].toLowerCase()){
				case "help":
					if(args.length > 1){
						switch(args[1].toLowerCase()){
							case "help":
								sender.sendMessage(new String[]{
									ChatColor.ITALIC + "" + ChatColor.GREEN + "Really?",
								});
							break;
							case "zone":
								if(args.length > 2){
									switch(args[2].toLowerCase()){
										case "create":
											sender.sendMessage(new String[]{
												"Used to create a new minigame zone. Right-click to select corners of zone box.",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone create [name]",
											});
										break;
										case "destroy":
											sender.sendMessage(new String[]{
												"Used to destroy minigame zones. stand inside the zone(s) you wish to destroy, or enter the name of the target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone destroy [name]",
											});
										break;
										case "rename":
											sender.sendMessage(new String[]{
												"Used to rename a game zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone rename <oldName> <newName>",
											});
										break;
										case "display":
											sender.sendMessage(new String[]{
												"Used to display zones. use 'local' to show local zones, use 'global' to show all zones, or use 'name' to display a specific zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone display <local/global/name> [name]",
											});
										break;
										case "undisplay":
											sender.sendMessage(new String[]{
												"Used to undisplay zones. use 'local' to hide local zones, use 'global' to hide all zones, or use 'name' to hide a specific zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone undisplay <local/global/name> [name]",
											});
										break;
										case "addgame":
											sender.sendMessage(new String[]{
												"Adds a game to target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone addgame <zoneName> <gameName>",
											});
										break;
										case "delgame":
											sender.sendMessage(new String[]{
												"Removes a game from target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone delgame <zoneName> <gameName>",
											});
										break;
										case "addsetting":
											sender.sendMessage(new String[]{
												"Adds/Modifies a setting of the target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone addsetting <zoneName> <settingName> <value>",
											});
										break;
										case "delsetting":
											sender.sendMessage(new String[]{
												"Removes a setting from the target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone delsetting <zoneName> <settingName>",
											});
										break;
										case "showgames":
											sender.sendMessage(new String[]{
												"Displays the games linked to the local zone(s), or the target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone showgames [zoneName]",
											});
										break;
										case "showsettings":
											sender.sendMessage(new String[]{
												"Displays the settings associated with the local zone(s), or the target zone",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone showsettings [zoneName]",
											});
										break;
										case "showzones":
											sender.sendMessage(new String[]{
												"Displays the names of target zones",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame zone showzones <local/global>",
											});
										break;
										default:
											sender.sendMessage(new String[]{
												ChatColor.RED + "Invalid zoning mode. use \"/minigame help zone\" for a list of valid zoning modes",
											});
										break;
									}
								}else{
									sender.sendMessage(new String[]{
										"Command mode used for defining and modifying minigame zones.",
										ChatColor.GOLD + "Usage: " + ChatColor.RESET + "/minigame zone <mode> [args]",
										"Available Zoning Modes: " + ChatColor.GOLD + "create, destroy, rename, display, undisplay, addgame, delgame, addsetting, delsetting, showgames, showsettings, showzones",
									});
								}
							break;
							case "world":
								if(args.length > 2){
									switch(args[2].toLowerCase()){
										case "addgame":
											sender.sendMessage(new String[]{
												"Adds a game to the world",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame world addgame <name>",
											});
										break;
										case "delgame":
											sender.sendMessage(new String[]{
												"Removes a game from the world",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame world delgame <name>",
											});
										break;
										case "addsetting":
											sender.sendMessage(new String[]{
												"Adds/Modifies a setting to the world",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame world addsetting <name> <value>",
											});
										break;
										case "delsetting":
											sender.sendMessage(new String[]{
												"Removes a setting from the world",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame world delsetting <name>",
											});
										break;
										case "showgames":
											sender.sendMessage(new String[]{
												"Displays the games linked to the world",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame world showgames",
											});
										break;
										case "showsettings":
											sender.sendMessage(new String[]{
												"Displays the settings associated to the world",
												ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame world showsettings",
											});
										break;
										default:
											sender.sendMessage(new String[]{
												ChatColor.RED + "Invalid world mode. use \"/minigame help world\" for a list of valid world modes",
											});
										break;
									}
								}else{
									sender.sendMessage(new String[]{
										"Command mode used for modifying the game settings of the world.",
										ChatColor.GOLD + "Usage: " + ChatColor.RESET + "/minigame world <mode> [args]",
										"Available World Modes: " + ChatColor.GOLD + "addgame, delgame, addsetting, delsetting, showgames, showsettings",
									});
								}
							break;
							default:
								sender.sendMessage(new String[]{
									ChatColor.RED + "Invalid mode. use \"/minigame help\" for a list of valid modes",
								});
							break;
						}
					}else{
						sender.sendMessage(new String[]{
							"The base command used by the Minigame plugin.",
							ChatColor.GOLD + "Usage:" + ChatColor.RESET + " /minigame <mode> [args]",
							"Available Modes: " + ChatColor.GOLD + "help, zone, world",
						});
					}
				break;
				case "zone":
					if(args.length > 1){
						switch(args[1].toLowerCase()){
							case "create":
								if(!(sender instanceof Player)){
									sender.sendMessage(new String[]{
										ChatColor.RED + "This command must be run by a player"
									});
									return true;
								}
								sender.sendMessage(new String[]{
									ChatColor.GREEN + "Right-click two blocks to use as corners for the zone"
								});
								MinigameCore.instance.zoneSelectCorner.put(player.getName(), 1);
								if(args.length > 2){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										EventListener.zoneName.put(player.getName(), args[2]);
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "That name is already in use"
										});
									}
								}
							break;
							case "destroy":
								if(!(sender instanceof Player)){
									sender.sendMessage(new String[]{
										ChatColor.RED + "This command must be run by a player"
									});
									return true;
								}
								if(args.length > 2){
									switch(args[2].toLowerCase()){
									case "confirm":
										if(confirmDestroy.containsKey(player)){
											MinigameZone[] zones = ZoneHandler.getLocalZones(confirmDestroy.get(player));
											if(specificZone.get(player)){
												sender.sendMessage(new String[]{
													ChatColor.GREEN + "Zone \"" + ZoneHandler.getZonesForLocation(confirmDestroy.get(player)).getName() + "\" has Successfully destroyed"
												});
												ZoneHandler.destroyZoneAtLocation(confirmDestroy.get(player));
											}else{
												if(zones.length > 0){
													if(zones.length > 1){
														sender.sendMessage(new String[]{
															ChatColor.GREEN + "Zones Successfully destroyed"
														});
													}else{
														sender.sendMessage(new String[]{
															ChatColor.GREEN + "Zone Successfully destroyed"
														});
													}
												}else{
													sender.sendMessage(new String[]{
														ChatColor.RED + "There are no zones at the target location, mabye someone else has already destroyed them?"
													});
												}
												ZoneHandler.destroyZones(zones);
											}
											confirmDestroy.remove(player);
											specificZone.remove(player);
										}else{
											sender.sendMessage(new String[]{
												ChatColor.RED + "A zone destruction is not in progress, so it can't be confirmed."
											});
										}
									break;
									case "cancel":
										if(confirmDestroy.containsKey(player)){
											MinigameZone[] zones = ZoneHandler.getLocalZones(confirmDestroy.get(player));
											for(MinigameZone zone : zones){
												zone.undisplay(player);
											}
											confirmDestroy.remove(player);
											specificZone.remove(player);
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Zone destruction canceled"
											});
										}else{
											sender.sendMessage(new String[]{
												ChatColor.RED + "A zone destruction is not in progress, so it can't be canceled."
											});
										}
									break;
									default:
										MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
										if(zone == null){
											sender.sendMessage(new String[]{
												ChatColor.RED + "There is no zone by that name",
											});
											return true;
										}
										if(confirmDestroy.containsKey(player)){
											sender.sendMessage(new String[]{
												ChatColor.RED + "A zone destruction is already in progress. complete it using",
												ChatColor.RED + "/minigame zone destroy <confirm/cancel>",
											});
											return true;
										}
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Are you sure you wish to destroy the zone \"" + args[2] + "\"?"
										});
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Use \"/minigame zone destroy confirm\" to confirm destruction, or \"/minigame zone destroy cancel\" to abort"
										});
										zone.display(player, 1200);
										confirmDestroy.put(player, new Location(Bukkit.getServer().getWorld(zone.getWorld()),zone.getX1(),zone.getY1(),zone.getZ1()));
										specificZone.put(player, true);
									break;
									}
								}else{
									MinigameZone[] zones = ZoneHandler.getLocalZones(player.getLocation());
									if(confirmDestroy.containsKey(player)){
										sender.sendMessage(new String[]{
											ChatColor.RED + "A zone destruction is already in progress. complete it using",
											ChatColor.RED + "/minigame zone destroy <confirm/cancel>",
										});
										return true;
									}
									if(zones.length > 0){
										if(zones.length > 1){
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Are you sure you wish to destroy all " + zones.length + " zones at this location?"
											});
										}else{
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Are you sure you wish to destroy the zone at this location?"
											});
										}
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Use \"/minigame zone destroy confirm\" to confirm destruction, or \"/minigame zone destroy cancel\" to abort"
										});
										for(MinigameZone zone : zones){
											zone.display(player, 1200);
										}
										confirmDestroy.put(player, player.getLocation());
										specificZone.put(player, false);
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "There are no zones at this location to destroy"
										});
									}
								}
								MinigameCore.instance.zoneSelectCorner.put(player.getName(), 1);
							break;
							case "rename":
								if(args.length > 3){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
										});
										return true;
									}
									MinigameZone zoneForName = ZoneHandler.getZoneForName(args[3]);
									if(zoneForName == null){
										zone.setName(args[3]);
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "That name is already in use"
										});
									}
									zone.setName(args[3]);
									sender.sendMessage(new String[]{
										ChatColor.GREEN + "Successfully renamed zone \"" + args[2] + "\" to \"" + args[3] + "\""
									});
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame zone display <oldName> <newName>",
									});
								}
							break;
							case "display":
								if(!(sender instanceof Player)){
									sender.sendMessage(new String[]{
										ChatColor.RED + "This command must be run by a player"
									});
									return true;
								}
								if(args.length > 2){
									switch(args[2].toLowerCase()){
										case "local":
											for(MinigameZone zone : ZoneHandler.getLocalZones(player.getLocation())){
												zone.display(player);
											}
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Displaying local zones"
											});
										break;
										case "global":
											ZoneHandler.displayAllZones(player);
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Displaying all zones"
											});
										break;
										case "name":
											if(args.length > 3){
												if(ZoneHandler.displayZone(player, args[3])){
													sender.sendMessage(new String[]{
														ChatColor.GREEN + "Displaying zone \"" + args[3] + "\""
													});
												}else{
													sender.sendMessage(new String[]{
														ChatColor.RED + "There is no zone by the name \"" + args[3] + "\""
													});
												}
											}else{
												sender.sendMessage(new String[]{
													ChatColor.RED + "A name must be provided to display a zone by a particular name",
												});
											}
										break;
										default:
											sender.sendMessage(new String[]{
												ChatColor.RED + "Invalid zone display mode. Use either 'local', 'global', or 'name'",
											});
										break;
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame zone display <local/global/name> [name]",
									});
								}
							break;
							case "undisplay":
								if(!(sender instanceof Player)){
									sender.sendMessage(new String[]{
										ChatColor.RED + "This command must be run by a player"
									});
									return true;
								}
								if(args.length > 2){
									switch(args[2].toLowerCase()){
										case "local":
											for(MinigameZone zone : ZoneHandler.getLocalZones(player.getLocation())){
												zone.undisplay(player);
											}
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Undisplaying local zones"
											});
										break;
										case "global":
											ZoneHandler.undisplayAllZones(player);
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Undisplaying all zones"
											});
										break;
										case "name":
											if(args.length > 3){
												if(ZoneHandler.undisplayZone(player, args[3])){
													sender.sendMessage(new String[]{
														ChatColor.GREEN + "Undisplaying zone \"" + args[3] + "\""
													});
												}else{
													sender.sendMessage(new String[]{
														ChatColor.RED + "There is no zone by the name \"" + args[3] + "\""
													});
												}
											}else{
												sender.sendMessage(new String[]{
													ChatColor.RED + "A name must be provided to undisplay a zone by a particular name",
												});
											}
										break;
										default:
											sender.sendMessage(new String[]{
												ChatColor.RED + "Invalid zone undisplay mode. Use either 'local', 'global', or 'name'",
											});
										break;
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame zone undisplay <local/global/name> [name]",
									});
								}
							break;
							case "addgame":
								if(args.length > 3){
									if(MinigameHandler.isGameRegistered(args[3])){
										MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
										if(zone == null){
											sender.sendMessage(new String[]{
												ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
											});
											return true;
										}
										if(zone.addGame(args[3])){
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Successfully added minigame to zone \"" + args[2] + "\""
											});
										}else{
											sender.sendMessage(new String[]{
												ChatColor.RED + "Failed to add minigame to zone \"" + args[2] + "\": minigame already present"
											});
										}
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "Unknown minigame: " + args[3]
										});
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame zone addgame <zoneName> <gameName>",
									});
								}
							break;
							case "delgame":
								if(args.length > 3){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
										});
										return true;
									}
									if(zone.delGame(args[3])){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Successfully removed minigame from zone \"" + args[2] + "\""
										});
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "Failed to remove minigame from zone \"" + args[2] + "\": minigame not present"
										});
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame zone delgame <zoneName> <gameName>",
									});
								}
							break;
							case "addsetting":
								if(args.length > 4){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
										});
										return true;
									}
									if(zone.addSetting(args[3],String.join(" ",Arrays.copyOfRange(args, 4, args.length)))){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Successfully added setting from zone \"" + args[2] + "\""
										});
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "Um... I don't know what you just did, but I think you broked it >.>"
										});
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame world addsetting <zoneName> <settingName> <value>",
									});
								}
							break;
							case "delsetting":
								if(args.length > 3){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
										});
										return true;
									}
									if(zone.delSetting(args[3])){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Successfully removed setting from zone \"" + args[2] + "\""
										});
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "Failed to remove setting from zone \"" + args[2] + "\": setting not present"
										});
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame world delsetting <zoneName> <settingName>",
									});
								}
							break;
							case "showgames":
								if(args.length > 2){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
										});
										return true;
									}
									sender.sendMessage(new String[]{
										ChatColor.GREEN + zone.getName() + ":"
									});
									String[] games = zone.getGames().toArray(new String[zone.getGames().size()]);
									if(games.length < 1){
										sender.sendMessage(new String[]{
												ChatColor.GREEN + " - No Games"
										});
									}
									for(String game : games){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + " - " + MinigameHandler.getGameByID(game).getGameName() + " (" + game + ")"
										});
									}
								}else{
									int zoneNumber = 0;
									for(MinigameZone zone : ZoneHandler.getLocalZones(player.getLocation())){
										zoneNumber++;
										String[] games = zone.getGames().toArray(new String[zone.getGames().size()]);
										sender.sendMessage(new String[]{
											ChatColor.GREEN + zone.getName() + ":"
										});
										if(games.length < 1){
											sender.sendMessage(new String[]{
													ChatColor.GREEN + " - No Games"
											});
										}
										for(String game : games){
											sender.sendMessage(new String[]{
												ChatColor.GREEN + " - " + MinigameHandler.getGameByID(game).getGameName() + " (" + game + ")"
											});
										}
									}
									if(zoneNumber == 0){
										sender.sendMessage(new String[]{
												ChatColor.RED + "There are no zones at this location"
										});
									}
								}
							break;
							case "showsettings":
								if(args.length > 2){
									MinigameZone zone = ZoneHandler.getZoneForName(args[2]);
									if(zone == null){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There is no zone by the name \"" + args[2] + "\""
										});
										return true;
									}
									sender.sendMessage(new String[]{
										ChatColor.GREEN + zone.getName() + ":"
									});
									String[] settings = zone.getSettingsAsStrings().toArray(new String[zone.getSettings().size()]);
									if(settings.length < 1){
										sender.sendMessage(new String[]{
												ChatColor.GREEN + " - No Settings"
										});
									}
									for(String setting : settings){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + " - " + setting
										});
									}
								}else{
									int zoneNumber = 0;
									for(MinigameZone zone : ZoneHandler.getLocalZones(player.getLocation())){
										zoneNumber++;
										String[] settings = zone.getSettingsAsStrings().toArray(new String[zone.getSettings().size()]);
										sender.sendMessage(new String[]{
											ChatColor.GREEN + zone.getName() + ":"
										});
										if(settings.length < 1){
											sender.sendMessage(new String[]{
													ChatColor.GREEN + " - No Settings"
											});
										}
										for(String setting : settings){
											sender.sendMessage(new String[]{
												ChatColor.GREEN + " - " + setting
											});
										}
									}
									if(zoneNumber == 0){
										sender.sendMessage(new String[]{
											ChatColor.RED + "There are no zones at this location"
										});
									}
								}
							break;
							case "showzones":
								if(args.length > 2){
									switch(args[2].toLowerCase()){
										case "local":
											sender.sendMessage(new String[]{
													ChatColor.GREEN + "Local Zones:"
											});
											for(MinigameZone zone : ZoneHandler.getLocalZones(player.getLocation())){
												sender.sendMessage(new String[]{
														ChatColor.GREEN + " - " + zone.getName() + " - " + zone.getHumanReadableWorld()
												});
											}
										break;
										case "global":
											sender.sendMessage(new String[]{
												ChatColor.GREEN + "Global Zones:"
											});
											for(MinigameZone zone : ZoneHandler.getZones()){
												sender.sendMessage(new String[]{
													ChatColor.GREEN + " - " + zone.getName() + " - " + zone.getHumanReadableWorld()
												});
											}
										break;
										default:
											sender.sendMessage(new String[]{
												ChatColor.RED + "Invalid zone view mode. Use either 'local' or 'global'",
											});
										break;
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Usage: /minigame world showzones <local/global>",
									});
								}
							break;
							default:
								sender.sendMessage(new String[]{
									ChatColor.RED + "Invalid zoning mode. use \"/minigame help zone\" for a list of valid zoning modes",
								});
							break;
						}
					}else{
						sender.sendMessage(new String[]{
							ChatColor.RED + "Usage: /minigame zone <mode> [args]",
						});
					}
				break;
				case "world":
					if(args.length > 1){
						switch(args[1].toLowerCase()){
						case "addgame":
							if(args.length > 2){
								if(MinigameHandler.isGameRegistered(args[2])){
									boolean zoneAddSuccess = ZoneHandler.getWorldZone(player.getWorld().getName()).addGame(args[2]);
									if(zoneAddSuccess){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Successfully added minigame to world"
										});
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "Failed to add minigame to world: minigame already present"
										});
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Unknown minigame: " + args[2]
									});
								}
							}else{
								sender.sendMessage(new String[]{
									ChatColor.RED + "Usage: /minigame world addgame <name>",
								});
							}
						break;
						case "delgame":
							if(args.length > 2){
								if(MinigameHandler.isGameRegistered(args[2])){
									boolean zoneDelSuccess = ZoneHandler.getWorldZone(player.getWorld().getName()).delGame(args[2]);
									if(zoneDelSuccess){
										sender.sendMessage(new String[]{
											ChatColor.GREEN + "Successfully removed minigame from world"
										});
									}else{
										sender.sendMessage(new String[]{
											ChatColor.RED + "Failed to remove minigame from world: minigame not present"
										});
									}
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Unknown minigame: " + args[2]
									});
								}
							}else{
								sender.sendMessage(new String[]{
									ChatColor.RED + "Usage: /minigame world delgame <name>",
								});
							}
						break;
						case "addsetting":
							if(args.length > 3){
								boolean zoneAddSuccess = ZoneHandler.getWorldZone(player.getWorld().getName()).addSetting(args[2],String.join(" ",Arrays.copyOfRange(args, 3, args.length)));
								if(zoneAddSuccess){
									sender.sendMessage(new String[]{
										ChatColor.GREEN + "Successfully added setting to world"
									});
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Um... I don't know what you just did, but I think you broked it >.>"
									});
								}
							}else{
								sender.sendMessage(new String[]{
									ChatColor.RED + "Usage: /minigame world addsetting <name> <value>",
								});
							}
						break;
						case "delsetting":
							if(args.length > 2){
								boolean zoneDelSuccess = ZoneHandler.getWorldZone(player.getWorld().getName()).delSetting(args[2]);
								if(zoneDelSuccess){
									sender.sendMessage(new String[]{
										ChatColor.GREEN + "Successfully removed setting to world"
									});
								}else{
									sender.sendMessage(new String[]{
										ChatColor.RED + "Failed to remove setting from world: setting not present"
									});
								}
							}else{
								sender.sendMessage(new String[]{
									ChatColor.RED + "Usage: /minigame world delsetting <name>",
								});
							}
						break;
						case "showgames":
							MinigameZone gameZone = ZoneHandler.getWorldZone(player.getLocation().getWorld().getName());
							String[] games = gameZone.getGames().toArray(new String[gameZone.getGames().size()]);
							sender.sendMessage(new String[]{
								ChatColor.GREEN + "World Zone:"
							});
							if(games.length < 1){
								sender.sendMessage(new String[]{
									ChatColor.GREEN + " - No Games"
								});
							}
							for(String game : games){
								sender.sendMessage(new String[]{
									ChatColor.GREEN + " - " + MinigameHandler.getGameByID(game).getGameName() + " (" + game + ")"
								});
							}
						break;
						case "showsettings":
							MinigameZone settingZone = ZoneHandler.getWorldZone(player.getLocation().getWorld().getName());
							String[] settings = settingZone.getSettingsAsStrings().toArray(new String[settingZone.getSettings().size()]);sender.sendMessage(new String[]{
								ChatColor.GREEN + "World Zone:"
							});
							if(settings.length < 1){
								sender.sendMessage(new String[]{
									ChatColor.GREEN + " - No Settings"
								});
							}
							for(String setting : settings){
								sender.sendMessage(new String[]{
									ChatColor.GREEN + " - " + setting
								});
							}
						break;
						default:
							sender.sendMessage(new String[]{
								ChatColor.RED + "Invalid world mode. use \"/minigame help world\" for a list of valid world modes",
							});
						break;
						}
					}else{
						sender.sendMessage(new String[]{
							ChatColor.RED + "Usage: /minigame world <mode> [args]",
						});
					}
				break;
				default:	
					sender.sendMessage(new String[]{
						ChatColor.RED + "Invalid mode. use \"/minigame help\" for a list of valid modes",
					});
				break;
			}
		}else{
			return false;
		}
		return true;
	}

}
