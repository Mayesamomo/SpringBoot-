package com.repostit.repostit.Utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String COMMUNITY_NOT_FOUND_WITH_ID = "Community not found with id - ";
    public static final String COMMUNITY_NOT_FOUND_WITH_NAME = "Community not found with name - ";
    public static final String POST_NOT_FOUND_FOR_ID = "Post Not Found for id - ";
    public static final String ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification"; // sends users here when
    // they click on the activation email link sent to them.
    public static final String POST_URL = "http://localhost:4200/view-post/"; //returns newly users here
}
