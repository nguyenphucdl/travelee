package com.travelee.rest.service;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.dao.place.PlaceDao;
import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.dao.place.PlaceNearbyEntity;
import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.hateoas.HateoastBuilder;
import com.travelee.rest.hateoas.StateLink;
import com.travelee.rest.helpers.BeanUtility;
import com.travelee.rest.helpers.NullAwareBeanUtilsBean;
import com.travelee.rest.resource.photo.PhotoList;
import com.travelee.rest.resource.place.Place;
import com.travelee.rest.resource.place.PlaceNearBy;
import com.travelee.rest.resource.place.PlaceResource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nguyenphucuit
 */
public class PlaceServiceDbAccessImpl implements PlaceService {

    @Autowired
    PlaceDao placeDao;

    @Autowired
    private PlaceResource placeResource;

    @Autowired
    private HateoastBuilder hateoasBuilder;
    
    /*
     * *********************************** READ ***********************************
     */
    @Override
    public List<Place> getPlaces(Integer offset, Integer maxResult) throws AppException {
        List<PlaceEntity> placeEntities = placeDao.getPlaces(offset, maxResult);

        if (placeEntities == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Please set either ASC or DESC for the orderByInsertionDate parameter", null, AppConstants.BLOG_POST_URL);
        }
        //List<Place> places = getCityFromEntities(placeEntities);
        return getPlacesFromEntities(placeEntities);
    }

    private List<Place> getPlacesFromEntities(List<PlaceEntity> placeEntities) {
        List<Place> response = new ArrayList<>();
        for (PlaceEntity placeEntity : placeEntities) {
            response.add(new Place(placeEntity));
        }
        return response;
    }

    /*
     * *********************************** READ ***********************************
     */
    @Override
    public List<PlaceNearBy> getPlaceNearBy(Double lat, Double lng, Integer distance,String types ,Integer offset, Integer maxResult) throws AppException {
        List<PlaceNearbyEntity> placeNearbyEntities = null;
        if(types.equals("all"))
        {
            placeNearbyEntities = placeDao.getPlaceNearby(lat, lng, distance, offset, maxResult);
        }
        else {
            String[] keywords = types.split("[|]");
            StringBuilder searchPhraseBuilder = new StringBuilder();
            for(String keyword : keywords){
                searchPhraseBuilder.append(keyword).append(" ");
            }
            searchPhraseBuilder.deleteCharAt(searchPhraseBuilder.length() -1);
            
            placeNearbyEntities = placeDao.getPlaceNearbyMatchTypes(lat, lng, distance, searchPhraseBuilder.toString(), offset, maxResult);
        }
        
        return getPlaceNearbyFormEntities(placeNearbyEntities);
    }

    private List<PlaceNearBy> getPlaceNearbyFormEntities(List<PlaceNearbyEntity> placeNearbyEntities) {
        List<PlaceNearBy> response = new ArrayList<>();
        PlaceNearBy placeNearby = null;
        for (PlaceNearbyEntity entity : placeNearbyEntities) {
            placeNearby = new PlaceNearBy(entity);
            placeNearby.setPhotos(getPhotoListFromPhotoEntity(entity.getPhotoEntityList()));
            
            StateLink selfLink = hateoasBuilder.buildResourceReferenceLink(PlaceResource.class, "self" ,placeNearby.getId());
            placeNearby.addLink(selfLink);
            response.add(placeNearby);
        }        
        return response;
    }

    private List<PhotoList> getPhotoListFromPhotoEntity(List<PhotoEntity> photoEntities) {
        List<PhotoList> response = new ArrayList<>();
        for(PhotoEntity photoEntity : photoEntities) {
            response.add(new PhotoList(photoEntity));
        }
        return response;
    }
    
    @Override
    public Place verifyPlaceExistenceById(Long id) throws AppException {
        PlaceEntity placeEntity = placeDao.getPlaceById(id);
        if (placeEntity == null) {
            return null;
        } else {
            return new Place(placeEntity);
        }
    }

