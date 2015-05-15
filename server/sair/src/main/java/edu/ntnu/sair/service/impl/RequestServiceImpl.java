package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.dao.PhotoReportDao;
import edu.ntnu.sair.dao.TextReportDao;
import edu.ntnu.sair.model.*;
import edu.ntnu.sair.service.RequestService;
import edu.ntnu.sair.service.UserService;
import edu.ntnu.sair.util.Coder;
import edu.ntnu.sair.util.Constant;
import org.apache.cxf.annotations.GZIP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.File;
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
    private TextReportDao textReportDao;
    private PhotoReportDao photoReportDao;

    @Transactional
    @Override
    public String getAllTeamLocations(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<Location> list = this.locationDao.getByTeam(member.getTeamId());
            if (list == null)
                return new Result("getAllTeamLocations", "No result").toString();
            JSONArray array = new JSONArray();
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
            return new Result("getAllTeamLocations", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getAllTeamLocations", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getLatestTeamLocations(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<Location> list = this.locationDao.getByTeamLatest(member.getTeamId());
            if (list == null) {
                return new Result("getLatestTeamLocations", "No result").toString();
            }
            JSONArray array = new JSONArray();
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
            return new Result("getLatestTeamLocations", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("getLatestTeamLocations", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getPeriodTeamLocations(String username, String uuid, String sendingTime, String startTime, String endTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<Location> list = this.locationDao.getByTeamPeriod(member.getTeamId(), Long.valueOf(startTime), Long.valueOf(endTime));
            if (list == null) {
                return new Result("getPeriodTeamLocations", "No result").toString();
            }
            JSONArray array = new JSONArray();
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
            System.out.println("Number of LocationReports sent: " + array.length());
            return new Result("getPeriodTeamLocations", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("getPeriodTeamLocations", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getAllTeamTextReports(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<TextReport> list = this.textReportDao.getByTeam(member.getTeamId());
            if (list == null) {
                return new Result("getAllTeamTextReports", "No result").toString();
            }
            JSONArray array = new JSONArray();
            for (TextReport textReport : list) {
                JSONObject obj = new JSONObject();
                Location location = textReport.getLocation();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());

                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());


                obj.put("id", textReport.getId());
                obj.put("content", textReport.getContent());
                array.put(obj);
            }
            return new Result("getAllTeamTextReports", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getAllTeamTextReports", "server error").toString();
        }
    }

    // NOTE(Torgrim): added timeoflastrequest param here for testing
    @Transactional
    @Override
    public String getLatestTeamTextReports(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<TextReport> list = this.textReportDao.getByTeamLatest(member.getTeamId());
            if (list == null) {
                return new Result("getLatestTeamTextReports", "No result").toString();
            }
            JSONArray array = new JSONArray();
            for (TextReport textReport : list) {
                JSONObject obj = new JSONObject();
                Location location = textReport.getLocation();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());

                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());

                obj.put("id", textReport.getId());
                obj.put("content", textReport.getContent());
                array.put(obj);
            }
            return new Result("getLatestTeamTextReports", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getLatestTeamTextReports", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getPeriodTeamTextReports(String username, String uuid, String sendingTime, String startTime, String endTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<TextReport> list = this.textReportDao.getByTeamPeriod(member.getTeamId(), Long.valueOf(startTime), Long.valueOf(endTime));
            if (list == null) {
                return new Result("getPeriodTeamTextReports", "No result").toString();
            }
            JSONArray array = new JSONArray();
            for (TextReport textReport : list) {
                JSONObject obj = new JSONObject();
                Location location = textReport.getLocation();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());

                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());

                obj.put("id", textReport.getId());
                obj.put("content", textReport.getContent());
                array.put(obj);
            }
            return new Result("getPeriodTeamTextReports", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getPeriodTeamTextReports", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getAllTeamPhotoReports(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<PhotoReport> list = this.photoReportDao.getByTeam(member.getTeamId());
            if (list == null) {
                return new Result("getAllTeamPhotoReports", "No result").toString();
            }
            JSONArray array = new JSONArray();
            for (PhotoReport photoReport : list) {
                JSONObject obj = new JSONObject();
                Location location = photoReport.getLocation();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());

                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());

                obj.put("id", photoReport.getId());
                obj.put("description", photoReport.getDescription());
                obj.put("direction", photoReport.getDirection());
                obj.put("filename", photoReport.getName());
                obj.put("extension", photoReport.getExtension());
                array.put(obj);
            }
            return new Result("getAllTeamPhotoReports", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getAllTeamPhotoReports", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getLatestTeamPhotoReports(String username, String uuid, String sendingTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<PhotoReport> list = this.photoReportDao.getByTeamLatest(member.getTeamId());
            if (list == null) {
                return new Result("getLatestTeamPhotoReports", "No result").toString();
            }
            JSONArray array = new JSONArray();
            for (PhotoReport photoReport : list) {
                JSONObject obj = new JSONObject();
                Location location = photoReport.getLocation();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());

                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());

                obj.put("id", photoReport.getId());
                obj.put("description", photoReport.getDescription());
                obj.put("direction", photoReport.getDirection());
                obj.put("filename", photoReport.getName());
                obj.put("extension", photoReport.getExtension());
                array.put(obj);
            }
            return new Result("getLatestTeamPhotoReports", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getLatestTeamPhotoReports", "server error").toString();
        }
    }

    @Transactional
    @Override
    public String getPeriodTeamPhotoReports(String username, String uuid, String sendingTime, String startTime, String endTime) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        try {
            Member member = this.memberDao.getByUsername(username);
            List<PhotoReport> list = this.photoReportDao.getByTeamPeriod(member.getTeamId(), Long.valueOf(startTime), Long.valueOf(endTime));
            if (list == null) {
                return new Result("getPeriodTeamPhotoReports", "No result").toString();
            }
            JSONArray array = new JSONArray();
            for (PhotoReport photoReport : list) {
                JSONObject obj = new JSONObject();
                Location location = photoReport.getLocation();
                obj.put("username", location.getMember().getUsername());
                obj.put("name", location.getMember().getName());
                obj.put("teamId", location.getMember().getTeamId());
                obj.put("timestamp", location.getClientTimestamp().getTimeInMillis());

                obj.put("latitude", location.getLatitude());
                obj.put("longitude", location.getLongitude());

                obj.put("id", photoReport.getId());

                // NOTE(Torgrim): Added title..
                obj.put("title", photoReport.getTitle());


                obj.put("description", photoReport.getDescription());
                obj.put("direction", photoReport.getDirection());
                obj.put("filename", photoReport.getName());
                obj.put("extension", photoReport.getExtension());
                System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>    Sending photo report with extension " + photoReport.getExtension());
                array.put(obj);
            }
            return new Result("getPeriodTeamPhotoReports", "success", "JSONArray", array.toString()).toString();
        } catch (Exception e) {
            return new Result("getPeriodTeamPhotoReports", "server error").toString();
        }
    }

    @Override
    public String getAllSelfLocations(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getLatestSelfLocations(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getPeriodSelfLocations(String username, String uuid, String sendingTime, String startTime, String endTime) {
        return null;
    }

    @Override
    public String getAllSelfTextReports(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getLatestSelfTextReport(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getPeriodSelfTextReports(String username, String uuid, String sendingTime, String startTime, String endTime) {
        return null;
    }

    @Override
    public String getAllSelfPhotoReports(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getLatestSelfPhotoReport(String username, String uuid, String sendingTime) {
        return null;
    }

    @Override
    public String getPeriodSelfPhotoReports(String username, String uuid, String sendingTime, String startTime, String endTime) {
        return null;
    }


    @Transactional
    @Override
    public String getPhoto(String username, String uuid, String sendingTime, String picId) {
        String checkLogin = this.userService.checkLogin(username, uuid);
        if (!checkLogin.equals("success")) {
            return checkLogin;
        }

        PhotoReport photoReport = this.photoReportDao.getById(Long.valueOf(picId));
        String photoPath = Constant.PHOTO_PATH + photoReport.getName() + "." + photoReport.getExtension();
        File photo = new File(photoPath);

        return new Result("getPhoto", "success", "file", Coder.encryptBASE64(photo)).toString();
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

    @Autowired
    public void setTextReportDao(TextReportDao textReportDao) {
        this.textReportDao = textReportDao;
    }

    @Autowired
    public void setPhotoReportDao(PhotoReportDao photoReportDao) {
        this.photoReportDao = photoReportDao;
    }
}
