package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Chun on 2/10/15.
 * <br>
 * LocationDao: Offer the functions for accessing the database and handling location.
 */

public interface LocationDao {
    /**
     * Insert the location to the database
     *
     * @param location the location model to be handled in the database
     */
    void add(Location location);

    /**
     * Insert the location list to the database
     *
     * @param list the location list to be handled in the database
     */
    void add(List<Location> list);

    /**
     * Delete the location from the database
     *
     * @param location the location model to be handled in the database
     */
    void delete(Location location);

    /**
     * Update the location in the database
     *
     * @param location the location model to be handled in the database
     */
    void update(Location location);

    /**
     * Fetch the location by id from the database
     *
     * @param id the id of the location
     * @return location the target location to be returned
     */
    Location getById(long id);

    /**
     * Fetch all the locations from the database
     *
     * @return list the target location list to be returned
     */
    List<Location> getAll();

    /**
     * Fetch all the locations by member from the database
     *
     * @param member the owner of the location
     * @return list the target location list to be returned
     */
    List<Location> getByMember(Member member);

    /**
     * Fetch the latest location by member from the database
     *
     * @param member the owner of the location
     * @return location the target location to be returned
     */
    Location getByMemberLatest(Member member);

    /**
     * Fetch the locations by team from the database
     *
     * @param teamId the owner's team of the location
     * @return list the target location list to be returned
     */
    List<Location> getByTeam(String teamId);

    /**
     * Fetch the latest locations by team from the database
     *
     * @param teamId the owner's team of the location
     * @return list the target location list to be returned
     */
    List<Location> getByTeamLatest(String teamId);

    /**
     * Fetch the locations by team in a period of time from the database
     *
     * @param teamId    the owner's team of the location
     * @param startTime start of the period
     * @param endTime   end of the period
     * @return list the target location list to be returned
     */
    List<Location> getByTeamPeriod(String teamId, long startTime, long endTime);

    /**
     * Fetch the latest location by team member in a period of time from the database
     *
     * @param teamMemberUsername the username of the team member
     * @param startTime          start of the period
     * @param endTime            end of the period
     * @return list the target location list to be returned
     */
    Location getLastLocationForTeamMember(String teamMemberUsername, long startTime, long endTime);
}
