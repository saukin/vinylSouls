package xs.project.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import xs.project.entities.Album;
import xs.project.entities.User;


/**
 *
 * @author admin
 */
public class AlbumsDAO extends ParentDAO {

    public Album createAlbum(Album a) throws SQLException 
    {
        try (Connection connection = DriverManager.getConnection(url, user, password);)
        {
            PreparedStatement pStatement = connection.prepareStatement(createAlbumQuery, Statement.RETURN_GENERATED_KEYS);
            
            pStatement.setString(1, a.getAlbum_name());
            pStatement.setString(2, a.getUpc_code());
            pStatement.setInt(3, a.getPressing_year());
            pStatement.setString(4, a.getArtist_group());
            pStatement.setString(5, a.getCondition_state());
            pStatement.setString(6, a.getNotes());
            pStatement.setInt(7, a.getUser_ownership().getUser_id());
            pStatement.setBoolean(8, a.getActive());
            
            pStatement.executeUpdate();
            
            ResultSet genKeys = pStatement.getGeneratedKeys();
            
            genKeys.next();
            
            a.setEntry(genKeys.getInt(1));
        
        return a;
        }
    }
    
    public Album editAlbum(Album a) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            
            PreparedStatement pStatement = connection.prepareStatement(editAlbum);
            pStatement.setString(1, a.getAlbum_name());
            pStatement.setString(2, a.getUpc_code());
            pStatement.setInt(3, a.getPressing_year());
            pStatement.setString(4, a.getArtist_group());
            pStatement.setString(5, a.getCondition_state());
            pStatement.setString(6, a.getNotes());
            pStatement.setInt(7, a.getUser_ownership().getUser_id());
            pStatement.setBoolean(8, a.getActive());
            
