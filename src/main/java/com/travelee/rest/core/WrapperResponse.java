package com.travelee.rest.core;

import com.travelee.rest.hateoas.StateLink;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyenphucuit
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WrapperResponse {

    @XmlElement(name = "status")
    private int status;

    //@XmlAnyElement(lax = true)
    @XmlElement(name = "data", nillable = true)
    private Object obj;

    @XmlElement(name = "links")
    private List<StateLink> links;

    @XmlElement(name = "_metadata")
    private MetaData metaData;

    private void Init() {
        links = new ArrayList<>();
    }

    public WrapperResponse() {
        Init();
    }

    public WrapperResponse(Response response) {
        this.status = response.getStatus();
        this.obj = response.getEntity();
        Init();
    }

    public WrapperResponse(int status, Object obj) {
        this.status = status;
        this.obj = obj;
        Init();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlElement(name = "data", nillable = true)
    public Object getObject() {
        return this.obj;
    }

    public void setObject(Object object) {
        this.obj = object;
    }

    @XmlElement(name = "links")
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

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
