package io.github.richstark.DeliveryMan.Prize.constructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.Prize.Prize;
import io.github.richstark.DeliveryMan.Prize.PrizeManager;

public class PrizeConstructor implements Listener{
	
	private static Main main;
	public PrizeConstructor(Main i){
		main = i;
	}
	
	public void creatorSelectMenu(Player player){
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Select one creator");
		
		ItemStack before = new ItemStack(35, 1, (short)1, (byte)5);
		ItemMeta bm = before.getItemMeta();
		bm.setDisplayName(ChatColor.GREEN + "Before the click prize creator");
		bm.setLore(Arrays.asList(ChatColor.GOLD + "Start Here", ChatColor.GOLD + "Then, After Creator"));
		before.setItemMeta(bm);
		
		ItemStack after = new ItemStack(35, 1, (short)1, (byte)14);
		ItemMeta am = after.getItemMeta();
		am.setDisplayName(ChatColor.RED + "After the click prize creator");
		am.setLore(Arrays.asList(ChatColor.GOLD + "First use Before Creator"));
		after.setItemMeta(am);
		
		ItemStack close = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());	
		SkullMeta cm = (SkullMeta) close.getItemMeta();
		cm.setOwner("MHF_ArrowLeft");
		cm.setDisplayName(ChatColor.RED + "Go Back");
		close.setItemMeta(cm);	
		
