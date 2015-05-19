package com.travelee.rest.resource.photo;

import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.hateoas.StateLink;
import com.travelee.rest.helpers.BeanUtility;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Photo resource placeholder for json/xml representation
 *
 * @author nguyenphucuit
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Photo implements Serializable {

    private static final long serialVersionUID = -3626439659841636520L;

    private Long id;

    private String name;

    private String reference;

    private String path;

    private Integer type;

    private Integer width;

    private Integer height;

    private Date timestamp;

    protected List<StateLink> links;

    public Photo() {
    }

    public Photo(PhotoEntity photoEntity) {
        try {
            BeanUtility.copyProperties(this, photoEntity);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Photo(Long id, String reference) {
        this.id = id;
        this.reference = reference;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.resource.photo.Photo[ id=" + id + " ]";
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    @XmlElement(name = "links", required = false)
    public List<StateLink> getLinks() {
        return links;
    }

    public void setLinks(List<StateLink> links) {
        this.links = links;
    }

    public void addLink(StateLink link) {
        links.add(link);
    }

    public void removeLink(StateLink link) {
        links.remove(link);
    }
}
