package com.travelee.rest.resource.photo;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.core.ResourceBuilder;
import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.hateoas.HateoastBuilder;
import com.travelee.rest.service.PhotoService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("/photos")
public class PhotoResource {

    @Autowired
    private PhotoService photoService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private HateoastBuilder hateoasBuilder;

    /*
     * *********************************** READ ***********************************
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPhotos(@DefaultValue("0") @QueryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET) Integer offset,
            @DefaultValue(AppConstants.Photo.CONSTANTS_MAX_RESULTS) @QueryParam(AppConstants.Pagination.PARAM_PAGING_MAXRESULT) Integer maxResult) throws AppException {
        List<Photo> photos = photoService.getPhotos(offset, maxResult);

        return ResourceBuilder.status(Response.Status.OK).entity(photos).buildResponse();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPhotoById(@PathParam(AppConstants.Photo.PARAM_ID) Long id) throws AppException {

        Photo photoById = photoService.getPhotoById(id);
        if (photoById == null) {
            return ResourceBuilder.status(Response.Status.NOT_FOUND).entity("No Content").buildResponse();
        }
        return ResourceBuilder.status(Response.Status.OK).entity(photoById).buildResponse();
    }

    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPhotoListByPlaceId(@QueryParam(AppConstants.Place.PARAM_PLACE_ID) Integer placeId,
            @DefaultValue("0") @QueryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET) Integer offset,
            @DefaultValue(AppConstants.Photo.CONSTANTS_MAX_RESULTS) @QueryParam(AppConstants.Pagination.PARAM_PAGING_MAXRESULT) Integer maxResult) throws AppException {

        List<PhotoList> photos = photoService.getPhotoListByPlaceId(placeId, offset, maxResult);

        return ResourceBuilder.status(Response.Status.OK).entity(photos).link(hateoasBuilder.buildCurrentRequestLink()).links(hateoasBuilder.buildRequestPagingLinks(PhotoResource.class)).buildResponse();
    }

    /*public Response getPhotoListByPlaceId2(@Context UriInfo uriInfo, @DefaultValue("All") @QueryParam(value = "q") final List<String> item) throws AppException {}*/
    /*
     * *********************************** UPDATE ***********************************
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putPhotoById(@PathParam(AppConstants.Photo.PARAM_ID) Long id, Photo photo) throws AppException {
        Photo photoById = photoService.verifyPodcastExistenceById(id);

        if (photoById == null) {
            // resource not existent yet, and should be created under the
            // specified URI
            Long createPhotoId = photoService.createPhoto(photo);
            return ResourceBuilder.status(Response.Status.CREATED)//201
                    .entity("A new photo has been created AT THE LOCATION you specified")
                    .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(createPhotoId))
                    .buildResponse();
        } else {
            // resource is existent and a full update should occur
            photoService.updateFullyPhoto(photo);
            return ResourceBuilder.status(Response.Status.OK)//200
                    .entity("The photo you specified has been fully updated created AT THE LOCATION you specified")
                    .header("Location", "http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(id)).buildResponse();
        }
    }

    // PARTIAL update
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response partialUpdatePhoto(@PathParam(AppConstants.Photo.PARAM_ID) Long id, Photo photo) throws AppException {
        photo.setId(id);
        photoService.updatePartialPhoto(photo);
        return ResourceBuilder.status(Response.Status.OK)//200
                .entity("The photo you specified has been successfully updated")
                .buildResponse();
    }

    /*
     * *********************************** DELETE ***********************************
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePhotoById(@PathParam(AppConstants.Photo.PARAM_ID) Long id) throws AppException {
        photoService.deletePhotoById(id);
        return ResourceBuilder.status(Response.Status.NO_CONTENT)//204
                .entity("Photo successfully removed from database").buildResponse();
    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        /* IMPORTANT TO BUILD HATEOAS LINK */
        hateoasBuilder.initRequestContext(uriInfo);
    }
}
