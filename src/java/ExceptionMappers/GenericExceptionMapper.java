/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMappers;

import xs.project.exceptionMessages.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author admin
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
   
    /**
     *
     * @param ex
     * @return
     */
    @Override
    public Response toResponse(Throwable ex) {
        ErrorMessage em = new ErrorMessage(ex.getMessage(), 500);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(em)
            .build();
    }
}
