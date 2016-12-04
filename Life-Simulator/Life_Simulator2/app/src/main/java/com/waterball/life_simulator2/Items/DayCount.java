package com.waterball.life_simulator2.Items;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class DayCount extends Item {
    private String name;
    private String title;
    private String date;
    public static final String TITLE_STRING = "title_daycount";
    public static final String DATE_STRING = "DATE_daycount";


    public DayCount(String name , String title , String date){
        super();
        this.date = date;
        this.name = name;
        this.title = title;
    }
    public DayCount(int id , String name , String title , String date){
        super();
        super.id = id;
        this.date = date;
        this.name = name;
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
