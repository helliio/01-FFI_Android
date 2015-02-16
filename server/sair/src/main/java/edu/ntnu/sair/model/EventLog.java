package edu.ntnu.sair.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by chun on 2/6/15.
 */

@Entity
@Table(name = "eventlog")
public class EventLog {
    private long id;
    private Member member;
    private Calendar timestamp;
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

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
