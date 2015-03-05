package com.aprilchun.androidtest.service;

import org.json.JSONArray;

/**
 * Created by chun on 3/4/15.
 */
public interface RequestService {
    public JSONArray getAllTeamLocations(String username, String deviceId);
}
