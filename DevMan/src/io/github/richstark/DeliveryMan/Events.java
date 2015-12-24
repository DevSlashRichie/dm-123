package io.github.richstark.DeliveryMan;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.richstark.DeliveryMan.DNPC.ManManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;


public class Events implements Listener{
    
    static Main main;
    
    public Events(Main i){
        main = i;
    }
    
    
    
    @EventHandler
    public void getMenuonClick(NPCRightClickEvent e){
    	if(!ManManager.gMM().getUUIDs().contains(e.getNPC().getUniqueId()))
    		return;
    	
    	Player player = e.getClicker();
    	main.menu.getMenu(player);
    }
    
}
