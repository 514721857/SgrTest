package com.sgr.dyg.test.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MyLocation {
    @Id(autoincrement = true)
    private Long id;
    private String address;
    private Double Latitude;
    private Double Longitude;
    private String title;
    private Date CreateTime;
    @Generated(hash = 307072879)
    public MyLocation(Long id, String address, Double Latitude, Double Longitude,
            String title, Date CreateTime) {
        this.id = id;
        this.address = address;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.title = title;
        this.CreateTime = CreateTime;
    }
    @Generated(hash = 1702583037)
    public MyLocation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Double getLatitude() {
        return this.Latitude;
    }
    public void setLatitude(Double Latitude) {
        this.Latitude = Latitude;
    }
    public Double getLongitude() {
        return this.Longitude;
    }
    public void setLongitude(Double Longitude) {
        this.Longitude = Longitude;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(Date CreateTime) {
        this.CreateTime = CreateTime;
    }



}
