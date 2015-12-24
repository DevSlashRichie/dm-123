package io.github.richstark.DeliveryMan.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.Prize.Prize;
import io.github.richstark.DeliveryMan.Prize.PrizeManager;
import io.github.richstark.DeliveryMan.Timer.TimerObject;
import net.md_5.bungee.api.ChatColor;

public class Menu {
	
	private static Main main;
	public Menu(Main i){
		main = i;
	}
	
	public void getMenu(final Player player){
		final Inventory inv = Bukkit.createInventory(null, 9*6, main.NPCName.replaceAll("&", "§"));
		
		new TimerObject("menu", new BukkitRunnable() {
			
			@Override
			public void run() {
				
		    	if(main.sqlActive == false){
		    		for(Prize p : PrizeManager.get().getPrizes()){
		    			if(p.getWhoClicked().contains(player.getUniqueId().toString())){
		    				List<String>lore = new ArrayList<>();
		    				for(String str : p.getAfterLore()){
		    					lore.add(ChatColor.translateAlternateColorCodes("&".charAt(0), str
		    			    			.replaceAll("%player%", player.getName())
		    			    			.replaceAll("%days%", ""+p.getDays())
		    			    			.replaceAll("%hours%", ""+p.getHours())
		    			    			.replaceAll("%minutes%", ""+p.getMinutes())
		    			    			.replaceAll("%seconds%", ""+p.getSeconds())
		    							));
			    				p.setCurrentMaterial(createItem(p.getAfterItem().getType(), p.getAfterName().replaceAll("&", "§"), lore));
			    				inv.setItem(p.getSlot(), p.getCurrentMaterial());
		    				}
		    			} else {
		    				List<String> lore = new ArrayList<>();
		    				for(String str : p.getBeforeLore()){			
		    					lore.add(ChatColor.translateAlternateColorCodes("&".charAt(0), str
		    			    			.replaceAll("%player%", player.getName())
		    			    			.replaceAll("%days%", ""+p.getDays())
		    			    			.replaceAll("%hours%", ""+p.getHours())
		    			    			.replaceAll("%minutes%", ""+p.getMinutes())
		    			    			.replaceAll("%seconds%", ""+p.getSeconds())
		    							));
		    					p.setCurrentMaterial(createItem(p.getBeforeItem().getType(), p.getBeforeName().replaceAll("&", "§"), lore));
		    				}
		    				inv.setItem(p.getSlot(), p.getCurrentMaterial());
		    			}
		    		}
		    	}		
			}
		}.runTaskTimer(main, 3, 3));
		player.openInventory(inv);
	}
	
    public ItemStack createItem(Material material, String name, List<String> lore){
        ItemStack is = new ItemStack(material);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(name);
        ism.setLore(lore);
        is.setItemMeta(ism);
        
        return is;
    } 

    public void updateItems(Player player){
    	if(main.sqlActive == false){
    		for(Prize p : PrizeManager.get().getPrizes()){
    			if(p.getWhoClicked().contains(player.getUniqueId().toString())){
    				List<String> lore = new ArrayList<>();
    				for(String str : p.getAfterLore()){			
    					lore.add(ChatColor.translateAlternateColorCodes("&".charAt(0), str));
    					p.setCurrentMaterial(createItem(p.getAfterItem().getType(), p.getAfterName().replaceAll("&", "§"), lore));
    				}
    				
    			} else {
    				List<String> lore = new ArrayList<>();
    				for(String str : p.getBeforeLore()){			
    					lore.add(ChatColor.translateAlternateColorCodes("&".charAt(0), str));
    					p.setCurrentMaterial(createItem(p.getBeforeItem().getType(), p.getBeforeName().replaceAll("&", "§"), lore));
    				}
    			}
    		}
    	}
    }
    
    public String r(String str, Player player, Prize prize){
    	String s = str;
    	
    			s   			
    			.replaceAll("%player%", player.getName())
    			.replaceAll("%days%", ""+prize.getDays())
    			.replaceAll("%hours%", ""+prize.getHours())
    			.replaceAll("%minutes%", ""+prize.getMinutes())
    			.replaceAll("%seconds%", ""+prize.getSeconds());
    		
    	
    	return s;
    }
	
}
