package edu.ntnu.sair.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by chun on 2/6/15.
 */

@Entity
@Table(name = "member")
public class Member {
    private long id;
    private String username;
    private String password;
    private String name;
    private String uuid;
    private Calendar validTime;
    private String teamId;

    // NOTE(Torgrim): Added to test getting only reports not
    // already sent to the client
    private long timeOfLastLocationReportRequest = 0;
    private long timeOfLastTextReportRequest = 0;
    private long timeOfLastPhotoReportRequest = 0;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Calendar getValidTime() {
        return validTime;
    }

    public void setValidTime(Calendar validTime) {
        this.validTime = validTime;
    }

    @Column(name = "teamid")
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setTimeOfLastLocationReportRequest(long timeOfLastLocationReportRequest)
    {
        this.timeOfLastLocationReportRequest = timeOfLastLocationReportRequest;
    }
    public void setTimeOfLastTextReportRequest(long timeOfLastTextReportRequest)
    {
        this.timeOfLastTextReportRequest = timeOfLastTextReportRequest;
    }
    public void setTimeOfLastPhotoReportRequest(long timeOfLastPhotoReportRequest)
    {
        this.timeOfLastPhotoReportRequest = timeOfLastPhotoReportRequest;
    }

    @Column(name = "lastLocationRequest")
    public long getTimeOfLastLocationReportRequest()
    {
        return timeOfLastLocationReportRequest;
    }

    @Column(name = "lastTextRequest")
    public long getTimeOfLastTextReportRequest()
    {
        return timeOfLastTextReportRequest;
    }

    @Column(name = "lastPhotoRequest")
    public long getTimeOfLastPhotoReportRequest()
    {
        return timeOfLastTextReportRequest;
    }
}
