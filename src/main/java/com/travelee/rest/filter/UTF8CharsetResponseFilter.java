package com.travelee.rest.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author nguyenphucuit
 */
@Provider
public class UTF8CharsetResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        MediaType contentType = response.getMediaType();

        response.getHeaders().putSingle("Content-Type", contentType.toString() + ";charset=UTF-8");
    }

}
