package edu.ntnu.sair.controller;

import edu.ntnu.sair.service.ReportService;
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

@RequestMapping(value = "/report")
@Controller
public class ReportController {
    private ReportService reportService;

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/sendLocationReport", method = RequestMethod.POST)
    public String sendLocationReport(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("longitude") String longitude,
            @RequestParam("latitude") String latitude,
            @RequestParam("sendingTime") String sendingTime) {
        return this.reportService.sendLocationReport(username, uuid, sendingTime, latitude, longitude);

    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/sendLocationReportList", method = RequestMethod.POST)
    public String sendLocationReportList(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("list") String list) {
        return this.reportService.sendLocationReportList(username, uuid, sendingTime, list);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/sendTextReport", method = RequestMethod.POST)
    public String sendTextReport(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("content") String content) {
        return this.reportService.sendTextReport(username, uuid, sendingTime, latitude, longitude, content);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/sendTextReportList", method = RequestMethod.POST)
    public String sendTextReportList(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("list") String list) {
        return this.reportService.sendTextReportList(username, uuid, sendingTime, list);
    }

    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/sendPhotoReport", method = RequestMethod.POST)
    public String sendPhotoReport(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("sendingTime") String sendingTime,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("direction") String direction,
            @RequestParam("file") String file,
            @RequestParam("extension") String extension,
            @RequestParam("description") String description) {
        return this.reportService.sendPhotoReport(username, uuid, sendingTime, latitude, longitude, direction, file, extension, description);
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
