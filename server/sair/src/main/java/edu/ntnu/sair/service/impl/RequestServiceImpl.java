package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.service.RequestService;
import edu.ntnu.sair.service.UserService;
import org.apache.cxf.annotations.GZIP;
import org.apache.cxf.interceptor.OutInterceptors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.ws.rs.Produces;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chun on 2/16/15.
 */

@GZIP(force = true, threshold = 0)
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@WebService(endpointInterface = "edu.ntnu.sair.service.RequestService", targetNamespace = "http://service.sair.ntnu.edu/")
@Service("requestService")
public class RequestServiceImpl implements RequestService {
    private UserService userService;
    private MemberDao memberDao;
    private LocationDao locationDao;

    @Transactional
    @Override
    public String getAllTeamLocations(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return null;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<Location> list = this.locationDao.getByTeam(member.getTeamId());
            JSONArray array = new JSONArray();
            String s = "";
            for (Location location : list) {
                JSONObject obj = new JSONObject();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());
                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());
                array.put(obj);
            }
            return array.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getLatestTeamLocations(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getPeriodTeamLocations(String username, String uuid, String sendingTime, String startTime, String endTime) {
        return null;
    }

    @Override
    public String getAllTeamTexts(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getLatestTeamTexts(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getPeriodTeamTexts(String username, String uuid, String sendingTime, String startTime, String endTime) {
        return null;
    }

    @Override
    public String getAllTeamPhotos(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getLatestTeamPhotos(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getPeriodTeamPhotos(String username, String uuid, String sendingTime, String startTime, String endTime) {
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
