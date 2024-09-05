package com.enigma.challengebookingroom.constant;

import java.util.ResourceBundle;

public class APIUrl {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("api-urls");

    private APIUrl() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String PATH_VAR_ID = getApiUrl("path.var.id");
    public static final String AUTH = getApiUrl("auth");
    public static final String EMPLOYEE = getApiUrl("employee");
    public static final String EQUIPMENT = getApiUrl("equipment");
    public static final String EQUIPMENT_STATUS = getApiUrl("equipment_status");
    public static final String ACTIVITY_LOG = getApiUrl("activity_log");
    public static final String REPORT = getApiUrl("report");
    public static final String RESERVATION = getApiUrl("reservation");
    public static final String RESERVATION_STATUS = getApiUrl("reservation_status");
    public static final String ROLE = getApiUrl("role");
    public static final String ROOM = getApiUrl("room");
    public static final String ROOM_FACILITY = getApiUrl("room_facility");
    public static final String USER = getApiUrl("user");

    private static String getApiUrl(String key) {
        return resourceBundle.getString(key);
    }
}