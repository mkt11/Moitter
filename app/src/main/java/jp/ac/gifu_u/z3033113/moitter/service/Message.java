package jp.ac.gifu_u.z3033113.moitter.service;

import com.google.firebase.Timestamp;

public class Message {
    public  String user_name,userId,threadId,message;
    public Timestamp date;
    public  int good_number;
    public Message(){
        good_number=0;
    }

    public Timestamp getDate(){
        return this.date;
    }
}
