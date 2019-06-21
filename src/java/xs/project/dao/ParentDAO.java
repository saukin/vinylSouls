
package xs.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import xs.project.entities.Album;

/**
 * Parent class that keeps generic parameters for UserDAO and ALbumDAO classes
 * 
 * @author saukin
 */
public abstract class ParentDAO {
    
    /**
     * DB URL
     */
    protected final String url = "jdbc:mysql://localhost:3306/XS_PROJECT?zeroDateTimeBehavior=convertToNull";

    /**
     *DB client name
     */
    protected final String user = "access";

    /**
     *DB client password
     */
    protected final String password = "admin";
    
    /**
     *createUserQuery
     */
    protected final String createUserQuery = "INSERT INTO appusers (email, password) VALUES (?,?)";

    /**
     *getUserQuery
     */
    protected final String getUserQuery = "SELECT user_id, email FROM appusers WHERE user_id = ?";

    /**
     *getUser By Id Query
     */
    protected final String getUserByIdQuery = "SELECT user_id, email FROM appusers WHERE user_id = ?";

    /**
     * getUserByEmailQuery
     */
    protected final String getUserByEmailQuery = "SELECT user_id, email, password FROM appusers WHERE email = ?";
    
    /**
     * createAlbumQuery
     */
    protected final String createAlbumQuery = "INSERT INTO appalbums (album_name, upc_code, pressing_year, artist_group,"
                + "condition_state, notes, user_ownership, active) VALUES (?,?,?,?,?,?,?,?)";

    /**
     *editAlbum
     */
    protected final String editAlbum = "UPDATE appalbums SET album_name = ?, upc_code = ?, pressing_year = ?, artist_group = ?, condition_state = ?, notes = ?, user_ownership = ?, active = ? WHERE entry = ?";
    
    /**
     *getAlbumsQueryByUserId
     */
    protected final String getAlbumsQueryByUserId = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE user_ownership = ? AND active = ?";

    /**
     *getAlbumsByUpcQuery
     */
    protected final String getAlbumsByUpcQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE (artist_group = ? OR upc_code =? OR album_name=?) AND active = true";

    /**
     *getAlbumsByUpcAndUserIdQuery
     */
    protected final String getAlbumsByUpcAndUserIdQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE user_ownership = ? AND active = true AND (upc_code =? OR album_name=? OR artist_group = ?)";

    
    /**
     * getAlbumsQuery active=true
     */
    protected final String getAlbumsQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE active = true";

    /**
     * getAlbumByEntryQuery
     */
    protected final String getAlbumByEntryQuery = "SELECT entry, album_name, upc_code, pressing_year, artist_group, condition_state, notes, user_ownership, active FROM appalbums WHERE entry = ?";

    /**
     * countAlbumsByUserId
     */
    protected final String countAlbumsByUserId = "SELECT COUNT(*) FROM appalbums WHERE user_ownership = ? AND active = ?";
    
    /**
     * deleteAlbumQuery
     */
    protected final String deleteAlbumQuery = "UPDATE appalbums SET active = false WHERE entry = ?";

    /**
     * restoreAlbumQuery
     */
    protected final String restoreAlbumQuery = "UPDATE appalbums SET active = true WHERE entry = ?";
    
    /**
     *
     * @param r - ResultSet object 
     * @param a - Album object to fill with data from ResultSet
     * @return - Album entity with data from DB
     * @throws SQLException
     */
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

//    protected User createUserFromResult(ResultSet r) throws SQLException {
//        User u = new User();
//        u.setUser_id(r.getInt("USER_ID"));
//        u.setEmail(r.getString("EMAIL"));
//        u.setPassword(r.getString("PASSWORD"));
//        return u;
//    }
    
    
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
