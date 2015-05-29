package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Member;

import java.util.List;

/**
 * Created by Chun on 2/10/15.
 * <br>
 * MemberDao: Offer the functions for accessing the database and handling member.
 */

public interface MemberDao {

    /**
     * Insert the member to the database
     *
     * @param member the member model to be handled in the database
     */
    void add(Member member);

    /**
     * Delete the member from the database
     *
     * @param member the member model to be handled in the database
     */
    void delete(Member member);

    /**
     * Update the member to the database
     *
     * @param member the member model to be handled in the database
     */
    void update(Member member);

    /**
     * Fetch the member by id from the database
     *
     * @param id the id of the member
     * @return the target member to be returned
     */
    Member getById(long id);

    /**
     * Fetch all the members from the database
     *
     * @return the target member list to be returned
     */
    List<Member> getAll();

    /**
     * Fetch the member by username from the database
     *
     * @param username the username of the member
     * @return the target member to be returned
     */
    Member getByUsername(String username);

    /**
     * Fetch the members by team from the database
     *
     * @param teamId the id of the member
     * @return the target member list to be returned
     */
    List<Member> getByTeamId(String teamId);

    /**
     * Fetch the members by team and username from the database
     *
     * @param teamId   the id of the member
     * @param username the username of the member
     * @return the target member list to be returned
     */
    List<Member> getTeamByTeamIdAndUsername(String teamId, String username);

}
