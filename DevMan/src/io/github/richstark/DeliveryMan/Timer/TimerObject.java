package io.github.richstark.DeliveryMan.Timer;

import org.bukkit.scheduler.BukkitTask;

public class TimerObject {

	private final String id;
	private final BukkitTask runnable;
	
	public TimerObject(String id, BukkitTask runnable){
		this.id = id;
		this.runnable = runnable;
	}
	
	public String getId(){
		return id;
	}
	
	public int getTaskId(){
		return runnable.getTaskId();
	}
	
	public void cancel(){
		runnable.cancel();
		TimersManager.get().getTimers().remove(id);
	}
	
}
