package com.travelee.rest.resource.photo;

import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.hateoas.StateLink;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Photo resource placeholder for json/xml representation
 *
 * @author nguyenphucuit
 */
public class PhotoList extends Photo {

    private static final long serialVersionUID = -2088849181867018105L;
    
    public PhotoList(PhotoEntity photoEntity) {
        super(photoEntity);
        
        links = new ArrayList<>();
    }

    @Override
    @XmlTransient
    public Integer getWidth() {
        return super.getWidth();
    }

    @Override
    @XmlTransient
    public Integer getHeight() {
        return super.getHeight();
    }

    @Override
    @XmlTransient
    public String getReference() {
        return super.getReference();
    }
    
    @Override
    @XmlElement(name = "links", required = true)
    public List<StateLink> getLinks() {
        return this.links;
    }
}
