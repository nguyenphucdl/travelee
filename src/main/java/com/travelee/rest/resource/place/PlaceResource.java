package com.travelee.rest.resource.place;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.core.CollectionMetaData;
import com.travelee.rest.core.MetaData;
import com.travelee.rest.core.ResourceBuilder;
import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.hateoas.HateoastBuilder;
import com.travelee.rest.service.PlaceService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service class that handles REST requests
 *
 * @author nguyenphucuit
 */
@Component
@Path("/places")
public class PlaceResource {

    @Autowired
    private PlaceService placeService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private HateoastBuilder hateoasBuilder;

    /*
     * *********************************** READ ***********************************
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlaces(@DefaultValue("0") @QueryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET) Integer offset,
            @DefaultValue(AppConstants.Place.CONSTANT_MAX_RESULTS) @QueryParam(AppConstants.Pagination.PARAM_PAGING_MAXRESULT) Integer maxResult) throws AppException {

        List<Place> places = placeService.getPlaces(offset, maxResult);

        if (places == null) {
            return ResourceBuilder.status(Response.Status.NO_CONTENT).buildResponse();
        }
        return ResourceBuilder.status(Response.Status.OK).entity(places).link(hateoasBuilder.buildCurrentRequestLink()).buildResponse();
    }

    @GET
    @Path("/nearby")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlaceNearby(@QueryParam(AppConstants.Place.PARAM_LATITUDE) Double lat, @QueryParam(AppConstants.Place.PARAM_LONGTITUDE) Double lng,
            @DefaultValue(AppConstants.Place.CONSTANT_NEARBY_DISTANCE) @QueryParam(AppConstants.Place.PARAM_DISTANCE) Integer distance,
            @DefaultValue("0") @QueryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET) Integer offset,
            @DefaultValue(AppConstants.Place.CONSTANT_NEARBY_MAX_RESULTS) @QueryParam(AppConstants.Pagination.PARAM_PAGING_MAXRESULT) Integer maxResult,
            @DefaultValue("all") @QueryParam(AppConstants.Place.PARAM_TYPES) String types) throws AppException {

        if (lat == null || lng == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for search",
                    "Please verify that the lat or lng is properly set", AppConstants.BLOG_POST_URL);
        }

        List<PlaceNearBy> placeNearbyList = placeService.getPlaceNearBy(lat, lng, distance, types, offset, maxResult);

        if (placeNearbyList == null) {
            return ResourceBuilder.status(Response.Status.NO_CONTENT).buildResponse();
        }
        
        MetaData metaData = new CollectionMetaData(placeNearbyList.size(), offset, maxResult);
        
        return ResourceBuilder.status(Response.Status.OK).entity(placeNearbyList)
                .metaData(metaData)
                .link(hateoasBuilder.buildCurrentRequestLink())
                .links(hateoasBuilder.buildRequestPagingLinks(PlaceResource.class))
                .buildResponse();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlaceById(@PathParam(AppConstants.Place.PARAM_ID) Long id) throws AppException {
        PlaceResponse place = placeService.verifyPlaceExistenceById(id);
        if (place == null) {
            return ResourceBuilder.status(Response.Status.NOT_FOUND).buildResponse();
        }
        return ResourceBuilder.status(Response.Status.OK).entity(place).buildResponse();
    }

    /*
     * *********************************** UPDATE ***********************************
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putPlaceById(@PathParam(AppConstants.Place.PARAM_ID) Long id, Place place) throws AppException {
        Place verifyPlaceById = placeService.verifyPlaceExistenceById(id);

        if (verifyPlaceById == null) {
            // resource not existent yet, and should be created under the
            // specified URI
            Long createPlaceId = placeService.createPlace(place);

            return ResourceBuilder.status(Response.Status.CREATED)
                    .entity("A new podcast has been created AT THE LOCATION you specified")
                    .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(createPlaceId)).buildResponse();

        } else {
            // resource is existent and a full update should occur
            placeService.updateFullyPlace(place);
            return ResourceBuilder.status(Response.Status.OK)//200
                    .entity("The place you specified has been fully updated created AT THE LOCATION you specified")
                    .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(id)).buildResponse();
        }
    }

    // PARTIAL update
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response partialUpdatePlace(@PathParam(AppConstants.Place.PARAM_ID) Long id,
            Place place) throws AppException {
        place.setId(id);
        placeService.updatePartiallyPlace(place);
        return ResourceBuilder.status(Response.Status.OK)//200
                .entity("The place you specified has been successfully updated")
                .buildResponse();

    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        /* IMPORTANT TO BUILD HATEOAS LINKS */
        hateoasBuilder.initRequestContext(uriInfo);
    }
}
