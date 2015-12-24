package io.github.richstark.DeliveryMan.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.richstark.DeliveryMan.Timer.TimerPrizes;
import io.github.richstark.DeliveryMan.Timer.TimerPrizesManager;

public class Prize {
	
	private int ID;
	
	private int time;
	private TimerPrizes timer;
	
	private int slot;
	private List<String> commands;
	
	private ItemStack currentItem;
	
	private List<String> uuids = new ArrayList<>();
	
	private ItemStack beforeItem = new ItemStack(Material.ENDER_CHEST);
	private ItemStack afterItem = new ItemStack(Material.CHEST);
	
	private String beforeName;
	private String afterName;
	
	private List<String> beforeLore;
	private List<String> afterLore;
	
	public Prize(int ID, String beforeName, String afterName, List<String> beforeLore, List<String> afterLore, int time, TimerPrizes timer, int slot, List<String> commands, ItemStack currentItem){
		
		this.ID = ID;
		
		this.currentItem = currentItem;
		this.beforeName = beforeName;
		this.afterName = afterName;
		
		this.beforeLore = beforeLore;
		this.afterLore = afterLore;
		
		this.timer = timer;
		this.time = time;
		
		this.slot = slot;
		this.commands = commands;
		
	}
	
	public int getId(){
		return ID;
	}
	
	public String getBeforeName(){
		return beforeName;
	}
	
	public String getAfterName(){
		return afterName;
	}
	
	public List<String> getBeforeLore(){
		return beforeLore;
	}
	
	public List<String> getAfterLore(){
		return afterLore;
	}
	
	public TimerPrizes getTimer(){
		return timer;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getSlot(){
		return slot;
	}
	
	public List<String> getCommands(){
		return commands;
	}
	
	public List<String> getWhoClicked(){
		return uuids;
	}
	
	public ItemStack getCurrentMaterial(){
		return currentItem;
	}
	
	public ItemStack getBeforeItem(){
		return beforeItem;
	}
	
	public ItemStack getAfterItem(){
		return afterItem;
	}
	
	public void setBeforeItem(ItemStack bi){
		beforeItem= bi;
	}
	
	public void setAfterItem(ItemStack ti){
		afterItem = ti;
	}
	
	public void setCurrentMaterial(ItemStack item){
		currentItem = item;
	}
	
	public void addWhoClicked(UUID uuid){
		uuids.add(uuid.toString());
	}
	
	public void removeWhoClicked(UUID uuid){
		uuids.remove(uuid.toString());
	}
	
	public void clearWhoClicked(){
		uuids.clear();
	}
	
	public void setBeforeName(String n){
		beforeName = n;
	}
	
	public void setAfterName(String n){
		afterName = n;
	}
	
	public void setBeforeLore(List<String> l){
		beforeLore = l;
	}
	
	public void setAfterLore(List<String> l){
		afterLore = l;
	}
	
	public void delete(){
		TimerPrizesManager.get().getTimerObject("timer-" + ID).delete();
		PrizeManager.get().removeFromConfig(ID);
		PrizeManager.get().getPrizes().remove(this);
	}
	
	public int getDays(){
		int seconds = timer.getCurrentTimeSeconds();		
        double numdays 	  = Math.floor(seconds / 86400);
        return (int)numdays;
	}
	
	public int getHours(){
		int seconds = timer.getCurrentTimeSeconds();
		double numhours = Math.floor((seconds % 86400) / 3600);
		return (int)numhours;
	}
	
	public int getMinutes(){
		int seconds = timer.getCurrentTimeSeconds();
		double numminutes = Math.floor(((seconds % 86400) % 3600) / 60);
		return (int)numminutes;
	}
	
	public int getSeconds(){
		int seconds = timer.getCurrentTimeSeconds();
		double numseconds = ((seconds % 86400) % 3600) % 60;
		return (int)numseconds;
	}
	
}
