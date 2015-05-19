package com.travelee.rest.service;

import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.resource.photo.Photo;
import com.travelee.rest.resource.photo.PhotoList;
import java.util.List;

/**
 *
 * @author nguyenphucuit
 */
public interface PhotoService {

    /*
     * ******************** Create related methods **********************
     * */
    public Long createPhoto(Photo photo) throws AppException;

    /*
     ******************** Read related methods ********************
     */
    public Photo getPhotoById(long id) throws AppException;

    public List<PhotoList> getPhotoListByPlaceId(long placeId, int offset, int maxResult) throws AppException;

    public Photo verifyPodcastExistenceById(Long id) throws AppException;

    public void updateFullyPhoto(Photo photo) throws AppException;

    public void updatePartialPhoto(Photo photo) throws AppException;

    public void deletePhotoById(Long id) throws AppException;

    public List<Photo> getPhotos(Integer offset, Integer maxResult) throws AppException;

}
