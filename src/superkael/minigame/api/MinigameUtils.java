package superkael.minigame.api;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinigameUtils {
	
	public static boolean parseAsBoolean(String string){
		return Boolean.parseBoolean(string);
	}
	
	public static byte parseAsByte(String string){
		return Byte.parseByte(string);
	}
	
	public static int parseAsInt(String string){
		return Integer.parseInt(string);
	}
	
	public static short parseAsShort(String string){
		return Short.parseShort(string);
	}
	
	public static long parseAsLong(String string){
		return Long.parseLong(string);
	}
	
	public static float parseAsFloat(String string){
		return Float.parseFloat(string);
	}
	
	public static double parseAsDouble(String string){
		return Double.parseDouble(string);
	}
	
	public static Material parseAsItem(String string){
		return Material.getMaterial(string.toUpperCase());
	}
	
	public static ItemStack parseAsItemStack(String string){
		String[] splitAsterisk = string.split("\\*");
		String[] splitColon;
		int stackSize;
		short stackMeta;
		Material stackItem;
		if(splitAsterisk.length == 1){
			stackSize = 1;
			splitColon = splitAsterisk[0].split(":");
		}else if(splitAsterisk.length == 2){
			stackSize = parseAsInt(splitAsterisk[0]);
			splitColon = splitAsterisk[1].split(":");
		}else{
			System.out.println("Warning: Attempted to parse itemstack \"" + string + "\", but it is not a valid itemstack!");
			return null;
		}
		if(splitColon.length == 1){
			stackMeta = 0;
		}else if(splitColon.length == 2){
			stackMeta = parseAsShort(splitColon[1]);
		}else{
			System.out.println("Warning: Attempted to parse itemstack \"" + string + "\", but it is not a valid itemstack!");
			return null;
		}
		stackItem = parseAsItem(splitColon[0]);
		try{
			ItemStack stack = new ItemStack(stackItem, stackSize, stackMeta);
			return stack;
		}catch(Exception e){
			return null;
		}
	}
	
	public String parseAsColoredString(String string){
		string = string.replaceAll("&0", "" + ChatColor.BLACK + "");
		string = string.replaceAll("&1", "" + ChatColor.DARK_BLUE + "");
		string = string.replaceAll("&2", "" + ChatColor.DARK_GREEN + "");
		string = string.replaceAll("&3", "" + ChatColor.DARK_AQUA + "");
		string = string.replaceAll("&4", "" + ChatColor.DARK_RED + "");
		string = string.replaceAll("&5", "" + ChatColor.DARK_PURPLE + "");
		string = string.replaceAll("&6", "" + ChatColor.GOLD + "");
		string = string.replaceAll("&7", "" + ChatColor.GRAY + "");
		string = string.replaceAll("&8", "" + ChatColor.DARK_GRAY + "");
		string = string.replaceAll("&9", "" + ChatColor.BLUE + "");
		string = string.replaceAll("&a", "" + ChatColor.GREEN + "");
		string = string.replaceAll("&b", "" + ChatColor.AQUA + "");
		string = string.replaceAll("&c", "" + ChatColor.RED + "");
		string = string.replaceAll("&d", "" + ChatColor.LIGHT_PURPLE + "");
		string = string.replaceAll("&e", "" + ChatColor.YELLOW + "");
		string = string.replaceAll("&f", "" + ChatColor.WHITE + "");
		string = string.replaceAll("&l", "" + ChatColor.BOLD + "");
		string = string.replaceAll("&n", "" + ChatColor.UNDERLINE + "");
		string = string.replaceAll("&o", "" + ChatColor.ITALIC + "");
		string = string.replaceAll("&m", "" + ChatColor.STRIKETHROUGH + "");
		string = string.replaceAll("&k", "" + ChatColor.MAGIC + "");
		string = string.replaceAll("&r", "" + ChatColor.RESET + "");
		return string;
	}
}
