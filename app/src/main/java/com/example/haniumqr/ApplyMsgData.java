package com.example.haniumqr;

public class ApplyMsgData {
    public String hotel_name;
    public String userID;
    public String hostID;

    public ApplyMsgData(String hotel_name,String userID,String hostID) {
        this.hotel_name = hotel_name;
        this.userID = userID;
        this.hostID = hostID;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getUserID() {
        return userID;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public String getHostID() {
        return hostID;
    }
}
