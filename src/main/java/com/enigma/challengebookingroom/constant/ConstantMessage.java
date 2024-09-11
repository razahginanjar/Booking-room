package com.enigma.challengebookingroom.constant;

public class ConstantMessage {

    public static final String CREATED = "Successfully created";
    public static final String FETCHED = "Successfully fetched";
    public static final String FETCHED_ALL = "Successfully fetched all";
    public static final String UPDATED = "Successfully updated";
    public static final String NO_CONTENT = "Successfully deleted";
    public static final String NOT_FOUND = "Not found";
    public static final String OK = "Okay";
    public static final String BAD_REQUEST = "Bad request";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String FORBIDDEN = "Forbidden";
    public static final String CONFLICT = "Conflict";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String UNREACHABLE = "Service Unreachable";
    public static final String NOT_NULL = "Cannot be null";
    public static final String NOT_EMPTY = "Cannot be empty";
    public static final String NOT_BLANK = "Cannot be empty";
    public static final String ERROR_DATE = "Date cannot be in the past";
    public static final String ALREADY_CLICK_HTML = "<html><body><h1>Already Clicked</h1><p>This link has already been used. Please contact support if you need assistance.</p></body></html>";
    public static final String SUCCESS_CLICKED = "<html><body><h1>Success</h1><p>Your response has been recorded. Thank you!</p></body></html>";

    private ConstantMessage() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}