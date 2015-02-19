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
}
