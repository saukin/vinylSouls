
package ExceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import xs.project.exceptionMessages.ErrorMessage;
import xs.project.exceptions.Exception500;
import xs.project.exceptions.Exception404;

/**
 *
 * @author saukin
 */
@Provider
public class Exception500Mapper implements ExceptionMapper<Exception500> {

    /**
     *
     * @param ex
     * @return Response (error message)
     */
    @Override
    public Response toResponse(Exception500 ex) {
        ErrorMessage em = new ErrorMessage(ex.getMessage(), 500);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(em)
            .build();
    }
    
}
