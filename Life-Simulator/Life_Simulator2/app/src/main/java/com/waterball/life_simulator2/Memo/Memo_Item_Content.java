package com.waterball.life_simulator2.Memo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.Memo_DB_Facade;
import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;
import com.waterball.life_simulator2.User.LevelManager;

import java.util.List;

public class Memo_Item_Content extends AppCompatActivity {
    private TextView titleTX;
    private TextView contentTX;

    private static final int REQUEST_EDIT = 1;

    private List<Memo> memoList = Memo_Activity.memoList;
    private DB_Facade memo_db_facade = Memo_DB_Facade.getFacade();;
    private Memo currentMemo;  //觀看中的Memo
    private int currentPosition;  //觀看中Memo的Position
    private int currentMemoId;  //觀看中Memo的Position
    private LevelManager levelManager = LevelManager.getLevelManager();

    private void loadMemoToText(){
        Intent intent = getIntent();
        titleTX.setText(intent.getStringExtra(Memo.TITLE_STRING));
        contentTX.setText(intent.getStringExtra(Memo.CONTENT_STRING));
        currentMemoId = intent.getIntExtra(Memo.ID_STRING,-1);
        if ( currentPosition == -1 || currentMemoId == -1  )
             Log.d("myLog","currentPosition or Id Error == -1 ~");
        //載入對應Id的Memo
        for ( int i = 0 ; i < memoList.size() ; i ++ )
            if ( memoList.get(i).getId() == currentMemoId )
            {

                currentMemo = memoList.get(i);
                currentPosition = i;
                Log.d("myLog","i" +memoList.get(i) );
                Log.d("myLog","id" +memoList.get(i).getId() );
                Log.d("myLog","c" +currentMemoId );
                Log.d("myLog","i" +currentMemo );
                break;
            }
    }

    private void processViews(){
        titleTX = (TextView)findViewById(R.id.memo_title_CONTENT);
        contentTX = (TextView)findViewById(R.id.memo_content_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo__item__content);
        processViews();
        loadMemoToText();
    }

    public void editOnClick(View v){
        Intent goEditPage = new Intent(this,Memo_item_page.class);
        goEditPage.setAction("android.intent.action.Edit");
        goEditPage.putExtra(Memo.TITLE_STRING,currentMemo.getName());
        goEditPage.putExtra(Memo.CATEGORY_STRING,currentMemo.getCategory());
        goEditPage.putExtra(Memo.CONTENT_STRING,currentMemo.getContent());
        startActivityForResult(goEditPage,REQUEST_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_EDIT )
        {
            if ( resultCode == RESULT_OK )
            {
                String title = data.getStringExtra(Memo.TITLE_STRING);
                String content = data.getStringExtra(Memo.CONTENT_STRING);
                String category = data.getStringExtra(Memo.CATEGORY_STRING);
                titleTX.setText(title);
                contentTX.setText(content);
                Memo memo = new Memo( currentMemo.getId(),title,content,category);
                Log.d("myLog","收到編輯後Memo: title"+title+"\ncategory"+category+"\ncontent:"+content);
                Log.d("myLog","編輯至Memo.. id:"+currentMemo.getId()+"\nposition:"+currentPosition);
                memo_db_facade.ModifyTuple(currentMemo.getId(),memo);
                memoList.set(currentPosition,memo);

                levelManager.updateEXP(5);
            }
        }
    }

    public void deleteOnClick(View view) {
        new AlertDialog.Builder(Memo_Item_Content.this)
                .setTitle("刪除")
                .setMessage("確定要刪除 "+currentMemo.getName()+" 嗎?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = currentMemo.getId();
                        memo_db_facade.deleteTuple(id);
                        memoList.remove(currentPosition);
                        Log.d("MyLog" , "當" + id);
                        Log.d("MyLog" , "位置" + currentPosition);
                        levelManager.updateEXP(-15);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();

    }
}
