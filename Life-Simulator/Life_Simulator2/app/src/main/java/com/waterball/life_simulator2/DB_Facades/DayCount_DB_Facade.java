package com.waterball.life_simulator2.DB_Facades;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.waterball.life_simulator2.Items.Item;
import com.waterball.life_simulator2.Items.DayCount;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class DayCount_DB_Facade extends DB_Facade {
    private static DB_Facade facade;
    public static final String DAYCOUNT_NAME = "daycount_name";
    public static final String DAYCOUNT_TITLE = "daycount_title";
    public static final String DAYCOUNT_DATE = "daycunt_date";
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_TITLE = 3;
    public static final int COLUMN_CATEGORY = 4;
    public static final int COLUMN_USERID = 1;
    private DayCount_DB_Facade() {
        super("DayCount_Table");
    }

    @Override
    public void createTable() throws SQLException {
        DB_Facade user_db_facade = User_DB_Facade.getFacade();
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY ," +
                    USER_ID + " INTEGER ," +
                    DAYCOUNT_NAME + " TEXT ," +
                    DAYCOUNT_TITLE + " TEXT ,"+
                    DAYCOUNT_DATE + " TEXT ,"+
                    "FOREIGN KEY ("+USER_ID+") REFERENCES "+ user_db_facade.TABLE_NAME+" (_id))" );
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }


    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        DayCount daycount = (DayCount)item;
        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + DAYCOUNT_NAME + "," + DAYCOUNT_TITLE +","+DAYCOUNT_DATE +","+USER_ID+") values ('"
                    + daycount.getName() + "','" + daycount.getTitle() +"',"+daycount.getUserId()+")");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        DayCount daycount = (DayCount)item;
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + DAYCOUNT_NAME + "='" + daycount.getName()
                    + "'," + DAYCOUNT_TITLE + "='" + daycount.getTitle() + "',"+ DAYCOUNT_DATE + "='" + daycount.getDate() + "' WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }
    @Override //搜尋資料 傳入目標資料封裝物件
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        DayCount daycount = (DayCount)item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DAYCOUNT_NAME + " = '" + daycount.getName() + "'", null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }

    //建議使用  獨體模式 得到唯獨一個facade
    public static DB_Facade getFacade() {
        if ( facade == null )
            facade = new DayCount_DB_Facade();
        return facade;
    }
}
