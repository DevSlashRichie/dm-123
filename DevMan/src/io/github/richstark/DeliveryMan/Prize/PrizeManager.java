package io.github.richstark.DeliveryMan.Prize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.Timer.TimerPrizesManager;
import net.md_5.bungee.api.ChatColor;

public class PrizeManager {

	ArrayList<Prize> prizes = new ArrayList<>();
	
	int prizesCant = 0;
	
	static Main main;
	public PrizeManager(Main i){
		main = i;
	}
	
	private static PrizeManager prizemanager;
		
	public static PrizeManager get(){
		if(prizemanager == null)
			prizemanager = new PrizeManager(main);
		return prizemanager;
	}
	
	public Prize getPrize(int id){
		for(Prize prize : prizes){
			if(prize.getId() == id){
				return prize;
			}
		}
		return null;
	}
	
	public Prize getPrizeBySlot(int slot){
		for(Prize p : prizes){
			if(p.getSlot() == slot){
				return p;
			}
		}
		return null;
	}
	
	public Prize createPrize(String displayName, List<String> lore, int time, int slot, List<String> cmds){
		if(getPrizeBySlot(slot) != null){
			return null;
		}
		
		prizesCant++;
		
		Prize prize = new Prize(prizesCant, ChatColor.GREEN + displayName, ChatColor.RED + displayName, lore, Arrays.asList(ChatColor.GRAY + "Wait: %timer%"), time, 
				TimerPrizesManager.get().createTimer("timer-" + prizesCant, time),
				slot, cmds, null);
		prize.setCurrentMaterial(prize.getBeforeItem());
		
		FileConfiguration config = main.getConfig();
		
		config.set("Prizes-prizesCount", prizesCant);
		
		config.set("Prizes.prize-" + prizesCant + ".slot", prize.getSlot());
		
		config.set("Prizes.prize-" + prizesCant + ".afterClickName", prize.getAfterName());
		config.set("Prizes.prize-" + prizesCant + ".beforeClickName", prize.getBeforeName());
		config.set("Prizes.prize-" + prizesCant + ".beforeClickLore", prize.getBeforeLore());
		config.set("Prizes.prize-" + prizesCant + ".afterClickLore", prize.getAfterLore());
		
		config.set("Prizes.prize-" + prizesCant + ".commands", prize.getCommands());
		
		config.set("Prizes.prize-" + prizesCant + ".itemBeforeClick", prize.getBeforeItem().getTypeId());
		config.set("Prizes.prize-" + prizesCant + ".itemAfterClick", prize.getAfterItem().getTypeId());
		
		config.set("Prizes.prize-" + prizesCant + ".time", "1:0:0:0");		
		main.saveConfig();
		
		
		main.saC.set("PrizesTimers.prize-" + prizesCant, prize.getTimer().getCurrentTimeSeconds());
		main.saC.set("PrizesClicked.prize-" + prizesCant, prize.getWhoClicked());
		main.saveSaConfig();
		
		prizes.add(prize);
		
		
		return prize;
	}
	
	public Prize createPrizeForConfig(String displayName, List<String> lore, int time, int slot, List<String> cmds){
		prizesCant++;
		
		Prize prize = new Prize(prizesCant, ChatColor.GREEN + displayName, ChatColor.RED + displayName, lore, Arrays.asList(ChatColor.GRAY + "Wait: %timer%"), time, 
				TimerPrizesManager.get().createTimer("timer-" + prizesCant, time),
				slot, cmds, new ItemStack(Material.ENDER_CHEST));
		
		prizes.add(prize);
		
		
		return prize;
	}
	
	public void savePrize(int id){
		Prize prize = getPrize(id);
		
		if(prize != null){
			FileConfiguration config = main.getConfig();
			
			config.set("Prizes.prize-" + id + ".slot", prize.getSlot());
			
			config.set("Prizes.prize-" + id + ".afterClickName", prize.getAfterName());
			config.set("Prizes.prize-" + id + ".beforeClickName", prize.getBeforeName());
			config.set("Prizes.prize-" + id + ".afterClickLore", prize.getAfterLore());
			config.set("Prizes.prize-" + id + ".beforeClickLore", prize.getBeforeLore());			
			
			config.set("Prizes.prize-" + id + ".commands", prize.getCommands());
			
			config.set("Prizes.prize-" + id + ".itemBeforeClick", prize.getBeforeItem().getTypeId());
			config.set("Prizes.prize-" + id + ".itemAfterClick", prize.getAfterItem().getTypeId());
			
			config.set("Prizes.prize-" + id + ".time", "1:0:0:0");		
			main.saveConfig();
		}
		
	}

	
	public ArrayList<Prize> getPrizes(){
		return prizes;
	}
	
	public int getPrizesCount(){
		return prizesCant;
	}
	
	public void removeFromConfig(int id){
		FileConfiguration config = main.getConfig();	
		config.set("Prizes.prize-" + id, null);
		main.saveConfig();
	}
	
}
