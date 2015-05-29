package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.PhotoReport;

import java.util.List;

/**
 * Created by Chun on 2/10/15.
 * <br>
 * PhotoReportDao: Offer the functions for accessing the database and handling photo report.
 */

public interface PhotoReportDao {
    /**
     * Insert the photo report to the database
     *
     * @param photoReport the photo report model to be handled in the database
     */
    void add(PhotoReport photoReport);

    /**
     * Delete the photo report from the database
     *
     * @param photoReport the photo report model to be handled in the database
     */
    void delete(PhotoReport photoReport);

    /**
     * Update the photo report in the database
     *
     * @param photoReport the photo report model to be handled in the database
     */
    void update(PhotoReport photoReport);

    /**
     * Fetch the photo report by id from the database
     *
     * @param id id of the photo report
     * @return the target photo report to be returned
     */
    PhotoReport getById(long id);

    /**
     * Fetch all the photo reports by id from the database
     *
     * @return the target photo report list to be returned
     */
    List<PhotoReport> getAll();

    /**
     * Fetch the photo reports by member from the database
     *
     * @param member owner of the photo report
     * @return the target photo report list to be returned
     */
    List<PhotoReport> getByMember(Member member);

    /**
     * Fetch the latest photo report by member from the database
     *
     * @param member owner of the photo report
     * @return the target photo report to be returned
     */
    PhotoReport getByMemberLatest(Member member);

    /**
     * Fetch the photo reports by team from the database
     *
     * @param teamId owner's teamid of the photo report
     * @return the target photo report list to be returned
     */
    List<PhotoReport> getByTeam(String teamId);

    /**
     * Fetch the latest photo reports by team from the database
     *
     * @param teamId owner's teamid of the photo report
     * @return the target photo report list to be returned
     */
    List<PhotoReport> getByTeamLatest(String teamId);

    /**
     * Fetch the photo reports by team in a period of time from the database
     *
     * @param teamId    owner's teamid of the photo report
     * @param username    username of the member
     * @param startTime start of the period
     * @param endTime   end of the period
     * @return the target photo report list to be returned
     */
    List<PhotoReport> getByTeamPeriod(String teamId, String username, long startTime, long endTime);

    /**
     * Fetch the photo reports by member in a period of time from the database
     *
     * @param username    username of the member
     * @param startTime start of the period
     * @param endTime   end of the period
     * @return the target photo report list to be returned
     */
    List<PhotoReport> getByUsernamePeriod(String username, long startTime, long endTime);
}
