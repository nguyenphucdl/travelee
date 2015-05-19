package com.travelee.rest.hateoas;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyenphucuit
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StateLink implements Serializable {

    private static final long serialVersionUID = 517353864098445356L;

    private String rel;

    private String href;

    public StateLink() {

    }

    public StateLink(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    /**
     * @return the rel
     */
    public String getRel() {
        return rel;
    }

    /**
     * @param rel the rel to set
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

}
