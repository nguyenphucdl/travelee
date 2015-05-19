package com.travelee.rest.hateoas;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.resource.photo.PhotoResource;
import com.travelee.rest.resource.place.Place;
import com.travelee.rest.resource.place.PlaceResource;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author nguyenphucuit
 */
public class HateoastBuilder {

    private UriInfo requestInfo;

    private String baseUri;

    private String currentRequestUri;

    private MultivaluedMap<String, String> queryParameters;

    private UriBuilder requestUriBuilder;

    public HateoastBuilder() {

    }

    private void Init() {
        baseUri = requestInfo.getBaseUri().toString();
        currentRequestUri = requestInfo.getRequestUri().toString();
        queryParameters = requestInfo.getQueryParameters();
        requestUriBuilder = requestInfo.getRequestUriBuilder();
    }

    public UriInfo getRequestInfo() {
        return requestInfo;
    }

    public void initRequestContext(UriInfo requestInfo) {
        this.requestInfo = requestInfo;
        this.Init();
    }

    public StateLink buildCurrentRequestLink() {
        return new StateLink(AppConstants.StateLink.REF_SELF_NAME, currentRequestUri);
    }

    public StateLink buildResourceReferenceLink(Class classResource, String name, Long id) {
        StateLink returnLink = null;
        if (classResource == PlaceResource.class || classResource == PhotoResource.class) {
            UriBuilder uriBuilder = requestInfo.getBaseUriBuilder();
            String pathResouce = UriBuilder.fromResource(classResource).toString();
            String link = uriBuilder.path(pathResouce).path(id.toString()).toString();
            returnLink = new StateLink(name, link);
        }
        return returnLink;
    }

    public List<StateLink> buildRequestPagingLinks(Class classResource) {
        List<StateLink> stateLinks = new ArrayList<>();
        
        UriBuilder requestUriBuilderClone = requestUriBuilder.clone();
        
        String offsetString = queryParameters.getFirst(AppConstants.Pagination.PARAM_PAGING_OFFSET);
        String maxResultString = queryParameters.getFirst(AppConstants.Pagination.PARAM_PAGING_MAXRESULT);
        int curOffset = 0;
        if (offsetString == null) {
            requestUriBuilderClone.queryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET, curOffset);
        } else {
            curOffset = Integer.parseInt(offsetString);
        }

        int nexOffset = curOffset + 25;
        int prevOffset = curOffset - 25;

        requestUriBuilderClone.replaceQueryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET, nexOffset);
        stateLinks.add(new StateLink(AppConstants.StateLink.REF_NEXT_NAME, requestUriBuilderClone.toString()));
        if (curOffset > 0) {
            requestUriBuilderClone.replaceQueryParam(AppConstants.Pagination.PARAM_PAGING_OFFSET, prevOffset);
            stateLinks.add(new StateLink(AppConstants.StateLink.REF_PREV_NAME, requestUriBuilderClone.toString()));
        }

        return stateLinks;
    }
}
