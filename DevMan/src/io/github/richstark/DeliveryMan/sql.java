package io.github.richstark.DeliveryMan;

import com.mysql.jdbc.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;



public class sql{

    private static Main main;
    
    public sql (Main i) {
        main = i;
    }
    
    public void createTables(){
       Connection con = null;
       PreparedStatement pst = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
          
        con = DriverManager.getConnection(url, user, password);
        
        pst = con.prepareStatement("CREATE TABLE DeliveryMan ("
                + "playerUUID VARCHAR(255),"
                + "playerName VARCHAR(255),"
                + "prize1 VARCHAR(255),"
                + "prize2 VARCHAR(255),"
                + "prize3 VARCHAR(255),"
                + "prize4 VARCHAR(255),"
                + "prize5 VARCHAR(255),"
                + "prize6 VARCHAR(255),"
                + "prize7 VARCHAR(255),"
                + "prize8 VARCHAR(255),"
                + "prize9 VARCHAR(255)"
                + ")");

        pst.executeUpdate();
        
       } catch(SQLException ex){
           Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
       } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
            }          
       }
               
    }
    public void createNewUser(String playerName, String playerUUID){
       Connection con = null;
       PreparedStatement pst = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
          
        con = DriverManager.getConnection(url, user, password);
        
        pst = con.prepareStatement("INSERT INTO DeliveryMan(playerUUID, playerName,prize1,prize2,prize3,prize4,prize5,prize6,prize7,prize8,prize9)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.setString(2, playerName);
        pst.setString(1, playerUUID);
        pst.setString(3, "true");
        pst.setString(4, "true");
        pst.setString(5, "true");
        pst.setString(6, "true");
        pst.setString(7, "true");
        pst.setString(8, "true");
        pst.setString(9, "true");
        pst.setString(10, "true");
        pst.setString(11, "true");
        
        pst.executeUpdate();
        
       } catch(SQLException ex){
            Main.log.log(Level.SEVERE,"[SQL-DeliveryMan]" +  ex.getMessage());          
       } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
            }          
       }
       
    }
    public void updateInfo(String playerUUID,String id, String value){
        
       Connection con = null;
       PreparedStatement pst = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
          
        con = DriverManager.getConnection(url, user, password);
        
        pst = con.prepareStatement("UPDATE DeliveryMan SET prize" + id + "='" + value +"' WHERE playerUUID='" + playerUUID + "'");      
        pst.executeUpdate();
        
       } catch(SQLException ex){
            Main.log.log(Level.SEVERE,"[SQL-DeliveryMan]" +  ex.getMessage());          
       } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Main.log.log(Level.SEVERE,"[SQL-DeliveryMan]" +  ex.getMessage());
            }          
       }
           
        
    }
    public String getValues(String playeruUUID, String id){
        String value = null;
        
       Connection conn = null;
       Statement st = null;
       ResultSet rs = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
           conn = DriverManager.getConnection(url, user, password);
           st = conn.createStatement();
           rs = st.executeQuery("SELECT * FROM DeliveryMan WHERE playerUUID='" + playeruUUID + "'");
           
           if(rs.next()){               
               value = rs.getString("prize" + id);
           }
           
       } catch(SQLException ex){
           Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());         
       } finally {
           try {
               if(rs != null){
                   rs.close();
               }
               if(st != null){
                   st.close();
               }
               if(conn != null){
                   conn.close();
               }
           } catch (SQLException ex){
               Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
           }
       }
                      
        return value;
    }
    public ArrayList<String> getAllPlayers(){
        ArrayList<String> value = new ArrayList<>();
        
       Connection conn = null;
       Statement st = null;
       ResultSet rs = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
           conn = DriverManager.getConnection(url, user, password);
           st = conn.createStatement();
           rs = st.executeQuery("SELECT playerUUID FROM DeliveryMan");
           
           while(rs.next()){               
               value.add(rs.getString("playerUUID"));
           }
           
       } catch(SQLException ex){
           Main.log.log(Level.SEVERE,"[SQL-DeliveryMan]" +  ex.getMessage());         
       } finally {
           try {
               if(rs != null){
                   rs.close();
               }
               if(st != null){
                   st.close();
               }
               if(conn != null){
                   conn.close();
               }
           } catch (SQLException ex){
               Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
           }
       }
                      
        return value;
    }
    
    public boolean tableExists(){
        boolean value = false;
        
       Connection conn = null;
       Statement st = null;
       ResultSet rs = null;
       DatabaseMetaData dm = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
           conn = DriverManager.getConnection(url, user, password);
           st = conn.createStatement();
           dm = (DatabaseMetaData) conn.getMetaData();
           
           rs = dm.getTables(null, null, "DeliveryMan", null);
           
           if(rs.next()){               
               value = true;
           } else {
               value = false;
           }
           
       } catch(SQLException ex){
           Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]{0}", ex.getMessage());         
       } finally {
           try {
               if(rs != null){
                   rs.close();
               }
               if(st != null){
                   st.close();
               }
               if(conn != null){
                   conn.close();
               }
           } catch (SQLException ex){
               Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
           }
       }
                      
        return value;
    }
    public boolean userExists(String playerUUID){
        boolean value = false;
        
       Connection conn = null;
       Statement st = null;
       ResultSet rs = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
           conn = DriverManager.getConnection(url, user, password);
           st = conn.createStatement();   
           rs = st.executeQuery("SELECT * FROM DeliveryMan WHERE playerUUID='" + playerUUID + "'");
           
           if(rs.next()){               
               value = true;
           } else {
               value = false;
           }
           
       } catch(SQLException ex){
           Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());         
       } finally {
           try {
               if(rs != null){
                   rs.close();
               }
               if(st != null){
                   st.close();
               }
               if(conn != null){
                   conn.close();
               }
           } catch (SQLException ex){
               Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
           }
       }
                      
        return value;
    }
    
    public int getActiveCount(String playerUUID){
        int count = 0;
        
       Connection conn = null;
       Statement st = null;
       ResultSet rs = null;
       
       String host = main.sqlHost;
       String db = main.sqlDB;
       
       String url = "jdbc:mysql://" + host + "/" + db;
       String user = main.sqlUser;
       String password = main.sqlPass;
       
       try {
           conn = DriverManager.getConnection(url, user, password);
           st = conn.createStatement();   
           rs = st.executeQuery("SELECT * FROM DeliveryMan WHERE playerUUID='" + playerUUID + "'");
           
           if(rs.getString("prize1").equalsIgnoreCase("false")){
               count = count + 1;
           } 
           
           if(rs.getString("prize2").equalsIgnoreCase("false")){
               count = count + 1;
           } 
           
           if(rs.getString("prize3").equalsIgnoreCase("false")){
               count = count + 1;
           } 
           
           if(rs.getString("prize4").equalsIgnoreCase("false")){
               count = count + 1;
           }  
           
           if(rs.getString("prize5").equalsIgnoreCase("false")){
               count = count + 1;
           } 
           
           if(rs.getString("prize6").equalsIgnoreCase("false")){
               count = count + 1;
           } 
           
           if(rs.getString("prize7").equalsIgnoreCase("false")){
              count = count + 1;
           } 
           
           if(rs.getString("prize8").equalsIgnoreCase("false")){
               count = count + 1;
           }       
           
           if(rs.getString("prize9").equalsIgnoreCase("false")){
               count = count + 1;
           }             
           
       } catch(SQLException ex){
           Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());         
       } finally {
           try {
               if(rs != null){
                   rs.close();
               }
               if(st != null){
                   st.close();
               }
               if(conn != null){
                   conn.close();
               }
           } catch (SQLException ex){
               Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
           }
       }
                      
        
        return count;
    }
    
    public void createNewTable(String id){
        Connection con = null;
        PreparedStatement pst = null;
        
        String host = main.sqlHost;
        String db = main.sqlDB;
        
        String url = "jdbc:mysql://" + host + "/" + db;
        String user = main.sqlUser;
        String password = main.sqlPass;
        
        try {
           
         con = DriverManager.getConnection(url, user, password);
         
         pst = con.prepareStatement("ALTER TABLE deliveryman DROP COLUMN prize" + id + " null");

         pst.executeUpdate();
         
        } catch(SQLException ex){
            Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
        } finally {
             try {
                 if (pst != null) {
                     pst.close();
                 }
                 if (con != null) {
                     con.close();
                 }

             } catch (SQLException ex) {
                 Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
             }          
        }
          
    }
    
    public void deleteTable(String id){
        Connection con = null;
        PreparedStatement pst = null;
        
        String host = main.sqlHost;
        String db = main.sqlDB;
        
        String url = "jdbc:mysql://" + host + "/" + db;
        String user = main.sqlUser;
        String password = main.sqlPass;
        
        try {
           
         con = DriverManager.getConnection(url, user, password);
         
         pst = con.prepareStatement("ALTER TABLE deliveryman DROP COLUMN prize" + id);

         pst.executeUpdate();
         
        } catch(SQLException ex){
            Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
        } finally {
             try {
                 if (pst != null) {
                     pst.close();
                 }
                 if (con != null) {
                     con.close();
                 }

             } catch (SQLException ex) {
                 Main.log.log(Level.SEVERE, "[SQL-DeliveryMan]" + ex.getMessage());
             }          
        }
          
    }
    
}