    /**
     * ******************* Create related methods implementation
     * **********************
     */
    @Override
    @Transactional("transactionManager")
    public Long createPlace(Place place) throws AppException {
        validateInputForCreation(place);

        //verify existence of resource in the db (feed must be unique)
        PlaceEntity placeEntityById = placeDao.getPlaceById(place.getId());
        if (placeEntityById == null) {
            throw new AppException(Response.Status.CONFLICT.getStatusCode(), 409, "Place with id already existing in the database with the id " + place.getId(),
                    "Please verify that the id properly set", AppConstants.BLOG_POST_URL);
        }
        return placeDao.createPlace(new PlaceEntity(place));
    }

    private void validateInputForCreation(Place place) throws AppException {
        if (place.getId() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the place id is properly generated/set", AppConstants.BLOG_POST_URL);
        }
        if (place.getName() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the place name is properly generated/set", AppConstants.BLOG_POST_URL);
        }
        //etc...
    }

    /**
     * ******************* UPDATE-related methods implementation
     * **********************
     */
    @Override
    @Transactional("transactionManager")
    public void updateFullyPlace(Place place) throws AppException {
        //do a validation to verify FULL update with PUT
        if (isFullUpdate(place)) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
                    400,
                    "Please specify all properties for Full UPDATE",
                    "required properties - id, name, path ...",
                    AppConstants.BLOG_POST_URL);
        }
        Place verifyPlaceExistenceById = verifyPlaceExistenceById(place.getId());

        if (verifyPlaceExistenceById == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + place.getId(),
                    AppConstants.BLOG_POST_URL);
        }
        
        placeDao.updatePlace(new PlaceEntity(place));
    }

    private boolean isFullUpdate(Place place) {
        return place.getId() == null 
                || place.getName() == null
                || place.getTypes() == null
                || place.getPlaceId() == null;
    }

//    private List<PlaceResponse> getPlacesFromEntities2(List<PlaceEntity> placeEntities) {
//    List<PlaceResponse> response = new ArrayList<>();
//    PlaceList placeHolder = null;
//    String testTemplate = AppConstants.HOST + UriBuilder.fromResource(PlaceResource.class).path("{id}").toTemplate();
//
//    for (PlaceEntity placeEntity : placeEntities) {
//
//        placeHolder = new PlaceList(placeEntity);
//        URI uri = UriBuilder.fromUri(testTemplate).resolveTemplate("id", placeHolder.getId()).build();
//        placeHolder.addState(new State("self", uri.toString()));
//        placeHolder.setPhotos(getPhotosFromPhotoEntities(placeEntity.getPhotoEntityList()));
//        response.add(placeHolder);
//    }
//    return response;
//    }
//    private List<PhotoList> getPhotosFromPhotoEntities(List<PhotoEntity> photoEntities) {
//        List<PhotoList> response = new ArrayList<>();
//        PhotoList photoList = null;
//        for (PhotoEntity photoEntity : photoEntities) {
//            photoList = new PhotoList(photoEntity);
//            photoList.addState(new State("self", "http://localhost:8080/travelee/api/photos/1"));
//            response.add(photoList);
//        }
//        return response;
//    }
    @Override
    @Transactional("transactionManager")
    public void updatePartiallyPlace(Place place) throws AppException {
        //do a validation to verify existence of the resource		
        PlaceEntity verifyPlaceExistenceById = placeDao.getPlaceById(place.getId());

        if (verifyPlaceExistenceById == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + place.getId(),
                    AppConstants.BLOG_POST_URL);
        }
        copyPartialProperties(verifyPlaceExistenceById, place);
        placeDao.updatePlace(verifyPlaceExistenceById);
    }

    private void copyPartialProperties(PlaceEntity placeEntityDest, Place placeOrigin) {
        try {
            BeanUtility.copyProperties(placeEntityDest, placeOrigin);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PlaceServiceDbAccessImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PlaceServiceDbAccessImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
