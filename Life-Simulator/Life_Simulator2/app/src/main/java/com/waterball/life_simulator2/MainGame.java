package com.waterball.life_simulator2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.waterball.life_simulator2.CheckList.CheckList_Activity;
import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.User_DB_Facade;
import com.waterball.life_simulator2.Memo.Memo_Activity;
import com.waterball.life_simulator2.Schedule.ScheduleActivity;
import com.waterball.life_simulator2.User.LevelManager;
import com.waterball.life_simulator2.User.User;

public class MainGame extends AppCompatActivity {
    public static User curUser;
    public static int userId;  // the currently playing user's id
    private Cursor userCursor;  // the currently playing user's cursor in the Database
    private LevelManager lvManager;

    private RelativeLayout parentRelative;  //背景
    private DB_Facade user_db_facade;
    private ProgressBar expBar;
    private TextView userLvTxt;
    private TextView userNameTxt;
    private CardView userDetailsCard; //the cardview that shows user details

    private void initiate(){
        user_db_facade = User_DB_Facade.getFacade();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int exp = curUser.getExp();
        int lv = curUser.getLv()+1;
        //更新經驗條
        userLvTxt.setText(lv+"");
        expBar.setMax(lvManager.getMaxExpNeed());
        expBar.setProgress(exp);
    }

    /*******  載入使用者資料 *******/
    private void loadUser(){
        //取得傳來的id
        Intent intent = getIntent();
        userId = intent.getIntExtra(User.ID_String,-1);
        Log.d("myLog","User id got : " + userId);
        if ( userId <= 0 )
            throw new SQLException("Error !! User's Id  == -1 ");

        // 尋找登入的User資料並存入Model
        userCursor = user_db_facade.getSpecifiedTupleById(userId);
        Log.d("myLog","是否找到user"+userId+","+(userCursor.getCount()==0?" 否 ":" 是 "));

        if (userCursor.getCount() == 0)
            throw new SQLException("找不到該user資料!!");
        userCursor.moveToFirst();

        // 判斷性別更換背景
        int userSex = userCursor.getInt(User_DB_Facade.COLUMN_INT_SEX);
        if ( userSex == User.BOY )
            parentRelative.setBackgroundResource(R.drawable.boy_room2);
        else
            parentRelative.setBackgroundResource(R.drawable.girl_room);

        //讀取使用者資料
        String userName = userCursor.getString(User_DB_Facade.COLUMN_TEXT_NAME);
        int userEXP = userCursor.getInt(User_DB_Facade.COLUMN_INT_EXP);
        int userLV = userCursor.getInt(User_DB_Facade.COLUMN_INT_LV);
        curUser = new User(userName,userEXP,userLV,userSex);


        lvManager = LevelManager.getLevelManager(curUser);

        //登入獎勵
        lvManager.updateEXP(25);

        //更新
        userNameTxt.setText(userName);
        userLvTxt.setText((curUser.getLv()+1)+"");
        expBar.setMax(lvManager.getMaxExpNeed());
        expBar.setProgress(curUser.getExp());
    }


    private void processView(){
        userDetailsCard = (CardView)findViewById(R.id.cardShowUserDetails_MainGame);
        userNameTxt = (TextView)findViewById(R.id.userNameShowOnCard_MainGame);
        expBar = (ProgressBar)findViewById(R.id.userExpBar_MainGame);
        userLvTxt = (TextView)findViewById(R.id.userLevel_MainGame);
        parentRelative = (RelativeLayout)findViewById(R.id.relative_layout_activity_main_game);
    }

    private void processController(){
        registerForContextMenu(userDetailsCard);  //長按卡片可獲得詳細資訊
    }

    /*****  Item 按鈕點擊 → 到各Item頁面  *******/
    public void itemOnClick(View v) {
        int id = v.getId();
        Intent goToItem = new Intent();
        goToItem.putExtra(User.ID_String,userId);
        switch(id){
            case R.id.memoBTN_GAME:
                goToItem.setClass(MainGame.this,Memo_Activity.class);
                break;
            case R.id.scheduleBTN_GAME:
                goToItem.setClass(MainGame.this, ScheduleActivity.class);
                break;
            case R.id.checklistBTN_GAME:
                goToItem.setClass(MainGame.this,  CheckList_Activity.class);
                break;
        }

        startActivity(goToItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        initiate();
        processView();
        processController();
        loadUser();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id)
        {
            case R.id.userDetailsMenu_show:
                Toast.makeText(getApplicationContext(),"玩家姓名: "+curUser.getName()+
                        "\n等級: "+(curUser.getLv()+1) + "\n經驗值: "+curUser.getExp(),Toast.LENGTH_LONG).show();
                break;
            case R.id.userDetailsMenu_setting:
                break;
        }

        return super.onContextItemSelected(item);
    }
}
