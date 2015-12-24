package io.github.richstark.DeliveryMan.Timer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitTask;

import io.github.richstark.DeliveryMan.Main;

public class TimersManager {
	
	List<TimerObject> timers = new ArrayList<>();
	
	public List<TimerObject> getTimers(){
		return timers;
	}
	
	private static Main Main;
	public TimersManager(Main i){
		Main = i;
	}
	
	
	private static TimersManager mm;	
    public static TimersManager get(){
        if(mm == null)
            mm = new TimersManager(Main);
        
        return mm;
    }
	
	public TimerObject getTimer(String id){
		for(TimerObject to : timers){
			if(to.getId().equals(id)){
				return to;
			}
		}
		return null;
	}
	
	public TimerObject createTimer(String id, BukkitTask runnable){		
		TimerObject to = new TimerObject(id, runnable);
		timers.add(to);
		return to;
	}
	
	public void cancelAll(){
		for(TimerObject to : timers){
			to.cancel();
		}
	}
	
	
}
