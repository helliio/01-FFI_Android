package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.service.ReportService;
import edu.ntnu.sair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * Created by chun on 2/16/15.
 */

@Service("reportService")
public class ReportServiceImpl implements ReportService {
    private UserService userService;
    private MemberDao memberDao;
    private LocationDao locationDao;


    @Transactional
    @Override
    public String sendLocationReport(String username, String uuid, String longitude, String latitude, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }
        Location location = new Location();
        location.setMember(this.memberDao.getByUsername(username));
        location.setLongitude(Double.valueOf(longitude));
        location.setLatitude(Double.valueOf(latitude));
        Calendar calendar = Calendar.getInstance();
        location.setServerTimestamp(calendar);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(Long.valueOf(sendingTime));
        location.setClientTimestamp(calendar2);
        this.locationDao.add(location);
        return "success";
    }

    @Transactional
    @Override
    public String sendTextReport(String username, String deviceId, String longitude, String latitude, String content, String sendingTime) {
        return null;
    }

    @Transactional
    @Override
    public String sendPhotoReport(String username, String deviceId, String longitude, String latitude, String extension, String description, String direction, String sendingTime) {
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
