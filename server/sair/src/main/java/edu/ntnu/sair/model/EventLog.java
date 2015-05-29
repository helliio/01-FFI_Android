package edu.ntnu.sair.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by chun on 2/6/15.
 * <br>
 * EventLog: Eventlog model is used to store the log of events.
 */

@Entity
@Table(name = "eventlog")
public class EventLog {
    private long id;
    private Member member;
    private Calendar clientTimestamp;
    private Calendar serverTimestamp;
    private String description;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "memberid")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Column(name = "clienttimestamp")
    public Calendar getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(Calendar clientTimestamp) {
        this.clientTimestamp = clientTimestamp;
    }

    @Column(name = "servertimestamp")
    public Calendar getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(Calendar serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
