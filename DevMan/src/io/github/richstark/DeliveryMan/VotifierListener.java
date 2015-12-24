package io.github.richstark.DeliveryMan;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


public class VotifierListener implements Listener{
    
    static Main main;
    public VotifierListener(Main i){
        main = i;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void VotifierEvt(VotifierEvent e){
        Vote vote = e.getVote();
        Player player = Bukkit.getPlayer(e.getVote().getUsername());
        
        if(vote.getAddress() == null || vote.getAddress().equals("")){
            Main.log.log(Level.WARNING, "[DeliveryMan - Votifier] No address from {0}", vote.getServiceName());
        }
        
        if(vote.getUsername() == null || vote.getUsername().equals("")){
            Main.log.log(Level.WARNING, "[DeliveryMan - Votifier] No username from {0}", vote.getServiceName());
        }
        
        if(player == null){
            main.votiferPlayerOff(vote);
        } else {
            main.votifierPlayerOn(vote);
        }
        
    }
    
    
    
}
