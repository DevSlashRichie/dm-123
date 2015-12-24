package io.github.richstark.DeliveryMan;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;
import com.vexsoftware.votifier.model.Vote;

import io.github.richstark.DeliveryMan.DNPC.Man;
import io.github.richstark.DeliveryMan.DNPC.ManManager;
import io.github.richstark.DeliveryMan.Menu.Menu;
import io.github.richstark.DeliveryMan.Menu.MenuListener;
import io.github.richstark.DeliveryMan.Prize.Prize;
import io.github.richstark.DeliveryMan.Prize.PrizeManager;
import io.github.richstark.DeliveryMan.Prize.constructor.PrizeConstructor;
import io.github.richstark.DeliveryMan.Timer.TimerPrizesManager;
import io.github.richstark.DeliveryMan.particles.ParticlesClass;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{ 
    //UPDATE 1
    //Logger
    public static final Logger log = Logger.getLogger("Minecraft");    
    
    //Configuration Variables
    File locFile;
    public FileConfiguration locC;
    
    File saFile;
    public FileConfiguration saC;
    
    //Classes Declaration
    ManManager mm = new ManManager(this);
    sql sql = new sql(this);
    ParticlesClass pc = new ParticlesClass(this);
    PrizeManager pm = new PrizeManager(this);
    public Menu menu = new Menu(this);
    TimerPrizesManager tpm = new TimerPrizesManager(this);
    PrizeConstructor prizeC = new PrizeConstructor(this);

    public String NPCName;
    public String line1;
    public  String line2;
    
    public String sqlHost;
    public String sqlUser;
    public String sqlPass;
    public String sqlDB;
    public boolean sqlActive;
    
    String joinMessageYes;
    String joinMessageNo;
    
    public boolean VotifierActive;
    public List<String> VotifierCommands = new ArrayList<>();
    public List<String> VotifierServices = new ArrayList<>();
    public String VotifierMessage;
    public int VotifierCoins;
    
    public String voteItemName;
    public  int voteItemSlot;
    public List<String> voteItemLore = new ArrayList<>();
    
    public boolean particlesActive;
    public String particlesType;
    
    public String skinN4ME; 
    
    @Override
    public void onEnable(){
    	
        getServer().getPluginManager().registerEvents(this, this);    
        getServer().getPluginManager().registerEvents(new Events(this), this);    
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new PrizeConstructor(this), this);
        
        locFile = new File("plugins/DeliveryMan/saves/locations.yml");
        locC = YamlConfiguration.loadConfiguration(locFile);
        
        saFile = new File("plugins/DeliveryMan/saves/dataSaves.yml");
        saC = YamlConfiguration.loadConfiguration(saFile);
        
        getUserConfig();               
        startSQL();
        
        if(VotifierActive == true){
            Bukkit.getPluginManager().registerEvents(new VotifierListener(this), this);  
        }
        
        if(locC.contains("NPCs") && !locC.getConfigurationSection("NPCs").getKeys(true).contains("")){
        try {
            for (String key : locC.getConfigurationSection("NPCs").getKeys(false)) {
                Location loc = ManManager.gMM().getCompleteLocation(key);
                ManManager.gMM().spawnMan(loc, key, NPCName); 
            } 
        } catch(Exception e){
            log.log(Level.WARNING, e.getMessage());
            for (String key : locC.getConfigurationSection("NPCs").getKeys(false)) {
                Location loc = ManManager.gMM().getCompleteLocation(key);
                ManManager.gMM().spawnMan(loc, key, NPCName); 
            }
        } finally {
            log.log(Level.INFO, "[DeliveryMan] Load Finished!");
        }           
        } 
        
        if(getConfig().contains("Prizes")){
        	for(String key : getConfig().getConfigurationSection("Prizes").getKeys(false)){
        		String beforeName = getConfig().getString("Prizes." + key + ".beforeClickName");
        		String afterName = getConfig().getString("Prizes." + key + ".afterClickName");
        		
        		List<String> beforeLore = getConfig().getStringList("Prizes." + key + ".beforeClickLore");
        		List<String> afterLore = getConfig().getStringList("Prizes." + key + ".afterClickLore");       		
        		
        		
        		String timerConfig = getConfig().getString("Prizes." + key + ".time");
        		int days = Integer.parseInt(timerConfig.split(":")[0]);
        		int hours = Integer.parseInt(timerConfig.split(":")[1]);
        		int minutes = Integer.parseInt(timerConfig.split(":")[2]);
        		int seconds = Integer.parseInt(timerConfig.split(":")[3]);
        		
        		int time = (days * 86400) + (hours * 3600) + (minutes * 60) + seconds;
        		
        		int slot = getConfig().getInt("Prizes." + key + ".slot");
        		List<String> cmds = getConfig().getStringList("Prizes." + key + ".commands");
        		
        		int afterItem = getConfig().getInt("Prizes." + key + ".itemAfterClick");
        		int beforeItem = getConfig().getInt("Prizes." + key + ".itemBeforeClick");
        		       		
        		Prize prize = PrizeManager.get().createPrizeForConfig(beforeName, beforeLore, time, slot, cmds);
        		prize.setAfterLore(afterLore);
        		prize.setBeforeLore(beforeLore);
        		prize.setBeforeName(beforeName);
        		prize.setAfterName(afterName);
        		prize.setAfterItem(new ItemStack(afterItem));
        		prize.setBeforeItem(new ItemStack(beforeItem));
        		PrizeManager.get().savePrize(prize.getId());
        	}
        }
        
        setupEconomy();
    }
    
    @Override
    public void onDisable(){
        ManManager.gMM().despawnAll();        
        saveSaConfig();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        
        if(label.equalsIgnoreCase("dm") || label.equalsIgnoreCase("deliveryman")){
            
            if(args.length == 0){
                
                sender.sendMessage(ChatColor.RED + "---[ " + ChatColor.GREEN + "DeliveryMan" + ChatColor.RED + " ]---");
                sender.sendMessage("");
                
                sender.sendMessage(ChatColor.RED + "Author: " + ChatColor.GREEN + "RichStark");
                sender.sendMessage(ChatColor.RED + "Plugin's Page: " + ChatColor.GREEN + "http://bit.ly/DeliveryManPlugin");
                sender.sendMessage(ChatColor.RED + "Version: " + ChatColor.GREEN + "3.0.0");
                sender.sendMessage(ChatColor.RED + "Do " + ChatColor.GREEN + "/" + label + "  help" + ChatColor.RED + " to show help");
                
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED + "--------------------");                
                
            } else if(args[0].equalsIgnoreCase("help")){
                sender.sendMessage(ChatColor.RED + "---[ " + ChatColor.GREEN + "DeliveryMan" + ChatColor.RED + " ]---");
                sender.sendMessage("");
                
                sender.sendMessage(ChatColor.RED + "/" + label + " create [name] " + ChatColor.GREEN + "To create a new man");
                sender.sendMessage(ChatColor.RED + "/" + label + " delete [name] " + ChatColor.GREEN + "To delete a man");
                sender.sendMessage(ChatColor.RED + "/" + label + " deleteall " + ChatColor.GREEN + "To delete all men");
                sender.sendMessage(ChatColor.RED + "/" + label + " list " + ChatColor.GREEN + "Show you the available men");
                sender.sendMessage(ChatColor.RED + "/" + label + " reload " + ChatColor.GREEN + "To reload men");
                sender.sendMessage(ChatColor.RED + "/" + label + " reset [player] " + ChatColor.GREEN + "Resets the time of the countdown");
                sender.sendMessage(ChatColor.RED + "/" + label + " openRemote " + ChatColor.GREEN + "Opens the \"prize center\" to the player who executed the command");
                sender.sendMessage(ChatColor.RED + "/" + label + " prize " + ChatColor.GREEN + "Opens the options for the prizes");
                sender.sendMessage(ChatColor.RED + "/" + label + " item " + ChatColor.GREEN + "");
                
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED + "--------------------");               
            } else if(args[0].equalsIgnoreCase("create")){
                if(p.hasPermission("deliveryman.create")){
                if(args.length <= 1){
                    p.sendMessage(ChatColor.RED + "Usage: /" + label + " create [name]");
                    return true;
                }
                
                if(locC.contains("NPCs." + args[1])){
                    p.sendMessage(ChatColor.RED + "That DeliveryMan Already exists!");
                    return true;
                }
                
                ManManager.gMM().spawnMan(p.getLocation(), args[1], NPCName);
                } 
            } else if(args[0].equalsIgnoreCase("deleteall")){
                if(p.hasPermission("deliveryman.deleteall")){
                ManManager.gMM().despawnAll();
                locC.set("NPCs", "");
                         
                p.sendMessage(ChatColor.GREEN + "You have deleted all DeliveryMen!");
                
                saveLocConfig();
                }
            } else if(args[0].equalsIgnoreCase("delete")){               
                if(p.hasPermission("deliveryman.delete")){
                if(args.length <= 1){
                    p.sendMessage(ChatColor.RED + "Usage: /" + label + " delete [name]");
                    return true;
                }
                
                if(!locC.contains("NPCs." + args[1])){
                    p.sendMessage(ChatColor.RED + "That VoteDeliver doesn't exists");
                    return true;
                }
                
                ManManager.gMM().manSize--;
                locC.set("count", ManManager.gMM().manSize);
                
                locC.getConfigurationSection("NPCs").set(args[1], null);
                ManManager.gMM().getMan(args[1]).despawn();                             
                
                p.sendMessage(ChatColor.GREEN + "You have deleted the DeliveryMan: " + args[1] + " !");
                saveLocConfig();
                }
            } else if(args[0].equalsIgnoreCase("list")){
                if(p.hasPermission("deliveryman.list")){
                p.sendMessage(ChatColor.RED + "This are the active DeliveryMen:");
                for(Man m : ManManager.gMM().getAllMans()){
                    
                    if(ManManager.gMM().getAllMans().isEmpty() == false){
                        p.sendMessage(ChatColor.RED + "ID: " + ChatColor.GREEN + m.getId());
                    } else {
                    p.sendMessage(ChatColor.GREEN + "You don't any DeliveryMan Active!");
                    }
                }
                }
            } else if(args[0].equalsIgnoreCase("reload")){
                if(p.hasPermission("deliveryman.reload")){
                      
                	getUserConfig();
                	reloadConfig();
                	
        if(!locC.getConfigurationSection("NPCs").getKeys(true).contains("")){
            ManManager.gMM().despawnAll();
        try {
            for (String key : locC.getConfigurationSection("NPCs").getKeys(false)) {
                Location loc = ManManager.gMM().getCompleteLocation(key);
                ManManager.gMM().spawnMan(loc, key, NPCName); 
            } 
        } catch(Exception e){
            log.log(Level.WARNING, e.getMessage());
            for (String key : locC.getConfigurationSection("NPCs").getKeys(false)) {
                Location loc = ManManager.gMM().getCompleteLocation(key);
                ManManager.gMM().spawnMan(loc, key, NPCName); 
            }
        } finally {
            log.log(Level.INFO, "[DeliveryMan] Load Finished!");
        }           
                        sender.sendMessage(ChatColor.GREEN + "You have deleted all DeliveryMen!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have any DeliveryMen active!");
                    }
                
            }
          } else if(args[0].equalsIgnoreCase("reset")){
              if(p.hasPermission("deliveryman.reset")){
              if(args.length <= 1){
                  p.sendMessage(ChatColor.RED + "To low arguments!");
                  return true;
              }
              
              for(Prize pr : PrizeManager.get().getPrizes()){
            	  pr.getWhoClicked().remove(p.getUniqueId().toString());
              }
              p.sendMessage(ChatColor.GREEN + "You have reseted the account of: " + ChatColor.DARK_GREEN + p.getName());
          }
          } else if(args[0].equalsIgnoreCase("openRemote")){
              if(p.hasPermission("deliveryman.openRemote")){
                  menu.getMenu(p);
              }
          } else if(args[0].equalsIgnoreCase("prize")){
        	  if(p.hasPermission("deliveryman.prize")){
        		  prizeC.getActionSelection(p);  
        	  }
          } else if(args[0].equalsIgnoreCase("item")){
        	  if(p.hasPermission("deliveryman.item")){
            	  if(args.length == 1){
            		  p.sendMessage(ChatColor.GOLD + "Guide to create an item:");
            		  p.sendMessage("");
            		  p.sendMessage(ChatColor.YELLOW + "First, you need to select an item, put the item in your hand");
            		  p.sendMessage(ChatColor.GOLD + "Then, use the command /dm item create");
            		  p.sendMessage(ChatColor.YELLOW + "Here is the usage: /dm item create [name] [lore...]");
            		  p.sendMessage(ChatColor.GOLD + "This is an example: /dm item create myItem Click_Me! How_are_you?");
            		  p.sendMessage(ChatColor.YELLOW + "You can use this placeholders: %timer% - %player%");
            		  p.sendMessage(ChatColor.GOLD + "Use \"_\" to create a space");
            	  } else if(args[1].equalsIgnoreCase("create")){
            		  if(args.length <= 3){
            			  p.sendMessage(ChatColor.RED + "Usage: /dm item create [name] [lore]");
            			  return true;
            		  }
            		  
            		  if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
            			  p.sendMessage(ChatColor.RED + "You need to select an item; put the item in your hand");
            			  return true;
            		  }
            		  
            		  List<String> lore = new ArrayList<>();
            		  for(int i = 3; i < args.length; i++){
            			  lore.add(args[i].replaceAll("&", "§").replaceAll("_", " "));
            		  }
            		  String name = args[2].replaceAll("&", "§").replaceAll("_", " ");
            		  ItemStack item = p.getItemInHand();
            		  ItemMeta im = item.getItemMeta();
            		  
            		  im.setDisplayName(name);
            		  im.setLore(lore);
            		  item.setItemMeta(im);
            		  
            		  p.sendMessage(ChatColor.GREEN + "Item sussesfully created!");
            	  }
        	  }
          }
        }
        
        return true;
    }
    
    public ItemStack getM(Material material, String name, List<String> lore){
        ItemStack is = new ItemStack(material);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(name);
        ism.setLore(lore);
        is.setItemMeta(ism);
        
        return is;
    }  

    public static Economy eco = null; 
    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if(economyProvider != null){
            eco = economyProvider.getProvider();
        }
        return (eco != null);
    }
    
    public void saveLocConfig(){
        try {
            locC.save(locFile);
        } catch(Exception e){
            Main.log.log(Level.WARNING, "Error loading the configuration");
            Main.log.log(Level.WARNING, "Error: {0}", e.getMessage());
            Main.log.log(Level.INFO, "Try restarting the console and/or reviewing the configuration");
        }
    }
    
    public void saveSaConfig(){
        try {
            saC.save(saFile);
        } catch(Exception e){
            Main.log.log(Level.WARNING, "Error loading the configuration");
            Main.log.log(Level.WARNING, "Error: {0}", e.getMessage());
            Main.log.log(Level.INFO, "Try restarting the console and/or reviewing the configuration");            
        }
    }
    
    public FileConfiguration getSaveC(){
        return saC;
    }
    
    public FileConfiguration getlocC(){
        return locC;
    }

    //RE-DO
    /*    @EventHandler
    private void onj(PlayerJoinEvent e){
        if(sqlActive == true && sql.userExists(e.getPlayer().getUniqueId().toString()) == false){
            sql.createNewUser(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString());
        }
        
        if(ManManager.gMM().getAllMans().isEmpty())
            return;       
        
        String ap;
        if(getActivePrizes(e.getPlayer()) == 0){
            ap = getColorChat(joinMessageNo.replaceAll("%prizes_time%", tr.getTimerPlaceholder()));
        } else {
            ap = getColorChat(joinMessageYes.replaceAll("%prizes_count%", "" + getActivePrizes(e.getPlayer())));
        }
        e.getPlayer().sendMessage(ap);
        Title.sendTitleToPlayer(e.getPlayer(), "", ap, 20, 40, 20);
    }*/
 
    public void getUserConfig(){
        FileConfiguration c = getConfig();
        
        File f = new File(getDataFolder(), "config.yml");
        if(!f.exists()){
            saveConfig();
        }
        
        if(!c.contains("SQLData")){
            c.set("SQLData.mySQL.active", false);
            c.set("SQLData.mySQL.host", "localhost");
            c.set("SQLData.mySQL.user", "root");
            c.set("SQLData.mySQL.password", "");
            c.set("SQLData.mySQL.database", "myServer");
            saveConfig();
            
            sqlHost = c.getString("SQLData.mySQL.host");
            sqlUser = c.getString("SQLData.mySQL.user");
            sqlPass = c.getString("SQLData.mySQL.password");
            sqlDB = c.getString("SQLData.mySQL.database");
            sqlActive = c.getBoolean("SQLData.mySQL.active");
        } else {
            sqlHost = c.getString("SQLData.mySQL.host");
            sqlUser = c.getString("SQLData.mySQL.user");
            sqlPass = c.getString("SQLData.mySQL.password");
            sqlDB = c.getString("SQLData.mySQL.database");
            sqlActive = c.getBoolean("SQLData.mySQL.active");
        }
        
        if(!c.contains("NPCTexts")){
            c.set("NPCTexts.Line1", "&e&lRIGHT CLICK");
            c.set("NPCTexts.Line2", "&4Come here to claim your prizes!");
            c.set("NPCTexts.Line3", "&aDeliveryMan");
            saveConfig();
            
            line1 = c.getString("NPCTexts.Line1");
            line2 = c.getString("NPCTexts.Line2");
            NPCName = c.getString("NPCTexts.Line3");
        } else {
            line1 = c.getString("NPCTexts.Line1");
            line2 = c.getString("NPCTexts.Line2");
            NPCName = c.getString("NPCTexts.Line3");            
        }
        
        if(!c.contains("joinMessage")){
            c.set("joinMessage.hasPrizes", "&6&lYou have: %prizes_count% prizes ready to open!");
            c.set("joinMessage.noPrizes", "&6You need to wait: %prizes_time% to get new prizes");
            saveConfig();
            
            joinMessageYes = c.getString("joinMessage.hasPrizes");
            joinMessageNo = c.getString("joinMessage.noPrizes");
        } else {
            joinMessageYes = c.getString("joinMessage.hasPrizes");
            joinMessageNo = c.getString("joinMessage.noPrizes");           
        } 
        
         if(!c.contains("Votifier")){
            c.set("Votifier.Active", false);
            c.set("Votifier.Message", "&a&lThank you for Voting!");
            c.set("Votifier.Commands", Arrays.asList("say &e%player% &ehas voted on: %voter%", "tell You have recived 500 coins"));
            c.set("Votifier.Money", 500);
            c.set("Votifier.URLs", Arrays.asList("URL;serviceName", "http://inserthere.your/voteService;hereYouServiceName"));
            c.set("Votifier.Item.Slot", 49);
            c.set("Votifier.Item.Title", "&6Vote Here");
            c.set("Votifier.Item.Lore", Arrays.asList("&aVote on: %vote_service_name%", "&b&n%vote_service_url%"));
            saveConfig();
            
            VotifierActive = c.getBoolean("Votifier.Active");
            VotifierCommands = c.getStringList("Votifier.Commands");
            VotifierMessage = c.getString("Votifier.Message");
            VotifierCoins = Integer.parseInt(c.getString("Votifier.Money"));
            VotifierServices = c.getStringList("Votifier.URLs");
            voteItemLore = c.getStringList("Votifier.Item.Lore");
            voteItemName = c.getString("Votifier.Item.Title");
            voteItemSlot = Integer.parseInt(c.getString("Votifier.Item.Slot"));
        } else {
            VotifierActive = c.getBoolean("Votifier.Active");
            VotifierCommands = c.getStringList("Votifier.Commands");
            VotifierMessage = c.getString("Votifier.Message");
            VotifierCoins = Integer.parseInt(c.getString("Votifier.Money"));
            VotifierServices = c.getStringList("Votifier.URLs");
            voteItemLore = c.getStringList("Votifier.Item.Lore");
            voteItemName = c.getString("Votifier.Item.Title");
            voteItemSlot = Integer.parseInt(c.getString("Votifier.Item.Slot"));         
         }  
         
         if(!c.contains("Particles")){
        	 c.set("Particles.Active", true);
        	 c.set("Particles.Type", "TREE");
        	 saveConfig();
        	 
        	 particlesActive = c.getBoolean("Particles.Active");
        	 particlesType = c.getString("Particles.Type");
         } else {
        	 particlesActive = c.getBoolean("Particles.Active");
        	 particlesType = c.getString("Particles.Type");
         }
         
         if(!saC.contains("timer")){
        	 saC.set("timer", false);
        	 saveSaConfig();
         }
         
         if(!c.contains("SkinName")){
        	 c.set("SkinName", "Zealock");
        	 saveConfig();
        	 
        	 skinN4ME = c.getString("SkinName");
         } else {
        	 skinN4ME = c.getString("SkinName");
         }
         
        
    }
    
    
    public String getColorChat(String Message){
        return ChatColor.translateAlternateColorCodes("&".charAt(0), Message);
    }

    public void startSQL(){
        if(sqlActive == true){        
            if(sql.tableExists() == false){
                sql.createTables();
                log.log(Level.INFO, "[SQL-DeliveryMan] Created Table DeliveryMan in {0} database", sqlDB);
            }
            log.log(Level.INFO, "[SQL-DeliveryMan] SQL configuration loaded");
        }
    }
    
    public void votiferPlayerOff(Vote vote){
        OfflinePlayer player = Bukkit.getOfflinePlayer(vote.getUsername());       
       // if(player.hasPlayedBefore() == false)
         //   return;
        eco.depositPlayer(player, VotifierCoins);        
        for(String cmds : VotifierCommands){
            getServer().dispatchCommand(getServer().getConsoleSender(), getColorChat(cmds.replaceAll("%player%", player.getName()).replaceAll("%voter%", vote.getServiceName())));  
        }       
    }
    
    public void votifierPlayerOn(Vote vote){
        Player player = Bukkit.getPlayer(vote.getUsername());
        //if(player.isOnline() == false)
          //  return;
        eco.depositPlayer(player, VotifierCoins); 
        player.sendMessage(getColorChat(VotifierMessage.replaceAll("%player%", player.getName()).replaceAll("%voter%", vote.getServiceName())));
        for(String cmds : VotifierCommands){
            getServer().dispatchCommand(getServer().getConsoleSender(), getColorChat(cmds.replaceAll("%player%", player.getName()).replaceAll("%voter%", vote.getServiceName())));  
        }            
    }
    
    public List<String> colorList(List<String> list){
        List<String> a = new ArrayList<>();
        for(String st : list){
            a.add(ChatColor.translateAlternateColorCodes("&".charAt(0), st));
        }
        return  a;
    }    
}

