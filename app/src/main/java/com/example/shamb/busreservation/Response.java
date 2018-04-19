package com.example.shamb.busreservation;

/**
 * Created by shamb on 3/19/2018.
 */

import java.util.List;

/**
 * Created by Manjeet Singh on 8/2/2017.
 */

public class Response {

    private List<Model> data;
    private String response;

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}