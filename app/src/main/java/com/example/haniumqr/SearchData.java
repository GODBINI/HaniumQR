package com.example.haniumqr;


public class SearchData {
    String hotel_name;
    String hotel_address;
    String hotel_detail;
    String userID;
    String hostID;

    public SearchData(String hotel_name, String hotel_address,String hotel_detail,String userID,String hostID){
        this.hotel_name=hotel_name;
        this.hotel_address=hotel_address;
        this.hotel_detail=hotel_detail;
        this.userID = userID;
        this.hostID = hostID;
    }

    public String getHotel_address() {
        return hotel_address;
    }
    public String getHotel_name() { return hotel_name; }
    public String getHotel_detail() { return hotel_detail; }

    public String getUserID() {
        return userID;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }
    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }
    public void setHotel_detail(String hotel_detail) {
        this.hotel_detail = hotel_detail;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }
}
