package edu.ntnu.sair.util;



import edu.ntnu.sair.service.ReportService;
import edu.ntnu.sair.service.UserService;
import edu.ntnu.sair.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

/**
 * Created by Torgrim on 15/04/2015.
 */
public class DatabasePopulator {

    ReportService reportService;

    // NOTE(Torgrim): linux testphoto path = /home/tbstbs/Documents/BachelorNTNU/testPhotos/
    // Windows path = p:/ProjectFFI/testPhotos
    private static final String PHOTO_PATH = "p:/ProjectFFI/testPhotos";

    private static final String TIME_IN_MILLI = Long.toString(new Date().getTime());


    public void registerAllNewMembers(UserService userService)
    {

        userService.register("to", "test", "Tore", "1");
        userService.register("pe", "test", "Per", "1");
        userService.register("bå", "test", "Bård", "1");
        userService.register("ch", "test", "Chun", "1");
        userService.register("ju", "test", "Junjun", "1");
        userService.register("si", "test", "Simen", "1");
        userService.register("mo", "test", "Morten", "1");
        userService.register("tbs", "test", "Torgrim", "1");

    }

    public void loginAllNewMembers(UserService userService)
    {

        userService.login("to", "1", "test", TIME_IN_MILLI);
        userService.login("pe", "2", "test", TIME_IN_MILLI);
        userService.login("bå", "3", "test", TIME_IN_MILLI);
        userService.login("ch", "4", "test", TIME_IN_MILLI);
        userService.login("ju", "5", "test", TIME_IN_MILLI);
        userService.login("si", "6", "test", TIME_IN_MILLI);
        userService.login("mo", "7", "test", TIME_IN_MILLI);
        userService.login("tbs", "8", "test", TIME_IN_MILLI);

    }

    public void populateLocationReports(ReportService reportService)
    {
        String result = "";
        result +=  " >>>>>    " + reportService.sendLocationReport("to", "1", TIME_IN_MILLI, "63.448243", "10.444393") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("pe", "2", TIME_IN_MILLI, "63.448250", "10.440501") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("bå", "3", TIME_IN_MILLI, "63.204424", "10.301304") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("ch", "4", TIME_IN_MILLI, "63.300404", "10.419923") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("ju", "5", TIME_IN_MILLI, "63.100233", "10.350023") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("si", "6", TIME_IN_MILLI, "63.321233", "10.401234") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("mo", "7", TIME_IN_MILLI, "63.204343", "10.372344") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("tbs", "8", TIME_IN_MILLI, "63.450031", "10.424243") + "\n";

        System.out.println("=================== Result of populating the location reports =================\n");
        System.out.println(result);
        System.out.println("=================== Result of populating the location reports =================\n");


    }

    public void populateTextReports(ReportService reportService)
    {
        String result = "";
        result += " >>>>>   " + reportService.sendTextReport("to", "1", TIME_IN_MILLI, "63.428253", "10.444384", "This is a message from user TO") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("pe", "2", TIME_IN_MILLI, "63.458221", "10.444342", "This is a message from user PE") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("bå", "3", TIME_IN_MILLI, "63.418243", "10.444353", "This is a message from user BÅ") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("ch", "4", TIME_IN_MILLI, "63.458240", "10.444312", "This is a message from user CH") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("ju", "5", TIME_IN_MILLI, "63.418210", "10.444301", "This is a message from user JU") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("si", "6", TIME_IN_MILLI, "63.428213", "10.444391", "This is a message from user SI") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("mo", "7", TIME_IN_MILLI, "63.408280", "10.444321", "This is a message from user MO") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("tbs", "8", TIME_IN_MILLI, "63.418295", "10.44455", "This is a message from user TBS") + "\n";

        System.out.println("=================== Result of populating the text reports =================\n");
        System.out.println(result);
        System.out.println("=================== Result of populating the text reports =================\n");

    }

    public void pupulatePhotoReports(ReportService reportService)
    {
        String result = "";
        result += ">>>>>    " + reportService.sendPhotoReport("to", "1", TIME_IN_MILLI, "63.382438", "9.955673", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test1.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from to, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("pe", "2", TIME_IN_MILLI, "63.439602", "10.201492", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test2.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from pe, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("bå", "3", TIME_IN_MILLI, "63.38859", "10.401993", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test3.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from bå, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("ch", "4", TIME_IN_MILLI, "63.310977", "10.204239", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test4.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from ch, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("ju", "5", TIME_IN_MILLI, "63.396586", "10.097122", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test5.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from ju, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("si", "6", TIME_IN_MILLI, "63.391051", "10.160294", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test6.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from si, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("mo", "7", TIME_IN_MILLI, "63.411956", "10.244064", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test7.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from mo, sending photo");

        result += ">>>>>    " + reportService.sendPhotoReport("tbs", "8", TIME_IN_MILLI, "63.383053", "10.226212", "0",
                                                            Coder.encryptBASE64(new File(PHOTO_PATH + "test8.jpg")), "jpg",
                                                            "This is the title for to's picture", "This is a test from tbs, sending photo");

        System.out.println("=================== Result of populating the photo reports =================\n");
        System.out.println(result);
        System.out.println("=================== Result of populating the photo reports =================\n");
    }





}




