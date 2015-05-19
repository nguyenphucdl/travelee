package com.travelee.rest.core;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author nguyenphucuit
 */
public class RequestContext {

    //MultivaluedMap<String, String> queryParams;// = uriInfo.getQueryParameters();
    //MultivaluedMap<String, String> pathParams;// = uriInfo.getPathParameters();
    private UriInfo uriInfo;

    public RequestContext() {
        
    }    

    public void test() {
        String path = getUriInfo().getAbsolutePath().toString();
        String path2 = getUriInfo().getRequestUri().toString();
    }

    /**
     * @return the uriInfo
     */
    public UriInfo getUriInfo() {
        return uriInfo;
    }

    /**
     * @param uriInfo the uriInfo to set
     */
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

}
