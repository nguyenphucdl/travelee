/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.travelee.rest.dao.photo;

import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.dao.tip.TipEntity;
import com.travelee.rest.dao.user.UserEntity;
import com.travelee.rest.resource.photo.Photo;
import com.travelee.rest.resource.place.Place;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "photo")
@NamedQueries({
    @NamedQuery(name = "PhotoEntity.findAll", query = "SELECT p FROM PhotoEntity p"),
    @NamedQuery(name = "PhotoEntity.findById", query = "SELECT p FROM PhotoEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PhotoEntity.findByName", query = "SELECT p FROM PhotoEntity p WHERE p.name = :name"),
    @NamedQuery(name = "PhotoEntity.findByReference", query = "SELECT p FROM PhotoEntity p WHERE p.reference = :reference"),
    @NamedQuery(name = "PhotoEntity.findByPath", query = "SELECT p FROM PhotoEntity p WHERE p.path = :path"),
    @NamedQuery(name = "PhotoEntity.findByType", query = "SELECT p FROM PhotoEntity p WHERE p.type = :type"),
    @NamedQuery(name = "PhotoEntity.findByWidth", query = "SELECT p FROM PhotoEntity p WHERE p.width = :width"),
    @NamedQuery(name = "PhotoEntity.findByHeight", query = "SELECT p FROM PhotoEntity p WHERE p.height = :height"),
    @NamedQuery(name = "PhotoEntity.findByTimestamp", query = "SELECT p FROM PhotoEntity p WHERE p.timestamp = :timestamp")})
public class PhotoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 1000)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "reference")
    private String reference;
    @Size(max = 1000)
    @Column(name = "path")
    private String path;
    @Column(name = "type")
    private Integer type;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private UserEntity userId;
    @JoinColumn(name = "tip_id", referencedColumnName = "id")
    @ManyToOne
    private TipEntity tipId;
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    @ManyToOne
    private PlaceEntity placeId;

    public PhotoEntity() {
    }

    public PhotoEntity(Long id) {
        this.id = id;
    }

    public PhotoEntity(Photo photo) {
        try {
            BeanUtils.copyProperties(this, photo);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PhotoEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PhotoEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public TipEntity getTipId() {
        return tipId;
    }

    public void setTipId(TipEntity tipId) {
        this.tipId = tipId;
    }

    public PlaceEntity getPlaceId() {
        return placeId;
    }

    public void setPlaceId(PlaceEntity placeId) {
        this.placeId = placeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhotoEntity)) {
            return false;
        }
        PhotoEntity other = (PhotoEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.photo.PhotoEntity[ id=" + id + " ]";
    }
    
}
