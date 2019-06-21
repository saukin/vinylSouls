/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import xs.project.exceptionMessages.ErrorMessage;
import xs.project.exceptions.Exception404;

/**
 * ExceptionMapper for 404 Not Found
 * 
 * 
 * @author saukin
 */
@Provider
public class Exception404Mapper implements ExceptionMapper<Exception404> {

    /**
     *
     * @param ex
     * @return Response (error message)
     */
    @Override
    public Response toResponse(Exception404 ex) {
        ErrorMessage em = new ErrorMessage(ex.getMessage(), 404);
        return Response.status(Status.NOT_FOUND)
            .entity(em)
            .build();
    }
    
    
    
    
}
