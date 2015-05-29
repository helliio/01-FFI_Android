package edu.ntnu.sair.controller;

import edu.ntnu.sair.service.UserService;
import edu.ntnu.sair.util.DatabasePopulator;
import org.apache.cxf.annotations.GZIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by chun on 3/5/15.
 * <br>
 * UserController: Offer the interfaces for the client to authenticate the user by REST.
 */

@RequestMapping(value = "/user")
@Controller
public class UserController {
    private UserService userService;

    /**
     * Offer the interface for the client to login a  user
     *
     * @param username  username for the user
     * @param password  password for the user
     * @param uuid      unique id for the user together with the device id
     * @param loginTime timestamp when the user login
     * @return result with string type as a json or a xml format
     */
    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam("username") String username,
            @RequestParam("uuid") String uuid,
            @RequestParam("password") String password,
            @RequestParam("loginTime") String loginTime) {
        return this.userService.login(username, uuid, password, loginTime);
    }

    /**
     * Offer the interface for the client to register a new user
     *
     * @param username username for the user
     * @param password password for the user
     * @param name     name for the user
     * @param teamId   teamId for the user
     * @return result with string type as a json or a xml format
     */
    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("teamId") String teamId) {
        return this.userService.register(username, password, name, teamId);
    }


    /*
     // Added by torgrim for testing.
     // This is used to populate and login new users in the local serve
     // database to use for testing.
     // There is also one in the reportController to
     // populate the local server database with test reports
     */
    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/registerAndLoginTestDB", method = RequestMethod.GET)
    public String populateDatabaseWithUsersAndLogin() {
        DatabasePopulator dbPopulator = new DatabasePopulator();
        dbPopulator.registerAllNewMembers(userService);
        dbPopulator.loginAllNewMembers(userService);
        return "Success";
    }


    // Added by Torgrim to get all current members of the local database
    @Scope("prototype")
    @ResponseBody
    @RequestMapping(value = "/getAllMembers", method = RequestMethod.GET)
    public String getAllMembers() {
        return userService.getAllMembers();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
