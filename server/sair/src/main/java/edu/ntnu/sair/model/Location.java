package edu.ntnu.sair.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by chun on 2/6/15.
 */

@Entity
@Table(name = "location")
public class Location {
    private long id;
    private Member member;
    private double longitude;
    private double latitude;
    private Calendar clientTimestamp;
    private Calendar serverTimestamp;

    private Integer checkBit = 0;

    @Column(name = "checkbit")
    public Integer getCheckBit() {
        return checkBit;
    }

    public void setCheckBit(Integer checkBit) {
        this.checkBit = checkBit;
    }

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
}
