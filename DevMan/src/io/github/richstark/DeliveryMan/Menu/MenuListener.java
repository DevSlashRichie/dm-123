package io.github.richstark.DeliveryMan.Menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.sql;
import io.github.richstark.DeliveryMan.Prize.Prize;
import io.github.richstark.DeliveryMan.Prize.PrizeManager;

public class MenuListener implements Listener{
	
	static Main main;
	public MenuListener(Main m){
		main = m;
	}
	
	sql sql = new sql(main);
	
	@EventHandler
	public void MenuClick(InventoryClickEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase(c(main.NPCName)))
			return;
			
		e.setCancelled(true);
		Player player = (Player) e.getWhoClicked();
		String uuid = player.getUniqueId().toString();
			
			for(Prize p : PrizeManager.get().getPrizes()){
				if(e.getSlot() == p.getSlot()){
					if(player.hasPermission("deliveryman.prize." + p.getId())){
						if(p.getWhoClicked().contains(uuid)){
							player.sendMessage(ChatColor.RED + "You already claimed this prize.");
							return;
						}
						
						p.addWhoClicked(player.getUniqueId());
						for(String cmds : p.getCommands()){
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmds.replaceAll("%player%", player.getName()));
						}
						
					} else {
						player.closeInventory();
						player.sendMessage(ChatColor.RED + "No Permission!");
						player.getLocation().getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 10, 1);	
					}
				}
			}
			
		}   
	
	private String c(String msg){
		return ChatColor.translateAlternateColorCodes("&".charAt(0), msg);
	}
	
}
