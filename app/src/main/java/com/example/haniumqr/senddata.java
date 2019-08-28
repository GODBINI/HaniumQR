package com.example.haniumqr;

public class senddata {
    public String firebasekey;
    public String userName;
    public int HostGuest; //1이면 호스트 2면 게스트

    public senddata(String userName,int HostGuest) {
        this.userName = userName;
        this.HostGuest = HostGuest;
    }

}

