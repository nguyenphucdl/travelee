package com.travelee.rest.core;

import com.travelee.rest.hateoas.StateLink;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author nguyenphucuit
 */
public class ResourceBuilder {

    private final WrapperResponse data;

    private final MultivaluedMap<String, Object> headers;

    public static class Builder {

        private WrapperResponse data;

        private MultivaluedMap<String, Object> headers;

        private static Builder _instance = null;

        private Builder() {
            headers = new MultivaluedHashMap<>();
            data = new WrapperResponse();
        }

        protected static Builder Instance() {
            if (_instance == null) {
                _instance = new Builder();
            }
            return _instance;
        }

        //builder methods for setting property
        public Builder status(int status) {
            this.data.setStatus(status);
            return this;
        }

        public Builder entity(Object entity) {
            this.data.setObject(entity);
            return this;
        }

        public Builder wrapper(WrapperResponse entity) {
            this.data = entity;
            return this;
        }

        public Builder header(String header, Object obj) {
            this.headers.add(header, obj);
            return this;
        }

        public Builder link(StateLink link) {
            this.data.addLink(link);
            return this;
        }
        
        public Builder links(List<StateLink> links) {
            for(StateLink link : links) {
                this.data.addLink(link);
            }
            return this;
        }
        
        public Builder metaData(MetaData metaData){
            this.data.setMetaData(metaData);
            return this;
        }

        //return fully build object
        public ResourceBuilder build() {
            _instance = new Builder();
            return new ResourceBuilder(this);
        }

        public Response buildResponse() {
            return build().buildResponse();
        }
    }

    //private constructor to enforce object creation through builder
    private ResourceBuilder(Builder builder) {
        this.data = builder.data;
        this.headers = builder.headers;
    }

    public Response buildResponse() {
        Response.ResponseBuilder resBuilder = Response.status(data.getStatus()).entity(data);
        Set<Map.Entry<String, List<Object>>> entries = this.headers.entrySet();
        for (Map.Entry<String, List<Object>> entry : entries) {
            String key = entry.getKey();
            List<Object> values = entry.getValue();
            for (Object val : values) {
                resBuilder.header(key, val);
            }
        }
        return resBuilder.build();
    }

    public static Builder status(int status) {
        return ResourceBuilder.Builder.Instance().status(status);
    }

    public static Builder status(Response.Status status) {
        return ResourceBuilder.Builder.Instance().status(status.getStatusCode());
    }
}
