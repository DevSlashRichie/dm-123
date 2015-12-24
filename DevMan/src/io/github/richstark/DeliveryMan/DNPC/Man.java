package io.github.richstark.DeliveryMan.DNPC;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import io.github.richstark.DeliveryMan.Timer.TimerObject;
import io.github.richstark.DeliveryMan.Timer.TimersManager;
import net.citizensnpcs.api.npc.NPC;


public class Man {

    private final Location loc;
    
    private final String id;
    
    private final NPC npc;
    private final ArmorStand stand;
    private final ArmorStand stand2;
    private final ArmorStand name;
    
    public Man(Location location, String id, NPC npc, ArmorStand stand, ArmorStand stand2, ArmorStand name){
        loc = location;
        this.id = id;
        this.npc = npc;
        this.stand = stand;
        this.stand2 = stand2;
        this.name = name;
    }
    
    public Location getLocation(){
        return loc;
    }
    
    public String getId(){
        return id;
    }
    
    public void despawn(){
        npc.destroy();
        stand.remove();
        stand2.remove();
        name.remove();
        
        TimerObject a = TimersManager.get().getTimer("ParticlesTimer." + id + ".1");
        TimerObject b = TimersManager.get().getTimer("ParticlesTimer." + id + ".2");
        TimerObject c = TimersManager.get().getTimer("ParticlesTimer." + id + ".3");
        
        if(a != null){
        	a.cancel();
        }
        
        if(b != null){
        	b.cancel();
        }
        
        if(c != null){
        	c.cancel();
        }
        
        ManManager.gMM().getAllMans().remove(id);
    }
    
    public void teleportTo(Location loc){
        npc.teleport(loc,TeleportCause.PLUGIN);
        stand.teleport(loc);
        stand2.teleport(loc);
        name.teleport(loc);
    }
    
    public ArrayList<ArmorStand> getStands(){
        ArrayList<ArmorStand> s = new ArrayList<>();
        
        s.add(stand);
        s.add(stand2);
        s.add(name);
        
        return s;
    }
    
    
}
