package xs.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import xs.project.entities.User;
import xs.project.exceptions.Exception500;
import xs.project.exceptions.Exception404;
import static xs.project.validators.BeanValidation.validateUser;
import static xs.project.validators.QueryValidation.checkExistUsers;
import static xs.project.validators.QueryValidation.validateEmail;
import static xs.project.validators.QueryValidation.validateUserId;

/**
 * DAO class for User entity
 * 
 * @author saukin/xavier
 */
public class UserDAO extends ParentDAO {

    /**
     *
     * @param u - User object that keeps data to create new user in DB
     * @return - created User object
     * @throws SQLException
     * @throws Exception500
     * @throws Exception404
     */
    public User createUser(User u) throws SQLException, Exception500, Exception404 {
        UserDAO ud = new UserDAO();
        if (!validateUser(u) && (ud.getUserByEmail(u.getEmail()) == null)) {
            throw new Exception500("invalid User value");
        }
        
        try (Connection connection = DriverManager.getConnection(url, user, password);) 
        {

            PreparedStatement pStatement = connection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, u.getEmail());
            pStatement.setString(2, u.getPassword());
            
            int ar = pStatement.executeUpdate();

            ResultSet genKeys = pStatement.getGeneratedKeys();
            
            genKeys.next();
            
            u.setUser_id(genKeys.getInt(1));
            
        return u;
        }
    }
    
    /**
     *
     * @param userId - user Id from DB
     * @return - User object selected by Id
     * @throws Exception404
     */
    public User getUserById(int userId) throws Exception404 {
        
        if (!validateUserId(userId)) {
            throw new Exception404("userId is out of range"); 
        }
        
        if (checkExistUsers() < userId) {
            throw new Exception404("no such a userId"); 
        }
        
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pStatement = connection.prepareStatement(getUserByIdQuery);
            pStatement.setInt(1, userId);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                User u = new User();
                u.setUser_id(resultSet.getInt("USER_ID"));
                u.setEmail(resultSet.getString("EMAIL"));
                return u;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     *
     * @param email - user email from DB
     * @return User object selected by email
     * @throws Exception404
     */
    public User getUserByEmail(String email) throws Exception404 {
        
        if (!validateEmail(email) ) {
            throw new Exception404("no such email"); 
        }
        
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pStatement = connection.prepareStatement(getUserByEmailQuery);
            pStatement.setString(1, email);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
               User u = new User();
                u.setUser_id(resultSet.getInt("USER_ID"));
                u.setEmail(resultSet.getString("EMAIL"));
                u.setPassword(resultSet.getString("PASSWORD"));
                return u;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
}
