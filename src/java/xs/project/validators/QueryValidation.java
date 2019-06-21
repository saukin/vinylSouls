
package xs.project.validators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 * VAlidation class for Album and User fields
 * 
 * @author saukin
 */
public class QueryValidation {
    
    private static final String URL = "jdbc:mysql://localhost:3306/XS_PROJECT?zeroDateTimeBehavior=convertToNull";
    private static final String USER = "access";
    private static final String PASSWORD = "admin";
    
    /**
     *
     * @param id
     * @return
     */
    public static boolean validateUserId(int id) {
        //8
        
        return (id <= 99999999 && id > 0);
    }
    
    /**
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        //25
        return (email.trim().length() <= 25 && email.trim().length() > 0);
    }
    
    /**
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        //25
        return (password.trim().length() <= 25 && password.trim().length() > 0);
    }
    
    /**
     *
     * @param entry
     * @return
     */
    public static boolean validateEntry(String entry) {
        //8
        return (entry.trim().length() <= 8 && entry.trim().length() > 0);
    }
    
    /**
     *
     * @param albumName
     * @return
     */
    public static boolean validateAlbumName(String albumName) {
        //25
        return (albumName.trim().length() <= 25 && albumName.trim().length() > 0);
    }
    
    /**
     *
     * @param upc
     * @return
     */
    public static boolean validateUPC(String upc) {
        // 25
        
        return (upc.trim().length() > 0);
    }
    
    /**
     *
     * @param year
     * @return
     */
    public static boolean validateYear(int year) {
       // 4
        int thisYear = LocalDate.now().getYear();
           
        return !((thisYear < year) || (year < 1900));
    }
    
    /**
     *
     * @param artist
     * @return
     */
    public static boolean validateArtist(String artist) {
        //25
        return (artist.trim().length() <= 25 && artist.trim().length() > 0);
    }
    
    /**
     *
     * @param condition
     * @return
     */
    public static boolean validateCondition(String condition) {
        // 4
        return condition.trim().length() <= 4;
    }
    
    /**
     *
     * @param notes
     * @return
     */
    public static boolean validateNotes(String notes) {
        // 400
        return notes.trim().length() <= 400;
    }
    
    /**
     *
     * @param owner
     * @return
     */
    public static boolean validateOwner(String owner) {
        // 8
        return owner.trim().length() <= 8;
    }
    

    
    /**
     * method to get Maximum id value from appusers table
     * 
     * @return
     */
    public static int checkExistUsers() {
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);) {

            PreparedStatement pStatement = connection.prepareStatement("SELECT MAX(user_id) FROM appusers");
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
               int maxId = resultSet.getInt(1);
               return  maxId;
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    /**
     * method to get Maximum entry value from appalbums table
     * 
     * @return 
     */
    public static int checkExistEntry() {
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);) {

            PreparedStatement pStatement = connection.prepareStatement("SELECT MAX(entry) FROM appalbums");
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
               int maxId = resultSet.getInt(1);
               return  maxId;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
       
}
