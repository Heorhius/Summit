package com.kaptsiug.project.model;

public class Request extends General {
    private String country;
    private int month;
    private int day;

    public Request(int id, String country, int month, int day) {
        this.id = id;
        this.country = country;
        this.month = month;
        this.day = day;
    }

    public Request(String country, int month, int day) {
        this.country = country;
        this.month = month;
        this.day = day;
    }

    public String getCountry() {
        return country;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Request) {
            Request request = (Request) obj;
            return (country.compareTo(request.country) == 0) && (month == request.month)
                    && (day == request.day);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Request [country = " + country + ", month = " + month + ", day = " + day + "]";
    }

}