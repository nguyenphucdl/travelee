/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.travelee.rest.dao.checkin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "checkin")
@NamedQueries({
    @NamedQuery(name = "CheckinEntity.findAll", query = "SELECT c FROM CheckinEntity c"),
    @NamedQuery(name = "CheckinEntity.findById", query = "SELECT c FROM CheckinEntity c WHERE c.id = :id"),
    @NamedQuery(name = "CheckinEntity.findByUserId", query = "SELECT c FROM CheckinEntity c WHERE c.userId = :userId"),
    @NamedQuery(name = "CheckinEntity.findByPlaceId", query = "SELECT c FROM CheckinEntity c WHERE c.placeId = :placeId"),
    @NamedQuery(name = "CheckinEntity.findByPrettyName", query = "SELECT c FROM CheckinEntity c WHERE c.prettyName = :prettyName"),
    @NamedQuery(name = "CheckinEntity.findByDatetime", query = "SELECT c FROM CheckinEntity c WHERE c.datetime = :datetime")})
public class CheckinEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "place_id")
    private int placeId;
    @Size(max = 300)
    @Column(name = "pretty_name")
    private String prettyName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    public CheckinEntity() {
    }

    public CheckinEntity(Integer id) {
        this.id = id;
    }

    public CheckinEntity(Integer id, int userId, int placeId, Date datetime) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.datetime = datetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
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
        if (!(object instanceof CheckinEntity)) {
            return false;
        }
        CheckinEntity other = (CheckinEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.checkin.CheckinEntity[ id=" + id + " ]";
    }
    
}
