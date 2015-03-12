package edu.ntnu.sair.controller;

import edu.ntnu.sair.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by torgrim on 3/5/15.
 */

@RequestMapping(value = "/request")
@Controller
public class RequestController {
    RequestService requestService;

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllTeamLocations", method = RequestMethod.POST)
    public String getAllTeamLocations(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getAllTeamLocations(username, uuid, sendingTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getLatestTeamLocations", method = RequestMethod.POST)
    public String getLatestTeamLocations(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getLatestTeamLocations(username, uuid, sendingTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPeriodTeamLocations", method = RequestMethod.POST)
    public String getPeriodTeamLocations(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return this.requestService.getPeriodTeamLocations(username, uuid, sendingTime, startTime, endTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllTeamTextReports", method = RequestMethod.POST)
    public String getAllTeamTextReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getAllTeamTextReports(username, uuid, sendingTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getLatestTeamTextReports", method = RequestMethod.POST)
    public String getLatestTeamTextReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getLatestTeamTextReports(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPeriodTeamTextReports", method = RequestMethod.POST)
    public String getPeriodTeamTextReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return this.requestService.getPeriodTeamTextReports(username, uuid, sendingTime, startTime, endTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllTeamPhotoReports", method = RequestMethod.POST)
    public String getAllTeamPhotoReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getAllTeamPhotoReports(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getLatestTeamPhotoReports", method = RequestMethod.POST)
    public String getLatestTeamPhotoReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getLatestTeamPhotoReports(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPeriodTeamPhotoReports", method = RequestMethod.POST)
    public String getPeriodTeamPhotoReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return this.requestService.getPeriodTeamPhotoReports(username, uuid, sendingTime, startTime, endTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllSelfLocations", method = RequestMethod.POST)
    public String getAllSelfLocations(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getAllSelfLocations(username, uuid, sendingTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getLatestSelfLocations", method = RequestMethod.POST)
    public String getLatestSelfLocations(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getLatestSelfLocations(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPeriodSelfLocations", method = RequestMethod.POST)
    public String getPeriodSelfLocations(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return this.requestService.getPeriodSelfLocations(username, uuid, sendingTime, startTime, endTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllSelfTextReports", method = RequestMethod.POST)
    public String getAllSelfTextReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getAllSelfTextReports(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getLatestSelfTextReports", method = RequestMethod.POST)
    public String getLatestSelfTextReport(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getLatestSelfTextReport(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPeriodSelfTextReports", method = RequestMethod.POST)
    public String getPeriodSelfTextReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return this.requestService.getPeriodSelfTextReports(username, uuid, sendingTime, startTime, endTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllSelfPhotoReports", method = RequestMethod.POST)
    public String getAllSelfPhotoReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getAllSelfPhotoReports(username, uuid, sendingTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getLatestSelfPhotoReport", method = RequestMethod.POST)
    public String getLatestSelfPhotoReport(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime) {
        return this.requestService.getLatestSelfPhotoReport(username, uuid, sendingTime);
    }


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPeriodSelfPhotoReports", method = RequestMethod.POST)
    public String getPeriodSelfPhotoReports(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return this.requestService.getPeriodSelfPhotoReports(username, uuid, sendingTime, startTime, endTime);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getPhoto", method = RequestMethod.POST)
    public String getPhoto(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("picId") String picId) {
        return this.requestService.getPhoto(username, uuid, sendingTime, picId);
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

}

