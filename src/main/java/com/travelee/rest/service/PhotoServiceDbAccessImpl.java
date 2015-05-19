package com.travelee.rest.service;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.dao.photo.PhotoDao;
import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.hateoas.HateoastBuilder;
import com.travelee.rest.helpers.NullAwareBeanUtilsBean;
import com.travelee.rest.resource.photo.Photo;
import com.travelee.rest.resource.photo.PhotoList;
import com.travelee.rest.resource.photo.PhotoResource;
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
public class PhotoServiceDbAccessImpl implements PhotoService {

    @Autowired
    PhotoDao photoDao;

    @Autowired
    HateoastBuilder hateoasBuilder;

    /*private List<Photo> getPhotoFromEntities(List<PhotoEntity> photoEntities) {
     List<Photo> response = new ArrayList<>();
     Photo photoHolder = null;
     for (PhotoEntity photoEntity : photoEntities) {
     photoHolder = new Photo(photoEntity);
     photoHolder.setIsTransient(true);
     response.add(photoHolder);
     }
     return response;
     }

    
     public List<Photo> getPhotosByPlaceId(long placeId, int offset, int maxResult) throws AppException {
     List<PhotoEntity> photoEntities = photoDao.getPhotosByPlaceId(placeId, offset, maxResult);

     return getPhotoFromEntities(photoEntities);
     }*/
    @Override
    public List<PhotoList> getPhotoListByPlaceId(long placeId, int offset, int maxResult) throws AppException {
        List<PhotoEntity> photoEntities = photoDao.getPhotosByPlaceId(placeId, offset, maxResult);

        return getPhotoListFromEntities(photoEntities);
    }

    private List<PhotoList> getPhotoListFromEntities(List<PhotoEntity> photoEntities) {
        List<PhotoList> response = new ArrayList<>();
        PhotoList photoHolder = null;
        for (PhotoEntity photoEntity : photoEntities) {
            photoHolder = new PhotoList(photoEntity);
            photoHolder.addLink(hateoasBuilder.buildResourceReferenceLink(PhotoResource.class, "self", photoEntity.getId()));
            response.add(photoHolder);
        }
        return response;
    }

    @Override
    public Photo getPhotoById(long id) throws AppException {
        int offset = 0;
        int maxResult = 20;
        PhotoEntity photoEntity = photoDao.getPhotoById(id);
        if (photoEntity == null) {
            return null;
        } else {
            return new Photo(photoEntity);
        }
    }

    @Override
    public Photo verifyPodcastExistenceById(Long id) throws AppException {
        PhotoEntity photoById = photoDao.getPhotoById(id);
        if (photoById == null) {
            return null;
        } else {
            return new Photo(photoById);
        }
    }

    @Override
    @Transactional("transactionManager")
    public Long createPhoto(Photo photo) throws AppException {

        validateInputForCreation(photo);

        //verify existence of resource in the db (feed must be unique)
        PhotoEntity photoEntityByid = photoDao.getPhotoById(photo.getId());
        if (photoEntityByid != null) {
            throw new AppException(Response.Status.CONFLICT.getStatusCode(), 409,
                    "Photo with id already existing in the database with the id " + photo.getId(),
                    "Please verify that the photo id are properly generated", AppConstants.BLOG_POST_URL);
        }
        return photoDao.createPhoto(new PhotoEntity(photo));
    }

    private void validateInputForCreation(Photo photo) throws AppException {
        if (photo.getId() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the photo id is properly generated/set", AppConstants.BLOG_POST_URL);
        }
        if (photo.getPath() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the photo path is properly generated/set", AppConstants.BLOG_POST_URL);
        }
        //etc...
    }

    /**
     * ******************* UPDATE-related methods implementation
     * **********************
     */
    @Override
    @Transactional("transactionManager")
    public void updateFullyPhoto(Photo photo) throws AppException {
        //do a validation to verify FULL update with PUT
        if (isFullUpdate(photo)) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
                    400,
                    "Please specify all properties for Full UPDATE",
                    "required properties - id, name, path ...",
                    AppConstants.BLOG_POST_URL);
        }

        Photo verifyPhotoExistenceById = verifyPodcastExistenceById(photo.getId());
        if (verifyPhotoExistenceById == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + photo.getId(),
                    AppConstants.BLOG_POST_URL);
        }

        photoDao.updatePhoto(new PhotoEntity(photo));
    }

    /**
     * Verifies the "completeness" of photo resource sent over the wire
     *
     * @param photo
     * @return
     */
    private boolean isFullUpdate(Photo photo) {
        return photo.getId() == null
                || photo.getName() == null
                || photo.getPath() == null
                || photo.getType() == null;
    }

    @Override
    @Transactional("transactionManager")
    public void updatePartialPhoto(Photo photo) throws AppException {
        //do a validation to verify existence of the resource		
        PhotoEntity verifyPhotoExistenceById = photoDao.getPhotoById(photo.getId());
        
        if (verifyPhotoExistenceById == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + photo.getId(),
                    AppConstants.BLOG_POST_URL);
        }
        copyPartialProperties(verifyPhotoExistenceById, photo);
        photoDao.updatePhoto(verifyPhotoExistenceById);
    }

    private void copyPartialProperties(PhotoEntity photoEntityDest, Photo photoOrigin) {
        BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
        try {
            notNull.copyProperties(photoEntityDest, photoOrigin);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PhotoServiceDbAccessImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PhotoServiceDbAccessImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void deletePhotoById(Long id) throws AppException {
        photoDao.deletePhotoById(id);
    }

    @Override
    public List<Photo> getPhotos(Integer offset, Integer maxResult) throws AppException {
        
        List<PhotoEntity> photoEntities = photoDao.getPhotos(offset, maxResult);
        return getPhotoFromEntities(photoEntities);
    }

    private List<Photo> getPhotoFromEntities(List<PhotoEntity> photoEntities) {
        List<Photo> response = new ArrayList<>();
        Photo photoHolder = null;
        for (PhotoEntity photoEntity : photoEntities) {
            photoHolder = new Photo(photoEntity);
            response.add(photoHolder);
        }
        return response;
    }
}
