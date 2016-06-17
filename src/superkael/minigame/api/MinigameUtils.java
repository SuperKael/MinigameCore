package superkael.minigame.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinigameUtils {
	
	public static Boolean parseAsBoolean(String string){
		return Boolean.parseBoolean(string);
	}
	
	public static Byte parseAsByte(String string){
		return Byte.parseByte(string);
	}
	
	public static Short parseAsShort(String string){
		return Short.parseShort(string);
	}
	
	public static Integer parseAsInt(String string){
		return Integer.parseInt(string);
	}
	
	public static Long parseAsLong(String string){
		return Long.parseLong(string);
	}
	
	public static Float parseAsFloat(String string){
		return Float.parseFloat(string);
	}
	
	public static Double parseAsDouble(String string){
		return Double.parseDouble(string);
	}
	
	public static Material parseAsItem(String string){
		return Material.getMaterial(string.toUpperCase());
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack parseAsItemStack(String string){
		String[] splitAsterisk = string.split("\\*");
		String[] splitColon;
		String[] splitSpace;
		int stackSize;
		short stackMeta;
		Material stackItem;
		String stackNBT;
		if(splitAsterisk.length == 1){
			splitColon = splitAsterisk[0].split(":");
			stackSize = 1;
		}else if(splitAsterisk.length == 2){
			splitColon = splitAsterisk[1].split(":");
			stackSize = parseAsInt(splitAsterisk[0]);
		}else{
			System.out.println("Warning: Attempted to parse itemstack \"" + string + "\", but it is not a valid itemstack!");
			return null;
		}
		if(splitColon.length == 1){
			splitSpace = splitColon[0].split(" ",2);
			stackMeta = 0;
		}else if(splitColon.length == 2){
			splitSpace = splitColon[1].split(" ",2);
			stackMeta = parseAsShort(splitSpace[0]);
		}else{
			System.out.println("Warning: Attempted to parse itemstack \"" + string + "\", but it is not a valid itemstack!");
			return null;
		}
		if(splitSpace.length == 1){
			stackNBT = null;
		}else if(splitSpace.length == 2){
			stackNBT = splitSpace[1];
		}else{
			System.out.println("Warning: Attempted to parse itemstack \"" + string + "\", but it is not a valid itemstack!");
			return null;
		}
		stackItem = parseAsItem(splitColon[0]);
		try{
			ItemStack stack = new ItemStack(stackItem, stackSize, stackMeta);
			stack = Bukkit.getUnsafe().modifyItemStack(stack, stackNBT);
			return stack;
		}catch(Exception e){
			return null;
		}
	}
	
	public static String parseAsFormattedString(String string){
		if(string == null)return null;
		/*
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
		string = string.replaceAll("&\\\\", "" + '\n' + "");
		return string;
		*/
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T parseAsSettingType(String string, SettingType type){
		switch(type){
			case STRING:
				return (T)string;
			case FSTRING:
				return (T)parseAsFormattedString(string);
			case BOOLEAN:
				return (T)parseAsBoolean(string);
			case BYTE:
				return (T)parseAsByte(string);
			case SHORT:
				return (T)parseAsShort(string);
			case INT:
				return (T)parseAsInt(string);
			case LONG:
				return (T)parseAsLong(string);
			case FLOAT:
				return (T)parseAsFloat(string);
			case DOUBLE:
				return (T)parseAsDouble(string);
			case ITEM:
				return (T)parseAsItem(string);
			case ITEMSTACK:
				return (T)parseAsItemStack(string);
			case OTHER:
				return (T)string;
			default:
				return null;
		}
	}
}
