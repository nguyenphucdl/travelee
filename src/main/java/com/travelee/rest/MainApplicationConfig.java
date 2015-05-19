package com.travelee.rest;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author nguyenphucuit
 */
@javax.ws.rs.ApplicationPath("webresources")
public class MainApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        
        //resources.add(DeclarativeLinkingFeature.class);
        //resources.add(JacksonFeature.class);
        //resources.add(MultiPartFeature.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        // register application resources
        resources.add(com.travelee.rest.EntryPoint.class);
        resources.add(com.travelee.rest.errorhandling.AppExceptionMapper.class);
        // register filters
        resources.add(com.travelee.rest.errorhandling.GenericExceptionMapper.class);
        resources.add(com.travelee.rest.errorhandling.NotFoundExceptionMapper.class);
        // register exception mappers
        resources.add(com.travelee.rest.filter.CORSResponseFilter.class);
        resources.add(com.travelee.rest.filter.LoggingResponseFilter.class);
        resources.add(com.travelee.rest.filter.UTF8CharsetResponseFilter.class);
        resources.add(com.travelee.rest.resource.city.CityResource.class);
        resources.add(com.travelee.rest.resource.photo.PhotoResource.class);
        resources.add(com.travelee.rest.resource.place.PlaceResource.class);
    }
    
}
