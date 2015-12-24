package io.github.richstark.DeliveryMan.test;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class NPCPacket implements Listener{
    private EntityPlayer npc;
    
    public void onEn(){
               MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
                WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
                npc = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.fromString("e401550b-f3d7-4e18-b361-5d343c59b207"), "Zealock"), new PlayerInteractManager(nmsWorld));
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
                npc.setLocation(e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ(), 0, 0);
                PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
    }
    
}
