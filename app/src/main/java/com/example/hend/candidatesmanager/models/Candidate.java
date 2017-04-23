package com.example.hend.candidatesmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hend on 4/22/2017.
 */

public class Candidate extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String mobile;
    private String position;
    private String address;
    private String photo;
    private String cvName;
    private String otherPaper;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getOtherPaper() {
        return otherPaper;
    }

    public void setOtherPaper(String otherPaper) {
        this.otherPaper = otherPaper;
    }

}
