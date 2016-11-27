package com.waterball.life_simulator2.User;

import android.util.Log;

import com.waterball.life_simulator2.DB_Facades.User_DB_Facade;

/**
 * Created by AndroidWork on 2016/11/26.
 */

public class LevelManager {
    public static  LevelManager levelManager = null;
    public final static int[] expList = { 100,200,400,800,1600,3200,6400,12800,25600};
    private static User curUser = null;
    public LevelManager(User user){
        this.curUser = user;
    }
    public void updateEXP(int exp){
        // update into model

        curUser.setExp( curUser.getExp() + exp );
        int curExp = curUser.getExp();
        if ( curExp >= expList[curUser.getLv()] )
        {
            curExp -= expList[curUser.getLv()];
            curUser.setExp(curExp);
            curUser.setLv(curUser.getLv()+1);
            Log.d("myLog","等級提升");
        }
        Log.d("myLog", "  等級: "+curUser.getLv() + " 經驗值: "+curUser.getExp());

        // update into db

        User_DB_Facade user_db_facade = (User_DB_Facade) User_DB_Facade.getFacade();
        user_db_facade.ModifyTuple(User.getUserId(),curUser);
    }

    public int getMaxExpNeed(){
        int lv = curUser.getLv();
        Log.d("myLog","當前升級經驗值為"+expList[lv]);
        return expList[lv];
    }

    //for first use
    public static LevelManager getLevelManager(User user){
            levelManager = new LevelManager(user);
        return levelManager;
    }

    public static LevelManager getLevelManager(){
        if ( curUser == null ) {
            Log.d("myLog", new Exception("levelManager shoud pass an user in first use").toString());
            return null;
        }
        return levelManager;
    }
}
