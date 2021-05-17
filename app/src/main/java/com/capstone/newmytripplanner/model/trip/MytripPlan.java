package com.capstone.newmytripplanner.model.trip;

import com.capstone.newmytripplanner.model.location.Documents;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.Serializable;
import java.util.List;

public class MytripPlan implements Serializable {
    String date;
    String userId;
    List<Documents> location;

    public MytripPlan () {}

    public MytripPlan(String date, String userId, List<Documents> location) {
        this.date = date;
        this.userId = userId;
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Documents> getLocation() {
        return location;
    }

    public void setLocation(List<Documents> location) {
        this.location = location;
    }
}
