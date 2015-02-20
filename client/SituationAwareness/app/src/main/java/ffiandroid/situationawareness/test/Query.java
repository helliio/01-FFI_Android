

package ffiandroid.situationawareness.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ffiandroid.situationawareness.datahandling.CoworkerLocation;

/**
 * This Query Class is part of project: Situation Awareness
 *
 * 		Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 *
 * Participants of this file: GuoJunjun
 */
public class Query {
    /**
     * @return How many people on the database
     */
    public static int getTeamSize() {
        String query = " SELECT COUNT(*) AS rowcount FROM memberid";
        Statement st;
        ResultSet rs;
        try {
            st = ConnectMysql.getConnection().createStatement();
            rs = st.executeQuery(query);
            rs.next();
            int count = rs.getInt("rowcount");
            st.close();
            return count;
        } catch (SQLException e) {
            System.out.println("error at getTeamSize: " + e);
        }
        return 0;
    }

    /**
     * @return a list of coworkers location
     */
    public static ArrayList<CoworkerLocation> getCoworkersLocation() {
        ArrayList<CoworkerLocation> coworkersLocationArrayList = new ArrayList<>();
        String query = " SELECT * FROM location";
        Statement st;
        ResultSet rs;
        try {
            st = ConnectMysql.getConnection().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                CoworkerLocation ckl = new CoworkerLocation();
                ckl.setMemberid(rs.getInt("memberid"));
                ckl.setLatitude(rs.getDouble("latitude"));
                ckl.setLongitude(rs.getDouble("longitude"));
                coworkersLocationArrayList.add(ckl);
            }
            return coworkersLocationArrayList;
        } catch (SQLException e) {
            System.out.println("error at getCoworkersLocation: " + e);
            return null;
        }
    }

    public static void updatePosition(double latitude, double longitude, int memberid) {
        try {
            String query = " UPDATE location SET latitude='" + latitude + "', longitude='" + longitude + "' WHERE " +
                    "memberid='" + memberid + "'";

            PreparedStatement preparedStmt = ConnectMysql.getConnection().prepareStatement(query);

            preparedStmt.execute();


        } catch (Exception e) {
            System.out.println("update position error: " + e);
        }


    }

}
