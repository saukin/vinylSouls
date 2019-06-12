/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import xs.project.entities.Album;
import xs.project.entities.User;

/**
 *
 * @author admin
 */
public abstract class ParentDAO {
    
    protected final String url = "jdbc:mysql://localhost:3306/XS_PROJECT?zeroDateTimeBehavior=convertToNull";
    protected final String user = "access";
    protected final String password = "admin";
    
    protected final String createUserQuery = "INSERT INTO appusers (email, password) VALUES (?,?)";
    protected final String getUserQuery = "SELECT user_id, email FROM appusers WHERE user_id = ?";
    protected final String getUserByIdQuery = "SELECT user_id, email FROM appusers WHERE user_id = ?";
    protected final String getUserByEmailQuery = "SELECT user_id, email, password FROM appusers WHERE email = ?";
    
    protected final String createAlbumQuery = "INSERT INTO appalbums (album_name, upc_code, pressing_year, artist_group,"
                + "condition_state, notes, user_ownership, active) VALUES (?,?,?,?,?,?,?,?)";
    protected final String editAlbum = "UPDATE appalbums SET album_name = ?, upc_code = ?, pressing_year = ?, artist_group = ?, condition_state = ?, notes = ?, user_ownership = ?, active = ? WHERE entry = ?";
    
    protected final String getAlbumsQueryByUserId = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE user_ownership = ? AND active = ?";
    protected final String getAlbumsByUpcQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE (artist_group = ? OR upc_code =? OR album_name=?) AND active = true";
    protected final String getAlbumsByUpcAndUserIdQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE user_ownership = ? AND active = true AND (upc_code =? OR album_name=?)";
    protected final String getAlbumsByArtistQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE artist_group =?";
    protected final String getAlbumsQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE active = true";
    protected final String getAlbumByIdQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE entry = ?";
    protected final String countAlbumsByUserIdChoiceActive = "SELECT COUNT(*) FROM appalbums WHERE user_ownership = ? AND active = ?";
    
    protected final String deleteAlbumQuery = "UPDATE appalbums SET active = false WHERE entry = ?";
    protected final String restoreAlbumQuery = "UPDATE appalbums SET active = true WHERE entry = ?";
    
    
    
    
    protected Album createAlbumFromResult(ResultSet r, Album a) throws SQLException {
        a.setEntry(r.getInt("ENTRY"));
        a.setAlbum_name(r.getString("ALBUM_NAME"));
        a.setUpc_code(r.getString("UPC_CODE"));
        a.setPressing_year(r.getInt("PRESSING_YEAR"));
        a.setArtist_group(r.getString("ARTIST_GROUP"));
        a.setCondition_state(r.getString("CONDITION_STATE"));
        a.setNotes(r.getString("NOTES"));
        a.setActive(r.getBoolean("ACTIVE"));
        
        return a;
    }

    protected User createUserFromResult(ResultSet r) throws SQLException {
        User u = new User();
        u.setUser_id(r.getInt("USER_ID"));
        u.setEmail(r.getString("EMAIL"));
        u.setPassword(r.getString("PASSWORD"));
        return u;
    }
    
    
//    protected Album createAlbumFromResultAndUser(ResultSet r, Album a, User u) throws SQLException {
//        
//        a.setEntry(r.getInt("ENTRY"));
//        a.setAlbum_name(r.getString("ALBUM_NAME"));
//        a.setUpc_code(r.getString("UPC_CODE"));
//        a.setPressing_year(r.getInt("PRESSING_YEAR"));
//        a.setArtist_group(r.getString("ARTIST_GROUP"));
//        a.setCondition_state(r.getString("CONDITION_STATE"));
//        a.setNotes(r.getString("NOTES"));
//        a.setUser_ownership(u);
//        a.setActive(r.getBoolean("ACTIVE"));
//
//        return a;
//    }
    
}
