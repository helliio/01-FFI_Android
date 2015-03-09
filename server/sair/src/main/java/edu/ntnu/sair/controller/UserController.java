package edu.ntnu.sair.controller;

import edu.ntnu.sair.service.UserService;
import org.apache.cxf.annotations.GZIP;
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


@GZIP(force = true, threshold = 0)
@RequestMapping(value = "/user")
@Controller
public class UserController {

    private UserService userService;


    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String addElement(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("password") String password,
            @RequestParam("loginTime") String loginTime) {
        this.userService.login(username, uuid, password, loginTime);
        return "success";

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
