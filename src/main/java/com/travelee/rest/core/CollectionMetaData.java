package com.travelee.rest.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyenphucuit
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class CollectionMetaData extends MetaData{
    
    private int totalCount;
    
    private int offset;
    
    private int limit;

    public CollectionMetaData(int totalCount, int offset, int limit){
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
    }
    /**
     * @return the totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
