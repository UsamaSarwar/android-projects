package com.example.aamir.tablayout.Modals;

/**
 * Created by Aamir on 8/2/2017.
 */

public class Reminder {

    int ID;
    String Subject;
    String Date;
    String Time;
    int flag;
    String message;

    public Reminder(int ID, String subject, String time, String date, String message,int flag) {
        this.ID = ID;
        Subject = subject;
        Date = date;
        Time = time;
        this.flag = flag;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
