package com.example.shamb.busreservation;

/**
 * Created by shamb on 4/16/2018.
 */

public class PostModel {

    private String user_id,bus_id,name,date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBooked_seats() {
        return booked_seats;
    }

    public void setBooked_seats(int booked_seats) {
        this.booked_seats = booked_seats;
    }

    private int booked_seats;

}
