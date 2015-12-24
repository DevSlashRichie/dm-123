package io.github.richstark.DeliveryMan.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.richstark.DeliveryMan.Main;
import io.github.richstark.DeliveryMan.Timer.TimersManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ParticlesClass {
	
	static Main man;
	public ParticlesClass(Main i){
		man = i;
	}
	
	ParticlesClass mm;
	
	boolean cancel = false;
	
	public void createHelixTree(Location location, String manId){
		final Location loc = location;
		 
		
		TimersManager.get().createTimer("ParticlesTimer." + manId + ".1", 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
                double radius = 2;
                
                for (double y = 3; y >= 0; y -= 0.007) {
                    radius = y / 3;
                    double x = radius * Math.cos(3 * y);
                    double z = radius * Math.sin(3 * y);
               
                    double y2 = 3 - y;
             
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.PORTAL, true,(float) (loc.getX() + x), (float) (loc.getY() + y2), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                    for(Player o : Bukkit.getOnlinePlayers())
                    	((CraftPlayer)o).getHandle().playerConnection.sendPacket(packet);
                }
           
                for (double y = 3; y >= 0; y -= 0.007) {
                    radius = y / 3;
                    double x = -(radius * Math.cos(3 * y));
                    double z = -(radius * Math.sin(3 * y));
               
                    double y2 = 3 - y;
               
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.PORTAL, true,(float) (loc.getX() + x), (float) (loc.getY() + y2), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                    for(Player o : Bukkit.getOnlinePlayers())
                    	((CraftPlayer)o).getHandle().playerConnection.sendPacket(packet);
                }
				
			}
		}.runTaskTimer(man, 0, 20));
		
		TimersManager.get().createTimer("ParticlesTimer." + manId + ".2", new BukkitRunnable() {
			
			@Override
			public void run() {
				  PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,(float) (loc.getX()), (float) (loc.getY() + 4), (float) (loc.getZ()), (float)0.0, (float)0.0, (float)0.0, 0, 5);
	              for(Player o : Bukkit.getOnlinePlayers())		
	            	  ((CraftPlayer)o).getHandle().playerConnection.sendPacket(packet);		
			}
		}.runTaskTimer(man, 0, 3));
	}
	
	public void createEnch(Location location, String manId){
		final Location loc = location;
		TimersManager.get().createTimer("ParticlesTimer." + manId + ".3", 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				  PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.ENCHANTMENT_TABLE, true,(float) (loc.getX()), (float) (loc.getY()), (float) (loc.getZ()), (float)0.0, (float)0.0, (float)0.0, 2, 50);
	              for(Player o : Bukkit.getOnlinePlayers())		
	            	  ((CraftPlayer)o).getHandle().playerConnection.sendPacket(packet);				
			}
		}.runTaskTimer(man, 0, 3));
	}
	
}
	
