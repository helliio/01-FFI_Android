package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.service.RequestService;
import edu.ntnu.sair.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chun on 2/16/15.
 */

@Service("requestService")
public class RequestServiceImpl implements RequestService {
    private UserService userService;
    private MemberDao memberDao;
    private LocationDao locationDao;

    @Transactional
    @Override
    public String getTeamMembers(String username, String uuid, String sendingTime) {
        return null;
    }

    @Transactional
    @Override
    public String getTeamLocations(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return null;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<Location> list = this.locationDao.getByTeam(member.getTeamId());
            JSONArray array = new JSONArray();
            for (Location location : list) {
                JSONObject obj = new JSONObject();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());
                obj.put("longitude", location.getLongitude());
                obj.put("latitude", location.getLatitude());
                array.put(obj);
            }
            return array.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public String getLocationsByMember(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return null;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<Location> list = this.locationDao.getByMember(member);
            JSONObject resultObj = new JSONObject();
            for (Location location : list) {
                JSONObject locationObj = new JSONObject();
                locationObj.put("longitude", String.valueOf(location.getLongitude()));
                locationObj.put("latitude", String.valueOf(location.getLatitude()));
                resultObj.put(String.valueOf(location.getId()), locationObj);
            }
            System.out.println(resultObj);
            System.out.println(resultObj.toString());
            return resultObj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public String getLocationsByPeriod(String username, String uuid, String startTime, String endTime, String sendingTime) {
        return null;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
}