            pStatement.setInt(9, a.getEntry());
            pStatement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return getAlbum(a.getUpc_code());
    }
    
    public List<Album> getAlbumsByUserId(int i, boolean active) throws SQLException {

        List<Album> rows = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            
            PreparedStatement pStatement = connection.prepareStatement(getAlbumsQueryByUserId);
            pStatement.setInt(1, i);
            pStatement.setBoolean(2, active);
            ResultSet resultSet = pStatement.executeQuery();
            User u = new User();
            u.setUser_id(i);
            while (resultSet.next()) {
                Album aa = new Album();
                createAlbumFromResult(resultSet, aa);
                aa.setUser_ownership(u);
                rows.add(aa);
            }
        }
        return rows;
    }
    
    public List<Album> getAlbums() throws SQLException {

        List<Album> rows = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);) 
        {
            
            PreparedStatement pStatement = connection.prepareStatement(getAlbumsQuery);
           
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                Album aa = new Album();
                createAlbumFromResult(resultSet, aa);
                
                rows.add(aa);
            }
        }
        return rows;
    }
    
    public Album getAlbum(String s) throws SQLException {
        
       try(Connection connection = DriverManager.getConnection(url, user, password);) {
                PreparedStatement albumStatement = connection.prepareStatement(getAlbumsByUpcQuery);
                albumStatement.setString(1, s);
                albumStatement.setString(2, s);
                
                ResultSet albumSet = albumStatement.executeQuery();
                
                albumSet.next();
                
                Album aa = new Album();
                
                createAlbumFromResult(albumSet, aa);
                
                User u = new User();
                
                PreparedStatement userStatement = connection.prepareStatement(getUserQuery);
                userStatement.setInt(1, albumSet.getInt("USER_OWNERSHIP"));
                
                ResultSet userSet = userStatement.executeQuery();
                
                userSet.next();
                
                u.setUser_id(userSet.getInt("USER_ID"));
                u.setEmail(userSet.getString("EMAIL"));
                
                aa.setUser_ownership(u);
        
            return aa;          
        }
    }
    
    public List<Album> getAlbumByUpc(String upc) throws SQLException {
       
        List<Album> rows = new ArrayList<>(); 
        
        try(Connection connection = DriverManager.getConnection(url, user, password);) {
                PreparedStatement albumStatement = connection.prepareStatement(getAlbumsByUpcQuery);
                albumStatement.setString(1, upc);
                albumStatement.setString(2, upc);
                albumStatement.setString(3, upc);
                
               ResultSet albumSet = albumStatement.executeQuery();

            while (albumSet.next()) {

                Album aa = new Album();

                createAlbumFromResult(albumSet, aa);

                User u = new User();

                PreparedStatement userStatement = connection.prepareStatement(getUserQuery);
                userStatement.setInt(1, albumSet.getInt("USER_OWNERSHIP"));

                ResultSet userSet = userStatement.executeQuery();

                userSet.next();

                u.setUser_id(userSet.getInt("USER_ID"));
                u.setEmail(userSet.getString("EMAIL"));

                aa.setUser_ownership(u);
                rows.add(aa);
            }

            return rows;          
        }
    }
    
    
    public List<Album> getAlbumByUpcAndUserId(String user_id, String upc) throws SQLException {
       
        List<Album> rows = new ArrayList<>(); 
        
        try(Connection connection = DriverManager.getConnection(url, user, password);) {
                PreparedStatement albumStatement = connection.prepareStatement(getAlbumsByUpcAndUserIdQuery);
                albumStatement.setString(1, user_id);
                albumStatement.setString(2, upc);
                albumStatement.setString(3, upc);
                
               ResultSet albumSet = albumStatement.executeQuery();

            while (albumSet.next()) {

                Album aa = new Album();

                createAlbumFromResult(albumSet, aa);

                User u = new User();

                PreparedStatement userStatement = connection.prepareStatement(getUserQuery);
                userStatement.setInt(1, albumSet.getInt("USER_OWNERSHIP"));

                ResultSet userSet = userStatement.executeQuery();

                userSet.next();

                u.setUser_id(userSet.getInt("USER_ID"));
                u.setEmail(userSet.getString("EMAIL"));

                aa.setUser_ownership(u);
                rows.add(aa);
            }

            return rows;          
        }
    }
    
    public Album getAlbumByEntry(String entry) throws SQLException {
        
       try(Connection connection = DriverManager.getConnection(url, user, password);) {
                PreparedStatement pStatement = connection.prepareStatement(getAlbumByIdQuery);
                pStatement.setString(1, entry);
                
                
                ResultSet albumSet = pStatement.executeQuery();
                
                albumSet.next();
                
                Album aa = new Album();
                
                createAlbumFromResult(albumSet, aa);
                
                User u = new User();

                PreparedStatement userStatement = connection.prepareStatement(getUserQuery);
                userStatement.setInt(1, albumSet.getInt("USER_OWNERSHIP"));

                ResultSet userSet = userStatement.executeQuery();

                userSet.next();

                u.setUser_id(userSet.getInt("USER_ID"));
                u.setEmail(userSet.getString("EMAIL"));

                aa.setUser_ownership(u);
        
            return aa;          
        }
    }
    
    
    public int countAlbumsByUserIdChoiceActive(int u, boolean active) {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pStatement = connection.prepareStatement(countAlbumsByUserIdChoiceActive);
            pStatement.setInt(1, u);
            pStatement.setBoolean(2, active);

            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(u);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public Album deleteAlbum(String ent) throws SQLException {
        int entry = Integer.parseInt(ent);
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            
            PreparedStatement pStatement = connection.prepareStatement(deleteAlbumQuery);
            pStatement.setInt(1, entry);
//            pStatement.setBoolean(1, false);
            pStatement.executeUpdate();
        
        } catch (Exception e) {
            System.out.println("A TROUBLE");
        }
        
        return getAlbumByEntry(ent);
    }
    
    
    public Album restoreAlbum(String entry) throws SQLException {
        
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            
            PreparedStatement pStatement = connection.prepareStatement(restoreAlbumQuery);
            pStatement.setInt(1, Integer.parseInt(entry));
            pStatement.executeUpdate();
        
        }
        
        return getAlbumByEntry(entry);
        
    }
    
    
    
}    

