package com.travelee.rest.errorhandling;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author nguyenphucuit
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException ex) {
        return Response.status(ex.getResponse().getStatus())
                .entity(new ErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON) //this has to be set to get the generated JSON 
                .build();
    }
}
