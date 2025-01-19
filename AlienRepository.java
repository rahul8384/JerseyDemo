package com.teluskolearning.demorestjersey;
//import java.util.*;
//import jakarta.ws.rs.*;
//
//public class AlienRepository {
//	
//	private List<Alien> alien;
//	
//	public AlienRepository() {
//		alien = new ArrayList<>();
//		Alien a1 = new Alien();
//	    a1.setId(101);
//	    a1.setName("Rahul");
//	    a1.setPoints(100);
//
//	    Alien a2 = new Alien();
//	    a2.setId(102);
//	    a2.setName("Shrey");
//	    a2.setPoints(60);
//
//	    alien.add(a1);
//	    alien.add(a2);
//
//	}
//	
//	public List<Alien> getAliens(){
//		return alien;
//	}
//	
//	public Alien getAlien(int id) {
//		
//		for(Alien a1 : alien) {
//			if((Integer.valueOf(a1.getId()).equals(id))) {
//				return a1;
//			}
//		}
//		
//		return null;
//	}
//	
//
//
////	public void create(Alien a1) {
////		// TODO Auto-generated method stub
////		alien.add(a1);
////	}
//	
//	public void create(Alien alien) {
//		int newId = alien.isEmpty() ? 1 : alien.get(alien.size() - 1).getId() + 1;
//	    alien.setId(newId);
//	    alien.add(alien);
//	}
//	
//
//}
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class AlienRepository{

    private List<Alien> aliens;
    
    private Connection con = null;
    

    // Constructor to initialize the repository
    public AlienRepository() {
    	 String url = "jdbc:mysql://localhost:3306/restdb";
    	 String username = "rahul";
    	 String password = "Iamwhoiam@0503";
    	 try {
    		 Class.forName("com.mysql.cj.jdbc.Driver");
    		 con = DriverManager.getConnection(url, username, password);
    	 }catch(Exception e) {
    		 System.out.println("connection failed!....");
    		 System.out.println(e);
    	 }
    }

    // Method to fetch all aliens
    public List<Alien> getAliens() {
    	List<Alien> aliens = new ArrayList<>();
    	String query = "select * from aliens";
    	try {
    		Statement st = con.createStatement();
    		ResultSet rs = st.executeQuery(query);
    		while(rs.next()) {
    			Alien a = new Alien();
    			a.setId(rs.getInt(1));
    			a.setName(rs.getString(2));
    			a.setPoints(rs.getInt(3));
    			
    			aliens.add(a);
    		}
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	return aliens;
    }

    // Method to fetch a single alien by ID
    public Alien getAlien(int id) {
    	 Alien a = new Alien();
    	String query = "select * from aliens where id =" + id;
    	try {
    		Statement st = con.createStatement();
    		ResultSet rs = st.executeQuery(query);
    		if(rs.next()) {
    			
    			a.setId(rs.getInt(1));
    			a.setName(rs.getString(2));
    			a.setPoints(rs.getInt(3));
    			
    		}
  
    	}
    	catch(Exception e) {
    		System.out.println(e);
    		e.printStackTrace();
    	}
    	return a;
    }

    // Method to create a new alien with auto-generated ID
    public Alien create(Alien alien) {
    	 String sql = "INSERT INTO aliens (name, points) VALUES (?, ?)";
         try {
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             stmt.setString(1, alien.getName());
             stmt.setInt(2, alien.getPoints());
             stmt.executeUpdate();

             // Get the auto-generated ID
             ResultSet generatedKeys = stmt.getGeneratedKeys();
             if (generatedKeys.next()) {
                 alien.setId(generatedKeys.getInt(1));
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return alien;
    }
    
//    public void create(Alien alien) {
//    	String sql = "insert into alines values(?,?,?)";
//    	try {
//    		PreparedStatement st = con.prepareStatement(sql);
//    		st.setInt(1, alien.getId());
//    		st.setString(2, alien.getName());
//    		st.setInt(3, alien.getPoints());
//    		st.executeUpdate();
//    	}catch(Exception e) {
//    		System.out.println(e);
//    	}
//    }
    
    public Alien update(Alien alien) {
        String sql = "UPDATE aliens SET name = ?, points = ? WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, alien.getName());
            stmt.setInt(2, alien.getPoints());
            stmt.setInt(3, alien.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Alien updated successfully.");
                return alien;
            } else {
                System.out.println("Alien with ID " + alien.getId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if update fails
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM aliens WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Alien deleted successfully.");
                return true;
            } else {
                System.out.println("Alien with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if delete fails
    }


}

