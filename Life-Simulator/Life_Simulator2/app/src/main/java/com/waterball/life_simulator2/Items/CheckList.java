package com.waterball.life_simulator2.Items;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class CheckList extends Item {
    private String name;
    private String content;
    private String date;
    private int id;
    public static final String TITLE_STRING = "title_chekclist";
    public static final String CONTENT_STRING = "content_chekclist";
    public static final String DATE_STRING = "date_chekclist";
    //待新增
    public CheckList(String name , String content , String date ){
        super();
        this.name = name;
        this.content = content;
        this.date = date;
    }
    public CheckList(int id,String name , String content /*, String date */){
        super();
        super.id = id;
        this.name = name;
        this.content = content;
        //this.date = date;
    }
    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate(){ return date ; }

    public void setDate(String date){ this.date = date ; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
