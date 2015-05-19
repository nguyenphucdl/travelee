package com.travelee.rest.dao.photo;

import java.util.List;

/**
 *
 * @author nguyenphucuit
 */
public interface PhotoDao {

    PhotoEntity getPhotoById(long id);

    List<PhotoEntity> getPhotosByPlaceId(long placeId, int offset, int maxResult);

    public Long createPhoto(PhotoEntity photoEntity);

    public void updatePhoto(PhotoEntity photoEntity);

    public void deletePhotoById(Long id);

    public List<PhotoEntity> getPhotos(Integer offset, Integer maxResult);
}
