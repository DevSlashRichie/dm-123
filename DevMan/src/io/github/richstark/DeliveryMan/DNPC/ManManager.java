package io.github.richstark.DeliveryMan.DNPC;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.Timer.TimersManager;
import io.github.richstark.DeliveryMan.particles.ParticlesClass;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.util.NMS;
 

public class ManManager{
    
    private final List<Man> mans = new ArrayList<>();
    private final List<UUID> uds = new ArrayList<>();
    public int manSize = 0;
    
    private NPC npc;
    private ArmorStand stand;
    private ArmorStand stand2;
    private ArmorStand nameStand;
    
    
    private static ManManager mm;
    private static Main Main;
    
    public ManManager (Main i) {
        Main = i;
    }
    
    public static ManManager gMM(){
        if(mm == null)
            mm = new ManManager(Main);
        
        return mm;
    }
    
    ParticlesClass pc = new ParticlesClass(Main);
    
    public Man getMan(String id){
        for(Man m : this.mans){
            if(m.getId().equals(id)){
                return m;
            }
        }
        return null;
    }
    
    public Man spawnMan(Location loc, String name, String visibleName){
        manSize++;
        
        FileConfiguration config = Main.getlocC();
        
        config.set("NPCs." + name + ".Location.X", loc.getX());
        config.set("NPCs." + name + ".Location.Y", loc.getBlockY());
        config.set("NPCs." + name + ".Location.Z", loc.getZ());
        config.set("NPCs." + name + ".Location.PT", loc.getPitch());
        config.set("NPCs." + name + ".Location.YW", loc.getYaw());
        config.set("NPCs." + name + ".Location.WORLD", loc.getWorld().getName());
        
        Main.saveLocConfig();        
        
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "");
        npc.setFlyable(true);
        npc.spawn(loc);
        
        SkinnableEntity skin = NMS.getSkinnable(npc.getEntity());
        if(skin != null){
        	skin.setSkinName(Main.skinN4ME);
        }

        nameStand = loc.getWorld().spawn(loc, ArmorStand.class);
        nameStand.setBasePlate(false);
        nameStand.setCustomName(ChatColor.translateAlternateColorCodes("&".charAt(0), visibleName));
        nameStand.setCustomNameVisible(true);
        nameStand.setVisible(false);
        nameStand.setGravity(false);
        nameStand.setCanPickupItems(false);
        
        stand = loc.add(0,0.3,0).getWorld().spawn(loc, ArmorStand.class);
        stand.setBasePlate(false);
        stand.setCustomName(ChatColor.translateAlternateColorCodes("&".charAt(0), Main.line2));
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setCanPickupItems(false);
        
        stand2 = loc.add(0,0.2,0).getWorld().spawn(loc, ArmorStand.class);
        stand2.setBasePlate(false);
        stand2.setCustomName(ChatColor.translateAlternateColorCodes("&".charAt(0), Main.line1));
        stand2.setCustomNameVisible(true);
        stand2.setVisible(false);
        stand2.setGravity(false); 
        stand2.setCanPickupItems(false);
        
        uds.add(npc.getUniqueId());
        
        iniParticles(loc, name);
        Man m = new Man(loc, name, npc, stand, stand2, nameStand); 
        this.mans.add(m);
        return m;
    }
    
    public void despawnAll(){
        for(Man m : mans){
            m.despawn();
        }     
        
        //Particles
        TimersManager.get().cancelAll();
        
        mans.clear();
        manSize = 0;
        Main.saveSaConfig();
    }
    
    public List<Man> getAllMans(){
        return mans;
    }
    
    public Location getCompleteLocation(String name){
        FileConfiguration ca = Main.getlocC();
        
        double x = Double.parseDouble(ca.getString("NPCs." + name + ".Location.X"));
        double y = Double.parseDouble(ca.getString("NPCs." + name + ".Location.Y"));
        double z = Double.parseDouble(ca.getString("NPCs." + name + ".Location.Z"));
        
        float pt = Float.parseFloat(ca.getString("NPCs." + name + ".Location.PT"));
        float yw = Float.parseFloat(ca.getString("NPCs." + name + ".Location.YW"));
        
        World world = Main.getServer().getWorld(ca.getString("NPCs." + name + ".Location.WORLD"));
        
       return new Location(world, x, y, z, yw, pt);
       
    }
    
    public List<UUID> getUUIDs(){
        return uds;
    }
    
    private void iniParticles(Location loc, String manId){
    	
    	if(Main.particlesType.equalsIgnoreCase("TREE")){
    		pc.createHelixTree(loc, manId);
    	} else if(Main.particlesType.equalsIgnoreCase("ENCHANTMENT_EFFECT")){
    		pc.createEnch(loc, manId);
    	} else {
    		System.out.println("------------------------------------------------------------");
    		System.out.println("|[DeliveryMan-Particles]ERROR: That particle doesn't exist!|");
    		System.out.println("------------------------------------------------------------");
    	}
    	
    }
       
    
}

