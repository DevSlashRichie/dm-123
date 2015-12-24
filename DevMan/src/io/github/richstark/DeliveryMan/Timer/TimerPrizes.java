package io.github.richstark.DeliveryMan.Timer;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimerPrizes {
	
	private int time;
	private String id;
	private int currentTime;
	private String formatTime;
	
	public TimerPrizes(int time, int currentTime,String id, String formatTime){
		this.time = time;
		this.id = id;
		this.currentTime = currentTime;
		this.formatTime = formatTime;
	}
	
	public String getId(){
		return id;
	}
	
	public int getStaticTime(){
		return time;
	}
	
	public int getCurrentTimeSeconds(){
		return currentTime;
	}
	
	public String getFormattedTime(){
		return formatTime;
	}
	
	public void updateFormatTime(String time){
		formatTime = time;
	}
		
	public void updateTime(int time){
		currentTime = time;
	}
	
	public void delete(){
		TimerPrizesManager.get().timers.remove(this);
	}
	
	
}
