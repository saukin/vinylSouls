
package xs.project.exceptionMessages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * ErrorMessage Class for ExceptionMappers
 * 
 * @author saukin
 */
@XmlRootElement
public class ErrorMessage {
    
    private String errorMessage;
    private int errorCode;
   
    /**
     *
     * @param errorMessage
     * @param errorCode
     */
    public ErrorMessage(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * 
     */
    public ErrorMessage() {
    }

    /**
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     *
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    
    
}
