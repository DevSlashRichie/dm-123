package io.github.richstark.DeliveryMan.Timer;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.Prize.PrizeManager;

public class TimerPrizesManager {
	
	List<TimerPrizes> timers = new ArrayList<>();
	 
	private static Main main;
	public TimerPrizesManager(Main i){
		main = i;
	}
	
	private static TimerPrizesManager mm;	
    public static TimerPrizesManager get(){
        if(mm == null)
            mm = new TimerPrizesManager(main);
        
        return mm;
    }
	
	public TimerPrizes getTimerObject(String id){
		for(TimerPrizes tp : timers){
			if(tp.getId().equals(id)){
				return tp;
			}
		}
		return null;
	}
	
	public TimerPrizes createTimer(final String id, final int time){
		
		final TimerPrizes tp = new TimerPrizes(time, time, id, "");
		
		new BukkitRunnable() {
			int interval = time;
			int t = time;
			@Override
			public void run() {			
				if(!timers.contains(tp)){
					this.cancel();
				}				
				String[] dd = tp.getId().split("-");
				Integer d = Integer.parseInt(dd[1]);
				interval--;
				if(interval == -1){
					interval = t;
					PrizeManager.get().getPrize(d).getWhoClicked().clear();
				}
				tp.updateTime(interval);
			}
		}.runTaskTimer(main, 20, 20);
		timers.add(tp);
		
		return tp;	
	}
	
}
