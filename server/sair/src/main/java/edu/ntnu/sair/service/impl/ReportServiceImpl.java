package edu.ntnu.sair.service.impl;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.service.ReportService;
import edu.ntnu.sair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.Calendar;

/**
 * Created by chun on 2/16/15.
 */

@WebService(endpointInterface = "edu.ntnu.sair.service.ReportService", targetNamespace = "http://service.sair.ntnu.edu/")
@Service("reportService")
public class ReportServiceImpl implements ReportService {
    private UserService userService;
    private LocationDao locationDao;


    @Transactional
    @Override
    public String sendLocationReport(String memberId, String deviceId, String longitude, String latitude, String sendingTime) {
        String checkLogin = this.userService.checkLogin(memberId, deviceId);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }
        Location location = new Location();
        location.setLongitude(Double.valueOf(longitude));
        location.setLatitude(Double.valueOf(latitude));
        Calendar calendar = Calendar.getInstance();
        location.setServerTimestamp(calendar);
        calendar.setTimeInMillis(Long.valueOf(sendingTime));
        location.setClientTimestamp(calendar);
        this.locationDao.add(location);
        return "success";
    }

    @Transactional
    @Override
    public String sendTextReport(String memberId, String deviceId, String longitude, String latitude, String content, String sendingTime) {
        return null;
    }

    @Transactional
    @Override
    public String sendPhotoReport(String memberId, String deviceId, String longitude, String latitude, String extension, String description, String direction, String sendingTime) {
        return null;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
}
