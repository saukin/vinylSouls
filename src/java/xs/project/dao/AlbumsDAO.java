package xs.project.dao;


import xs.project.exceptions.Exception404;
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
import xs.project.exceptions.Exception500;
import static xs.project.validators.BeanValidation.validateAlbum;
import xs.project.validators.QueryValidation;
import static xs.project.validators.QueryValidation.checkExistUsers;
import static xs.project.validators.QueryValidation.validateEntry;
import static xs.project.validators.QueryValidation.validateUPC;
import static xs.project.validators.QueryValidation.validateUserId;


/**
 *
 * DAO class for Album entities
 * 
 * @author saukin/xavier
 */
public class AlbumsDAO extends ParentDAO {

    /**
     *
     * @param a object type Album that keeps data to create new album in DB
     * @return created Album object 
     * @throws SQLException
     * @throws Exception404
     */
    public Album createAlbum(Album a) throws SQLException, Exception404 
    {   
        
        if (!validateAlbum(a)) {
            throw new Exception404("invalid Album value");
        }
        
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
    
    /**
     *
     * @param a - object type Album
     * @return edited Album object that keeps data to edit an album in DB
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    public Album editAlbum(Album a) throws SQLException, Exception404, Exception500 {
        
        if (!validateAlbum(a)) {
            throw new Exception404("invalid Album value");
        }
        
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
        
        return getAlbumByEntry(String.valueOf(a.getEntry()));
    }
    
    /**
     *
     * @param userId 
     * @param  active 
     * @return List of Album objects from DB selected by userId and state (active/not active)
     * 
     * @throws SQLException
     * @throws Exception404
     */
    public List<Album> getAlbumsByUserId(int userId, boolean active) throws SQLException, Exception404 {
        
        if (!validateUserId(userId) || String.valueOf(userId).trim().equalsIgnoreCase("")) {
            throw new Exception404("userId is out of range"); 
        }
        
        if (checkExistUsers() < userId) {
            throw new Exception404("no such a userId"); 
        }
        
        List<Album> rows = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            
            PreparedStatement pStatement = connection.prepareStatement(getAlbumsQueryByUserId);
            pStatement.setInt(1, userId);
            pStatement.setBoolean(2, active);
            ResultSet resultSet = pStatement.executeQuery();
            User u = new User();
            u.setUser_id(userId);
            while (resultSet.next()) {
                Album aa = new Album();
                createAlbumFromResult(resultSet, aa);
                aa.setUser_ownership(u);
                rows.add(aa);
            }
        }
        return rows;
    }
    
    /**
     *
     * @return List of Album objects - All albums from DB active=true
     * @throws SQLException
     */
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
    
//    public Album getAlbum(String str) throws SQLException {
//       
//        try(Connection connection = DriverManager.getConnection(url, user, password);) {
//                PreparedStatement albumStatement = connection.prepareStatement(getAlbumsByUpcQuery);
//                albumStatement.setString(1, str);
//                albumStatement.setString(2, str);
//                
//                ResultSet albumSet = albumStatement.executeQuery();
//                
//                albumSet.next();
//                
//                Album aa = new Album();
//                
//                createAlbumFromResult(albumSet, aa);
//                
//                User u = new User();
//                
//                PreparedStatement userStatement = connection.prepareStatement(getUserQuery);
//                userStatement.setInt(1, albumSet.getInt("USER_OWNERSHIP"));
//                
//                ResultSet userSet = userStatement.executeQuery();
//                
//                userSet.next();
//                
//                u.setUser_id(userSet.getInt("USER_ID"));
//                u.setEmail(userSet.getString("EMAIL"));
//                
//                aa.setUser_ownership(u);
//        
//            return aa;          
//        }
//    }

    /**
     *
     * @param upc - variable for searching upc code/artist/album name
     * @return List of Album objects  from DB selected by upc/albumName/artist state active
     * @throws SQLException
     * @throws Exception404
     */
    
