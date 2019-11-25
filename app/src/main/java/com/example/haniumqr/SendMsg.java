package com.example.haniumqr;

public class SendMsg {
    private String user;
    private String msg;

    public SendMsg() {

    }

    SendMsg(String user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    String getUser() {
        return user;
    }

}
