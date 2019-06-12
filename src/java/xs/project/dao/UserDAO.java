package xs.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import xs.project.entities.User;

/**
 *
 * @author admin
 */
public class UserDAO extends ParentDAO {
    
    public User createUser(User u) throws SQLException {
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
    
    public User getUserById(int userId) {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pStatement = connection.prepareStatement(getUserByIdQuery);
            pStatement.setInt(1, userId);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                User u = createUserFromResult(resultSet);
                return u;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public User getUserByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pStatement = connection.prepareStatement(getUserByEmailQuery);
            pStatement.setString(1, email);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                User u = createUserFromResult(resultSet);
                return u;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
