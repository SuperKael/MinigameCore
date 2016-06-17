package superkael.minigame.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum SettingType{
	
	STRING("String","A text value",String.class),
	FSTRING("FormattedString","A text value with advanced formatting",String.class),
	BOOLEAN("Boolean","A 'True' or 'False' value",Boolean.class),
	BYTE("Byte","A number from -128 to 127",Byte.class),
	SHORT("Short","A number from -32,768 to 32,767",Short.class),
	INT("Int","A number from -2^31 to 2^31-1",Integer.class),
	LONG("Long","A number from -2^63 to 2^63-1",Long.class),
	FLOAT("Float","A decimal Number from -2^31 to 2^31-1",Float.class),
	DOUBLE("Double","A decimal Number from -2^63 to 2^63-1",Double.class),
	ITEM("Item","An item type",Material.class),
	ITEMSTACK("ItemStack","An item stack, format: Amount*ItemID:ItemMeta {NBT Tag}",ItemStack.class),
	OTHER("Other","A non-standard setting value",Object.class);
	
	private String name;
	private String info;
	private Class<?> typeClass;
	
	SettingType(String name,String info,Class<?> typeClass){
		this.name = name;
		this.info = info;
		this.typeClass = typeClass;
	}
	
	public String getName(){
		return name;
	}
	
	public String getInfo(){
		return info;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Object> Class<T> getTypeClass(){
		if(Object.class.isAssignableFrom(typeClass)){
			return (Class<T>)typeClass;
		}else{
			return null;
		}
	}
	
	public String toString(){
		return getName() + " - " + getInfo();
	}
}