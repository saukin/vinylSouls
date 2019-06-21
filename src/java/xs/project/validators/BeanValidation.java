
package xs.project.validators;

import xs.project.entities.Album;
import xs.project.entities.User;
import static xs.project.validators.QueryValidation.validateAlbumName;
import static xs.project.validators.QueryValidation.validateArtist;
import static xs.project.validators.QueryValidation.validateCondition;
import static xs.project.validators.QueryValidation.validateEmail;
import static xs.project.validators.QueryValidation.validateNotes;
import static xs.project.validators.QueryValidation.validateUserId;
import static xs.project.validators.QueryValidation.validateYear;



/**
 *
 * @author saukin
 */
public class BeanValidation {
    
    /**
     *
     * @param a - Album object to check
     * @return boolean whether Album object is valid or not 
     */
    public static boolean validateAlbum(Album a) {
        
        if (!validateAlbumName(a.getAlbum_name()) || (a.getUpc_code().length() > 25) || 
               !validateYear(a.getPressing_year()) || !validateArtist(a.getArtist_group()) ||
               !validateCondition(a.getCondition_state()) || !validateNotes(a.getNotes())) {
            return false;
        }
        
        return true;
    }
    
    /**
     *
     * @param u - User object to check
     * @return boolean whether User object is valid or not 
     */
    public static boolean validateUser(User u) {
        return !(!validateEmail(u.getEmail()) || validateUserId(u.getUser_id()));
    }
    
}
