package com.travelee.rest.core;

/**
 *
 * @author nguyenphucuit
 */
public class AppConstants {

    public static final int GENERIC_APP_ERROR_CODE = 5001;
    public static final String BLOG_POST_URL = "http://www.codingpedia.org/ama/tutorial-rest-api-design-and-implementation-in-java-with-jersey-and-spring/";

    public static final String PLACE_DETAIL_TEMPLATE = "http://{host}/places/{id}";
    public static final String HOST = "http://localhost:8080//travelee";

    public static final String test = "http://{host}/{path}?q={param}";

    public static class Photo {
        public static final String PARAM_ID = "id";
        /* Constants */
        public static final String CONSTANTS_MAX_RESULTS = "25";
    }

    public static class Place {
        /* Params */
        public static final String PARAM_ID = "id";
        public static final String PARAM_LATITUDE = "lat";
        public static final String PARAM_LONGTITUDE = "lng";
        public static final String PARAM_DISTANCE = "distance";
        public static final String PARAM_TYPES = "types";
        public static final String PARAM_PLACE_ID = "placeId";
        /* Constants */
        public static final String CONSTANT_MAX_RESULTS = "25";
        public static final String CONSTANT_NEARBY_MAX_RESULTS = "25";
        public static final String CONSTANT_NEARBY_DISTANCE = "50";
        /* DB Field name */
        public static final String FIELD_ID = "id";
        public static final String FIELD_PLACE_ID = "placeId";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_LATITUDE = "lat";
        public static final String FIELD_LONGITUDE = "lng";
        public static final String FIELD_FORMATTED_ADDRESS = "formattedAddress";
        public static final String FIELD_FORMATTED_PHONE_NUMBER = "formattedPhoneNumber";
        public static final String FIELD_ICON = "icon";
        public static final String FIELD_TYPES = "types";
        public static final String FIELD_TAGS = "tags";
        public static final String FIELD_INTERNATIONAL_PHONE = "internationalPhoneNumber";
        public static final String FIELD_REFERENCE = "reference";
        public static final String FIELD_URL = "url";
        public static final String FIELD_VICINITY = "vicinity";
        public static final String FIELD_WEBSITE = "website";

        /* Custom PlaceNearBy Field name */
        public static final String CUSTOM_FIELD_DISTANCE = "distance";
        public static final String CUSTOM_FIELD_SCORE = "score";
    }

    public static class Pagination {

        public static final String PARAM_PAGING_OFFSET = "offset";
        public static final String PARAM_PAGING_MAXRESULT = "maxResult";
    }

    public static class StateLink {

        public static final String REF_NEXT_NAME = "next";
        public static final String REF_PREV_NAME = "prev";
        public static final String REF_SELF_NAME = "self";
    }

    public static class ProcedureTemplate {

        /* PARAMS ORDER: lat, lng, distance, phrases, offset, maxResult  */
        public static final String PROC_SEARCH_PLACE_NEARBY_MATCH_TYPE_TEMPLATE = "CALL `travelee_legacy`.`searchPlaceNearbyMatchTypeAdvanced`(%.6f, %.6f, %d, '%s', %d, %d)";
        public static final String PROC_SEARCH_PLACE_NEARBY_TEMPLATE = "CALL `travelee_legacy`.`searchPlaceNearby`(%.6f, %.6f, %d, %d, %d)";
    }
}
