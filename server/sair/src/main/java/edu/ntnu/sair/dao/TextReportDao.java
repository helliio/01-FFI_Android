package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.TextReport;

import java.util.List;

/**
 * Created by Chun on 2/10/15.
 * <br>
 * TextReportDao: Offer the functions for accessing the database and handling text report.
 */

public interface TextReportDao {
    /**
     * Insert the text report to the database
     *
     * @param textReport the text report model to be handled in the database
     */
    void add(TextReport textReport);

    /**
     * Insert the text report list to the database
     *
     * @param list the list of text report model to be handled in the database
     */
    void add(List<TextReport> list);

    /**
     * Delete the text report from the database
     *
     * @param textReport the text report model to be handled in the database
     */
    void delete(TextReport textReport);

    /**
     * Update the text report in the database
     *
     * @param textReport the text report model to be handled in the database
     */
    void update(TextReport textReport);


    /**
     * Fetch the text report by id from the database
     *
     * @param id the id of the text report
     * @return the target text report to be returned
     */
    TextReport getById(long id);

    /**
     * Fetch all the text reports from the database
     *
     * @return the target text list report to be returned
     */
    List<TextReport> getAll();

    /**
     * Fetch the text reports by member from the database
     *
     * @param member the owner of the text report
     * @return the target text list report to be returned
     */
    List<TextReport> getByMember(Member member);

    /**
     * Fetch the latest text report by member from the database
     *
     * @param member the onwer of the text report
     * @return the target text report to be returned
     */
    TextReport getByMemberLatest(Member member);

    /**
     * Fetch the text reports by team from the database
     *
     * @param teamId the owner's teamid of the text report
     * @return the target text list report to be returned
     */
    List<TextReport> getByTeam(String teamId);

    /**
     * Fetch the latest text reports by team from the database
     *
     * @param teamId the owner's teamid of the text report
     * @return the target text list report to be returned
     */
    List<TextReport> getByTeamLatest(String teamId);

    /**
     * Fetch the text reports by team in a period of time from the database
     *
     * @param teamId    the owner's teamid of the text report
     * @param startTime start of the period
     * @param endTime   end of the period
     * @return the target text list report to be returned
     */
    List<TextReport> getByTeamPeriod(String teamId, long startTime, long endTime);
}
