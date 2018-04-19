package com.example.shamb.busreservation;

/**
 * Created by shamb on 3/19/2018.
 */

public class Model // dict ke andr vale keys ke liye value dene ke liye.
{
    private String source;
    private String destination;
    private int cost;
    private String name;
    private String reached_Time;
    private String start_Time;
    private int seats_left;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getSource() {
        return source;
    }

    public int getSeats_left() {
        return seats_left;
    }

    public void setSeats_left(int seats_left) {
        this.seats_left = seats_left;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReached_Time() {
        return reached_Time;
    }

    public void setReached_Time(String reached_Time) {
        this.reached_Time = reached_Time;
    }

    public String getStart_Time() {
        return start_Time;
    }

    public void setStart_Time(String start_Time) {
        this.start_Time = start_Time;
    }
}