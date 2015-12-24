package io.github.richstark.DeliveryMan;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {
    
    public static void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
  {
      for(Player player : Bukkit.getOnlinePlayers()){
    CraftPlayer craftplayer = (CraftPlayer)player;
    PlayerConnection connection = craftplayer.getHandle().playerConnection;
    IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + ChatColor.translateAlternateColorCodes('&', title) + "'}");
    IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + ChatColor.translateAlternateColorCodes('&', subtitle) + "'}");
    Packet length = new PacketPlayOutTitle(EnumTitleAction.TIMES, titleJSON, fadeIn, stay, fadeOut);
    Packet titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
    Packet subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON, fadeIn, stay, fadeOut);
    connection.sendPacket(titlePacket);
    connection.sendPacket(length);
    connection.sendPacket(subtitlePacket);
      }
  }
    
    public static void sendTitleToPlayer(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut){
        
        CraftPlayer craftplayer = (CraftPlayer)player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + ChatColor.translateAlternateColorCodes('&', title) + "'}");
        IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + ChatColor.translateAlternateColorCodes('&', subtitle) + "'}");
        Packet length = new PacketPlayOutTitle(EnumTitleAction.TIMES, titleJSON, fadeIn, stay, fadeOut);
        Packet titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        Packet subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON, fadeIn, stay, fadeOut);
        connection.sendPacket(titlePacket);
        connection.sendPacket(length);
        connection.sendPacket(subtitlePacket);
        
    }
    
}
