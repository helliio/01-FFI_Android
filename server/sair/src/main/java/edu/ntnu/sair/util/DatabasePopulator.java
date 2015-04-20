package edu.ntnu.sair.util;



import edu.ntnu.sair.service.ReportService;
import edu.ntnu.sair.service.UserService;
import edu.ntnu.sair.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Torgrim on 15/04/2015.
 */
public class DatabasePopulator {

    ReportService reportService;


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

        userService.login("to", "1", "test", "1429476481680");
        userService.login("pe", "2", "test", "1429476481680");
        userService.login("bå", "3", "test", "1429476481680");
        userService.login("ch", "4", "test", "1429476481680");
        userService.login("ju", "5", "test", "1429476481680");
        userService.login("si", "6", "test", "1429476481680");
        userService.login("mo", "7", "test", "1429476481680");
        userService.login("tbs", "8", "test", "1429476481680");

    }

    public void populateLocationReports(ReportService reportService)
    {
        String result = "";
        result +=  " >>>>>    " + reportService.sendLocationReport("to", "1", "1429476481683", "63.448243", "10.444393") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("pe", "2", "1429476481684", "63.448250", "10.440501") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("bå", "3", "1429476481685", "63.204424", "10.301304") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("ch", "4", "1429476481686", "63.300404", "10.419923") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("ju", "5", "1429476481687", "63.100233", "10.350023") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("si", "6", "1429476481688", "63.321233", "10.401234") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("mo", "7", "1429476481689", "63.204343", "10.372344") + "\n";
        result += " >>>>>    " + reportService.sendLocationReport("tbs", "8", "1429476481690", "63.450031", "10.424243") + "\n";

        System.out.println("=================== Result of populating the location reports =================\n");
        System.out.println(result);
        System.out.println("=================== Result of populating the location reports =================\n");


    }

    public void populateTextReports(ReportService reportService)
    {
        String result = "";
        result += " >>>>>   " + reportService.sendTextReport("to", "1", "1429118974892", "63.428253", "10.444384", "This is a message from user TO") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("pe", "2", "1429118974563", "63.458221", "10.444342", "This is a message from user PE") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("bå", "3", "1429118974123", "63.418243", "10.444353", "This is a message from user BÅ") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("ch", "4", "1429118974313", "63.458240", "10.444312", "This is a message from user CH") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("ju", "5", "1429118974903", "63.418210", "10.444301", "This is a message from user JU") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("si", "6", "1429118974125", "63.428213", "10.444391", "This is a message from user SI") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("mo", "7", "1429118974321", "63.408280", "10.444321", "This is a message from user MO") + "\n";
        result += " >>>>>   " + reportService.sendTextReport("tbs", "8", "1429118974124", "63.418295", "10.44455", "This is a message from user TBS") + "\n";

        System.out.println("=================== Result of populating the text reports =================\n");
        System.out.println(result);
        System.out.println("=================== Result of populating the text reports =================\n");

    }



}




