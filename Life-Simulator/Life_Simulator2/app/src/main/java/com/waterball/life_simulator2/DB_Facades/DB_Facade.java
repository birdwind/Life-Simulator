package com.waterball.life_simulator2.DB_Facades;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.waterball.life_simulator2.Items.Item;
import com.waterball.life_simulator2.MainActivity;

/**
 * Created by Administrator on 2016/11/6.
 */

public abstract class DB_Facade {
    /*
    * 如果你要開一個表格:
    * (1) 開一個class 繼承此類別 命名 Entity名稱_DB_Facade
    * (2) 實踐所有SQL資料操作方法
    * (3) 設私有建構子 傳遞表格名稱 就會幫你create table
    * (4) 在你的facade內新增方法 getFacade() 參考 User_DB_Facade
    * (5) 在MyDbHelper中的onUpgrade(..)函數中新增一條指令實踐刪除你的表格 :
    *   你的類別名稱.getFacade().dropTable();
    * */
    String TABLE_NAME;  //table's name
    static final String USER_ID = "User_Id"; // 使用者id 每個item table都是依賴user id的
    static SQLiteDatabase db = MainActivity.db;  // 資料庫本體 由 MainActivity 創建

    DB_Facade(String tableName){
        //子類別需呼叫建構式，並且傳入table名稱 EX : super("userName");
        this.TABLE_NAME = tableName;
        createTable();  //建表
    }
    //子類別需呼叫建構式，但若多傳入一個boolean就不在建構式中建表
    DB_Facade(String tableName , boolean b){
        this.TABLE_NAME = tableName;
    }
    public abstract void createTable() ;  //創建table

    //刪除TABLE
    public void dropTable(){
        if ( TABLE_NAME == null )
            throw new SQLException("刪除之table無初始化");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
    }

    public void deleteTuple(int id) {  //刪除資料 傳入該資料id
            try {
                db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id = " + id);
            }catch (SQLException err){
                Log.d("myLog",err.toString());
            }
    }

    public abstract void InsertTuple(Item item) ;  //新增資料 傳入資料封裝好的item

    public abstract void ModifyTuple(int id , Item item) ;  //修改資料 傳入id 及 修改結果之Item

    public Cursor getAll() throws SQLException{  //顯示所有資料
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }

    public abstract Cursor getSpecifiedTupleByName(Item item)  ;  //搜尋特定資料by Name

    public Cursor getSpecifiedTupleById(int id) {  //搜尋特定資料by Id
        try {
            Log.d("myLog","從"+TABLE_NAME+"取得資料 : id "+id);
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id= " + id, null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }
    public Cursor getSpecifiedTupleByUserId(int Userid) {  //搜尋特定資料by UserId
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+USER_ID+" = " + Userid, null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }

}
