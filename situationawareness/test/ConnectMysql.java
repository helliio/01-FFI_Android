package ffiandroid.situationawareness.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This ConnectMysql Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * Participants of this file: GuoJunjun
 */
public class ConnectMysql {
    /**
     * @return Connection create a MySql database connection
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://chun.no:3306/sair?";
            String user = "sair";
            String driver = "com.mysql.jdbc.Driver";
            String password = "ffiandroid2015";

            //                        Class.forName(driver).newInstance();
            Class.forName(driver);
            //            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);
            System.out.println("CONNECTION ESTABLISHED #################################");
        } catch (SQLException ex) {
            System.out.println("CONNECTION ERROR: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Feilet under driverlasting: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("CONNECTION COULD NOT BE ESTABLISHED.");
            e.printStackTrace();
        }
        return con;
    }
}
