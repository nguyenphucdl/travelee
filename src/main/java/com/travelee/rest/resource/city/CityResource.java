package com.travelee.rest.resource.city;

import com.travelee.rest.core.ResourceBuilder;
import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.service.CityService;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CityResource that handles REST requests
 *
 * @author nguyenphucuit
 */
@Component
@Path("/cities")
public class CityResource {

    @Autowired
    private CityService cityService;

    /*
     * *********************************** READ ***********************************
     */
    /**
     * Returns all resources (City) from the database
     *
     * @return
     * @throws AppException
     */
    @GET
    //@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCities() throws AppException {
        List<City> cities = cityService.getCities();

        return ResourceBuilder.status(300).entity(cities).buildResponse();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityById(@PathParam("id") Long id) throws AppException {
        City city = cityService.verifyCityExistenceById(id);
        if (city == null) {
            return ResourceBuilder.status(Response.Status.NOT_FOUND).buildResponse();
        }
        return ResourceBuilder.status(Response.Status.OK).entity(city).buildResponse();
    }

    /*
     * *********************************** CREATE ***********************************
     */
    /**
     * Adds a new resource (City) from the given json format (at least title and
     * feed elements are required at the DB level)
     *
     * @param City
     * @return
     * @throws AppException
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createCity(City city) throws AppException {
        Long createCityId = cityService.createCity(city);

        return ResourceBuilder.status(Response.Status.CREATED).entity("A new city has been created")
                .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(createCityId)).buildResponse();
    }

    /**
     * Adds a new City (resource) from "form" (at least name and asciiname
     * elements are required at the DB level)
     *
     * @param name
     * @param asciiname
     * @param alternatenames
     * @param lat
     * @param ln
     * @param featureClass
     * @param featureCode
     * @return
     * @throws AppException
     */
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces
    public Response createCityFromApplicationFormURLencoder(
            @FormParam("name") String name,
            @FormParam("asciiname") String asciiname,
            @FormParam("alternatenames") String alternatenames,
            @FormParam("lat") String lat,
            @FormParam("ln") String ln,
            @FormParam("featureClass") String featureClass,
            @FormParam("featureCode") String featureCode
    ) throws AppException {

        return null;
    }

    /*
     * *********************************** UPDATE ***********************************
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putCityById(@PathParam("id") Long id, City city) throws AppException {

        City cityById = cityService.verifyCityExistenceById(id);

        if (cityById == null) {
            // resource not existent yet, and should be created under the
            // specified URI
            Long createCityId = cityService.createCity(city);

            return ResourceBuilder.status(Response.Status.CREATED)//201
                    .entity("A new city has been created AT THE LOCATION you specified")
                    .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(createCityId)).buildResponse();
        } else {
            // resource is existent and a full update should occur
            cityService.updateFullyCity(city);
            return ResourceBuilder.status(Response.Status.OK)//200
                    .entity("The city you specified has been fully updated created AT THE LOCATION you specified")
                    .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/"
                            + String.valueOf(id)).buildResponse();
        }
    }

    // PARTIAL update
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response partialUpdateCity(@PathParam("id") Long id, City city) throws AppException {
        city.setId(id);
        cityService.updatePartiallyCity(city);
        return ResourceBuilder.status(Response.Status.OK)//200
                .entity("The city you specified has been successfully updated")
                .buildResponse();
    }

}
