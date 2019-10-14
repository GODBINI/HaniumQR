package com.example.haniumqr;

public class GuestMsgData {
    public String MsgID;
    public String Msg;

    public  GuestMsgData(String msgID, String msg) {
        this.MsgID = msgID;
        this.Msg = msg;
    }

    public String getMsg() {
        return Msg;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }
}
