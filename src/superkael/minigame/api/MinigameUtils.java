package superkael.minigame.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinigameUtils {
	
	public static boolean parseAsBoolean(String value){
		return Boolean.parseBoolean(value);
	}
	
	public static byte parseAsByte(String value){
		return Byte.parseByte(value);
	}
	
	public static int parseAsInt(String value){
		return Integer.parseInt(value);
	}
	
	public static short parseAsShort(String value){
		return Short.parseShort(value);
	}
	
	public static long parseAsLong(String value){
		return Long.parseLong(value);
	}
	
	public static float parseAsFloat(String value){
		return Float.parseFloat(value);
	}
	
	public static double parseAsDouble(String value){
		return Double.parseDouble(value);
	}
	
	public static Material parseAsItem(String value){
		return Material.getMaterial(value.toUpperCase());
	}
	
	public static ItemStack parseAsItemStack(String value){
		String[] splitAsterisk = value.split("\\*");
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
			System.out.println("Warning: Attempted to parse itemstack \"" + value + "\", but it is not a valid itemstack!");
			return null;
		}
		if(splitColon.length == 1){
			stackMeta = 0;
		}else if(splitColon.length == 2){
			stackMeta = parseAsShort(splitColon[1]);
		}else{
			System.out.println("Warning: Attempted to parse itemstack \"" + value + "\", but it is not a valid itemstack!");
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
}
