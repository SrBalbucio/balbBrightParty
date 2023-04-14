package xyz.balbucio.bright.party.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	public Item(Material m, int quantidade, short data) {
		itemStack = new ItemStack(m, quantidade, data);
		itemMeta = itemStack.getItemMeta();
		
	}
	public Item setName(String s) {
		itemMeta.setDisplayName(s);
		itemStack.setItemMeta(itemMeta);
		return this;
		
	}
	public Item setLore(List<String> lore) {
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	public ItemStack getItemStack() {
		return itemStack;
	}
}