		inv.setItem(2, before);
		inv.setItem(4, close);
		inv.setItem(6, after);
		player.openInventory(inv);
	}
	
	public void getBeforeContructor(Player player){
		Inventory inv = Bukkit.createInventory(null, 9*6, "Prize Builder Before Item");
		
		ItemStack help = new ItemStack(Material.NETHER_STAR);
		ItemMeta hm = help.getItemMeta();
		hm.setDisplayName(ChatColor.GREEN + "How to create a new prize");
		hm.setLore(Arrays.asList("", ChatColor.GOLD + "First of all, you need to create", ChatColor.GOLD + "an item, use the command /dm item",
				ChatColor.GOLD + "and the follow the instructions", ChatColor.GOLD + "to create an item",
				ChatColor.GOLD + "then, just drop it here", ChatColor.GOLD + "this will copy the item and the location.", ChatColor.GOLD + "To edit the time, use the config.yml",
				"", ChatColor.AQUA + "YOU CAN LOSE YOUR ITEMS, MAKE A BACKUP" , "", ChatColor.GREEN + "To finish just close the inventory", "", ChatColor.GRAY + "(Right-Click to start)"));
		help.setItemMeta(hm);
		
		inv.setItem(22, help);
		
		player.openInventory(inv);
	}
	
	public Inventory getAfterConstructor(Player player){
		Inventory inv = Bukkit.createInventory(null, 9*6, "Prize Builder After Item");
		
		ItemStack s = new ItemStack(Material.NETHER_STAR);
		ItemMeta ss = s.getItemMeta();
		ss.setDisplayName(ChatColor.GREEN + "Right-Click me to start");
		s.setItemMeta(ss);
		
		inv.setItem(22, s);
		
		return inv;
	}
	
	public void getActionSelection(Player player){
		Inventory inv = Bukkit.createInventory(null, 9*4, ChatColor.RED + "Select one Action");
		
		ItemStack create = new ItemStack(160, 1, (short)1, (byte)5);
		ItemStack delete = new ItemStack(160, 1, (short)1, (byte)14);
		ItemStack edit	 = new ItemStack(160, 1, (short)1, (byte)9);
		ItemStack openDev = new ItemStack(Material.ARMOR_STAND);
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemStack helpItem = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		
		ItemMeta cm = create.getItemMeta();
		cm.setDisplayName(ChatColor.GREEN + "Add a new prize");
		cm.setLore(Arrays.asList(ChatColor.GREEN + "Contents:", "",ChatColor.GOLD+ "- BeforeClick Creator",ChatColor.GOLD + "- AfterClick Creator","",  ChatColor.GRAY + "(Click)"));
		create.setItemMeta(cm);
		
		ItemMeta dm = delete.getItemMeta();
		dm.setDisplayName(ChatColor.RED + "Delete a prize");
		dm.setLore(Arrays.asList(ChatColor.GRAY + "(Click)"));
		delete.setItemMeta(dm);
		
		ItemMeta em = edit.getItemMeta();
		em.setDisplayName(ChatColor.DARK_AQUA + "Edit a prize");
		em.setLore(Arrays.asList(ChatColor.GRAY + "Coming Soon...", "",ChatColor.GRAY + "(Click)"));
		edit.setItemMeta(em);
		
		ItemMeta odm = openDev.getItemMeta();
		odm.setDisplayName(ChatColor.GOLD + "Open deliveryman menu");
		odm.setLore(Arrays.asList(ChatColor.GRAY + "(Click)"));
		openDev.setItemMeta(odm);
		
		ItemMeta Cm = close.getItemMeta();
		Cm.setDisplayName(ChatColor.RED + "Close");
		Cm.setLore(Arrays.asList(ChatColor.GRAY + "(Click)"));
		close.setItemMeta(Cm);
		
		SkullMeta him = (SkullMeta) helpItem.getItemMeta();
		him.setOwner("MHF_Question");
		him.setDisplayName(ChatColor.YELLOW + "Help to create an item");
		him.setLore(Arrays.asList(ChatColor.GRAY + "(Click)"));
		helpItem.setItemMeta(him);
		
		inv.setItem(11, create);
		inv.setItem(13, edit);
		inv.setItem(15, delete);
		inv.setItem(30, openDev);
		inv.setItem(32, close);
		inv.setItem(35, helpItem);
		
		player.openInventory(inv);
	}

	@EventHandler
	public void clickInventorySA(InventoryClickEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase(ChatColor.RED + "Select one Action"))
			return;
		e.setCancelled(true);
		
		Player player = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		if(item.hasItemMeta()){
			if(item.getItemMeta().getDisplayName().contains("Add")){
				player.closeInventory();
				creatorSelectMenu(player);
			} else if(item.getItemMeta().getDisplayName().contains("Edit")){
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA + "This feature is coming soon.");
			} else if(item.getItemMeta().getDisplayName().contains("Delete")){
				player.closeInventory();
				getDeletePrizeInvetory(player);
			} else if(item.getItemMeta().getDisplayName().contains("Close")){
				player.closeInventory();
			} else if(item.getItemMeta().getDisplayName().contains("deliveryman")){
				player.closeInventory();
				main.menu.getMenu(player);
			} else if(item.getItemMeta().getDisplayName().contains("Help to create an item")){
				player.closeInventory();
      		  player.sendMessage(ChatColor.GOLD + "Guide to create an item:");
      		  player.sendMessage("");
      		  player.sendMessage(ChatColor.YELLOW + "First, you need to select an item, playerut the item in your hand");
      		  player.sendMessage(ChatColor.GOLD + "Then, use the command /dm item create");
      		  player.sendMessage(ChatColor.YELLOW + "Here is the usage: /dm item create [name] [lore...]");
      		  player.sendMessage(ChatColor.GOLD + "This is an examplayerle: /dm item create myItem Click_Me! How_are_you?");
      		  player.sendMessage(ChatColor.YELLOW + "You can use this playerlaceholders: %timer% - %playerlayer%");
      		  player.sendMessage(ChatColor.GOLD + "Use \"_\" to create a splayerace");
			}				
		}
		
	}
	
	boolean clickedBefore = false;
	boolean canStartBefore = false;
	
	boolean canStartAfter = false;
	boolean clickedAfter = false;
	boolean itemSel = false;
	
	Prize pf;
	
	@EventHandler
	public void clickInventoryForAfter(InventoryClickEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase("Prize Builder After Item"))
			return;
			
		Player player = (Player)e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("start")){
			e.setCancelled(true);
			if(e.getClick() == ClickType.RIGHT){
				canStartAfter = true;
				player.closeInventory();
				inventoryForAfterAfterNetherStar(player);
			}
		}
		
	}
	
	public void inventoryForAfterAfterNetherStar(Player player){
		Inventory inv = Bukkit.createInventory(null, 9*6, ChatColor.RED + "Select One");
		
		for(Prize prize : PrizeManager.get().getPrizes()){
			List<String> lore = new ArrayList<>();
			for(String str : prize.getBeforeLore()){
				lore.add(ChatColor.translateAlternateColorCodes("&".charAt(0), str));
				prize.setCurrentMaterial(main.getM(prize.getBeforeItem().getType(), prize.getBeforeName().replaceAll("&", "§"), lore));					
			}	
			inv.setItem(prize.getSlot(), prize.getCurrentMaterial());	
		}
		
		player.openInventory(inv);
	}
	
	@EventHandler
	public void interactInventoryForAfterAfterNetherStar(InventoryClickEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase(ChatColor.RED + "Select One"))
			return;
		
		Prize prize = PrizeManager.get().getPrizeBySlot(e.getSlot());
		if(prize != null){				
			e.setCancelled(true);	
			Inventory inv = e.getInventory();
			inv.clear();
			
			inv.setItem(22, main.getM(Material.GOLDEN_APPLE, ChatColor.GOLD + "Drag here [apple] the item", 
					Arrays.asList(ChatColor.GOLD + "you will use after the click of this item:",
							"", ChatColor.AQUA + "ID: " + ChatColor.DARK_AQUA + prize.getId(),
							ChatColor.AQUA + "BeforeClick name: " + ChatColor.DARK_AQUA + prize.getBeforeName())));	
			pf = prize;
		}
		
		if(e.getCurrentItem().getType() == Material.GOLDEN_APPLE && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().contains("[apple]")){
			e.setCancelled(true);
			ItemStack setItem = e.getCursor();
			
			if(setItem.getType() == Material.AIR || setItem == null)
				return;
				
			if(setItem != null){
				List<String> lore = Arrays.asList("");
				
				if(setItem.hasItemMeta() == false || setItem.getItemMeta().hasDisplayName() == false){
					ItemMeta im = setItem.getItemMeta();
					im.setDisplayName(setItem.getType().toString());
					setItem.setItemMeta(im);
				}
				ItemMeta itemMeta = setItem.getItemMeta();
				
				if(itemMeta == null)
					return;
				
				if(itemMeta.getLore() == null || itemMeta.getLore().isEmpty()){
					itemMeta.setLore(lore);
				}
				
				pf.setAfterItem(setItem);
				pf.setAfterName(itemMeta.getDisplayName());
				pf.setAfterLore(itemMeta.getLore());
				
				PrizeManager.get().savePrize(pf.getId());		
			}
			
		}
		
	}
	
	@EventHandler
	public void clickInventoryForSelector(InventoryClickEvent e){		
		if(!e.getInventory().getName().equalsIgnoreCase( ChatColor.RED + "Select one creator"))
			return;
		
		ItemStack item = e.getCurrentItem();
		if(item != null){
			if(item.hasItemMeta()){
				if(item.getItemMeta().getDisplayName().contains("Before")){
					getBeforeContructor((Player)e.getWhoClicked());
				} else if(item.getItemMeta().getDisplayName().contains("After")){
					
					if(PrizeManager.get().getPrizes().isEmpty()){
						e.getWhoClicked().sendMessage(ChatColor.RED + "You don't have prizes created, to create one, use before creator!");
						e.getWhoClicked().closeInventory();
						return;
					}
					
					e.getWhoClicked().openInventory(getAfterConstructor((Player)e.getWhoClicked()));
				} else if(item.getItemMeta().getDisplayName().contains("Back")){
					getActionSelection((Player)e.getWhoClicked());
				}
			}		
		}
		
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void clickInventoryForBefore(InventoryClickEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase("Prize Builder Before Item")){
			return;
		}		
		
		Player player = (Player) e.getWhoClicked();
		
		if(clickedBefore == false){
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
		
			if(e.getCurrentItem().getType() == Material.NETHER_STAR && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.AQUA + "YOU CAN LOSE YOUR ITEMS, MAKE A BACKUP")){
				e.setCancelled(true);
				if(e.getClick() == ClickType.RIGHT){		
					clickedBefore = true;
					canStartBefore = true;
					e.setCurrentItem(new ItemStack(Material.AIR));
				}
			}
		
	}
	
	@EventHandler
	public void closeInventoryForAfter(InventoryCloseEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase("Prize Builder After Item")){
			return;
		}
		Player player = (Player)e.getPlayer();
		
		if(canStartAfter == false){
			player.sendMessage(ChatColor.RED + "You have not added nothing");
			return;
		}
	}
	
	@EventHandler
	public void closeInventoryForBefore(InventoryCloseEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase("Prize Builder Before Item")){
			return;
		}
		
		Player player = (Player) e.getPlayer();
		
		if(canStartBefore == false){
			player.sendMessage(ChatColor.RED + "You have not added nothing");
			return;
		}
		
		for(int it = 0; it <= 53; it++){					
			if(e.getInventory().getItem(it) != null){
				ItemStack item = e.getInventory().getItem(it);		
				List<String> lore = Arrays.asList("");
				
				if(item.hasItemMeta() == false){
					ItemMeta im = item.getItemMeta();
					im.setDisplayName(String.valueOf(item.getType()));
					item.setItemMeta(im);
				}
				ItemMeta itemMeta = item.getItemMeta();
				
				if(itemMeta == null)
					return;
				
				if(itemMeta.getLore() == null || itemMeta.getLore().isEmpty()){
					itemMeta.setLore(lore);
				}
				
				double time1Month = 2.628e+6;
				
				Prize prize = PrizeManager.get().createPrize(itemMeta.getDisplayName(), itemMeta.getLore(), (int)time1Month
						, it, Arrays.asList("Add here your commands"));
				
				prize.setBeforeItem(item);
				prize.setBeforeLore(itemMeta.getLore());
				prize.setBeforeName(itemMeta.getDisplayName());
				PrizeManager.get().savePrize(prize.getId());
				
				player.getInventory().addItem(item);
				
			}	
		}	
		player.sendMessage(ChatColor.GOLD + "Do not forget to edit the commands and the time on config.yml");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/********************
	 * 					*
	 *  Delete Section  *
	 * 					*
	 ********************/
	
	
	public void getDeletePrizeInvetory(Player player){
		Inventory inv = Bukkit.createInventory(null, 9*6, ChatColor.RED + "Delete a Prize");
		
		for(Prize prize : PrizeManager.get().getPrizes()){
			List<String> lore = new ArrayList<>();
			for(String str : prize.getBeforeLore()){
				lore.add(ChatColor.translateAlternateColorCodes("&".charAt(0), str));
				prize.setCurrentMaterial(main.getM(prize.getBeforeItem().getType(), prize.getBeforeName().replaceAll("&", "§"), lore));					
			}	
			inv.setItem(prize.getSlot(), prize.getCurrentMaterial());	
		}
		
		player.openInventory(inv);
	}
	
	@EventHandler
	public void deletePrizeEvent(InventoryClickEvent e){
		if(!e.getInventory().getName().equalsIgnoreCase(ChatColor.RED + "Delete a Prize")){
			return;
		}
		e.setCancelled(true);
		
		Prize prize = PrizeManager.get().getPrizeBySlot(e.getSlot());
		
		if(prize != null){
			prize.delete();
			e.getWhoClicked().closeInventory();
			getDeletePrizeInvetory((Player)e.getWhoClicked());
		}
		
	}
	
	
	
	
}