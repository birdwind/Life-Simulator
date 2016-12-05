package com.waterball.life_simulator2.DB_Facades;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.waterball.life_simulator2.Items.CheckList;
import com.waterball.life_simulator2.Items.Item;


public class CheckList_DB_Facade extends DB_Facade {
    private static DB_Facade facade;
    public static final String CHECKLIST_NAME = "checklist_name";
    public static final String CHECKLIST_CONTENT = "checklist_content";
    public static final String CHECKLIST_DATE = "checklist_date";
    public static final int COLUMN_USERID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_CONTENT = 3;
    public static final int COLUMN_DATE = 4;
    private CheckList_DB_Facade() {
        super("CheckList_Table");
    }

    @Override
    public void createTable() throws SQLException {
        DB_Facade user_db_facade = User_DB_Facade.getFacade();
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER  ," + USER_ID + " INTEGER ," +
                    CHECKLIST_NAME + " TEXT ," + CHECKLIST_CONTENT + " TEXT ," + CHECKLIST_DATE + " TEXT ," +" "+
                    " PRIMARY KEY (_id ," + USER_ID +"))" );
        }catch (SQLException err){
            Log.d("myLog",err.toString());

        }
    }
    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        CheckList checklist = (CheckList)item;

        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + CHECKLIST_NAME + "," + CHECKLIST_CONTENT +","+CHECKLIST_DATE+","+USER_ID+") values ('"
                    + checklist.getName() + "','" + checklist.getContent() +"','"+checklist.getDate()+"',"+checklist.getUserId()+")");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        CheckList checklist = (CheckList)item;

        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + CHECKLIST_NAME + "='" + checklist.getName()
                    + "'," + CHECKLIST_CONTENT + "='" + checklist.getContent()  + "','" +
                    CHECKLIST_DATE +" ='" +checklist.getDate() +"' WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override //搜尋資料 傳入目標資料封裝物件
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        CheckList checklist = (CheckList)item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + CHECKLIST_NAME + " LIKE '" + checklist.getName() + "'", null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
        return null;
    }
    //建議使用  獨體模式 得到唯獨一個facade
    public static DB_Facade getFacade() {
        if ( facade == null )
            facade = new CheckList_DB_Facade();
        return facade;
    }
}