    public List<Album> getAlbumByUpc(String upc) throws SQLException, Exception404 {
        
        if (!validateUPC(upc) || upc.trim().length() > 25 || upc == null || upc.equalsIgnoreCase("")) {
            throw new Exception404("UPC is more than 25 or less than 1 characters");
        }
        
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
    
    /**
     *
     * @param upc - variable for searching upc code/artist/album name
     * @param userId - variable for User Id parameter
     * @return List of Album objects from DB selected by upc/albumName/artist state active 
     *          of specified user
     * @throws SQLException
     * @throws Exception404
     */
    public List<Album> getAlbumByUpcAndUserId(String upc, String userId) throws SQLException, Exception404 {
        
        if (!validateUPC(upc) || upc.trim().length() > 25 || upc == null || upc.equalsIgnoreCase("")) {
            throw new Exception404("UPC is more than 25 or less than 1 characters");
        }
        
        if (!validateUserId(Integer.parseInt(userId)) || userId.trim().equalsIgnoreCase("")) {
            throw new Exception404("userId is out of range"); 
        }
        
        if (checkExistUsers() < Integer.parseInt(userId)) {
            throw new Exception404("no such a userId"); 
        }
        
        List<Album> rows = new ArrayList<>(); 
        
        try(Connection connection = DriverManager.getConnection(url, user, password);) {
                PreparedStatement albumStatement = connection.prepareStatement(getAlbumsByUpcAndUserIdQuery);
                albumStatement.setInt(1, Integer.parseInt(userId));
                albumStatement.setString(2, upc);
                albumStatement.setString(3, upc);
                albumStatement.setString(4, upc);
                
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
    
    /**
     *
     * @param entry - variable for entry parameter in Db
     * @return Album object selected from db by entry
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    public Album getAlbumByEntry(String entry) throws SQLException, Exception404, Exception500 {
       
        if (!validateEntry(entry)) {
             throw new Exception404("entry out of range"); 
        }
        
        if (QueryValidation.checkExistEntry() < Integer.parseInt(entry) || Integer.parseInt(entry) == 0) {
            throw new Exception500("no such entry"); 
        }
        
        
        try(Connection connection = DriverManager.getConnection(url, user, password);) {
                PreparedStatement pStatement = connection.prepareStatement(getAlbumByEntryQuery);
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
    
    /**
     *
     * @param userId - variable for User Id parameter
     * @param active - variable for active parameter 
     * @return count of active/inactive albums of specified user
     * @throws Exception404
     */
    public int countAlbumsByUserIdChoiceActive(int userId, boolean active) throws Exception404 {
        
        if (!validateUserId(userId)) {
            throw new Exception404("userId is out of range"); 
        }
        
        if (checkExistUsers() < userId) {
            throw new Exception404("no such a userId"); 
        }
        
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pStatement = connection.prepareStatement(countAlbumsByUserId);
            pStatement.setInt(1, userId);
            pStatement.setBoolean(2, active);

            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    /**
     *
     * @param ent - String parameter of album entry
     * @return Album object of deleted(disactivated) album
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    public Album deleteAlbum(String ent) throws SQLException, Exception404, Exception500 {
        
        if (!validateEntry(ent)) {
             throw new Exception404("entry out of range"); 
        }
        
        if (QueryValidation.checkExistEntry() < Integer.parseInt(ent) || Integer.parseInt(ent) == 0) {
            throw new Exception500("no such entry"); 
        }
        
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
    
    /**
     *
     * @param entry - String parameter of album entry
     * @return Album object of restored(reactivated) album
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    public Album restoreAlbum(String entry) throws SQLException, Exception404, Exception500 {
        
        if (!validateEntry(entry)) {
             throw new Exception404("entry out of range"); 
        }
        
        if (QueryValidation.checkExistEntry() < Integer.parseInt(entry) || Integer.parseInt(entry) == 0) {
            throw new Exception500("no such entry"); 
        }
        
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            
            PreparedStatement pStatement = connection.prepareStatement(restoreAlbumQuery);
            pStatement.setInt(1, Integer.parseInt(entry));
            pStatement.executeUpdate();
        
        }
        
        return getAlbumByEntry(entry);
        
    }
    
}    

