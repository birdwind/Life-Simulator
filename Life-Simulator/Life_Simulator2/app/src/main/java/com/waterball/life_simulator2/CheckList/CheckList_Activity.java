package com.waterball.life_simulator2.CheckList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.waterball.life_simulator2.DB_Facades.CheckList_DB_Facade;
import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.Memo_DB_Facade;
import com.waterball.life_simulator2.Items.CheckList;
import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;
import com.waterball.life_simulator2.User.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.waterball.life_simulator2.MainGame.userId;

public class CheckList_Activity extends AppCompatActivity {
    private ListView checkListView;
    private static final int ADD_REQUEST = 0;
    private List<CheckList> checkList;
    private CheckListAdapter checkAdapter;
    private DB_Facade check_db_facade;
    private int currentPosition;
    private int currentCheckListId;
    private static final int REQUEST_EDIT = 1;
    private CheckList currentCheckList;
    /*待新增新的 adapter  包含 checkbox textview1 ->標題   textview2 -> 日期*/
    class CheckListAdapter extends BaseAdapter {
        private Context context;
        LayoutInflater inflater;
        public CheckListAdapter(Context c){
            context = c;
            inflater = LayoutInflater.from(this.context);
        }
        @Override
        public int getCount() {
            return checkList.size();
        }
        @Override
        public Object getItem(int i)
        {
            return checkList.get(i);
        }
        @Override
        public long getItemId(int i) {

            return checkList.get(i).getId();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            convertView = inflater.inflate(R.layout.activity_checklist_itemlist , null);
            CheckList checklist;
            checklist = (CheckList)getItem(position);
            TextView texv1 = (TextView)convertView.findViewById(R.id.title_txt);
            TextView texv2 = (TextView)convertView.findViewById(R.id.date_txt);
            texv1.setText(checklist.getName());
            texv2.setText(checklist.getDate());


            return convertView;
        }
    }
    private void initiate(){
        check_db_facade = CheckList_DB_Facade.getFacade();
        checkAdapter = new CheckListAdapter(this);
        checkList = Collections.checkedList( new ArrayList<CheckList>() , CheckList.class);
    }
    private void processViews(){
        checkListView = (ListView)findViewById(R.id.listV_Check);
    }

    private void processControl(){

        checkListView.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
        checkListView.setAdapter(checkAdapter);

        //待新增
        checkListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CheckList checklist = checkList.get(position);
                Intent intent = getIntent();
                currentPosition = position;
                currentCheckList = checklist;
                Log.d("MyLog" , "當" + checklist);
                Log.d("MyLog" , "數量" + checkList.size());
                Log.d("MyLog" , "位置" + currentCheckList.getId());
                new AlertDialog.Builder(CheckList_Activity.this)
                        .setTitle("-----刪除事項-----")
                        .setMessage("----確定要刪除 " + currentCheckList.getName()+" 嗎?" )
                        .setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = currentCheckList.getId();
                                Log.d("MyLog" , "當" + id);
                                Log.d("MyLog" , "位置" + currentCheckList);
                                check_db_facade.deleteTuple(id);
                                checkList.remove(currentPosition);
                                checkAdapter.notifyDataSetChanged();

                            }
                        })
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goToCheckListItemPage = new Intent(CheckList_Activity.this,CheckList_Add_Activity.class);
                                goToCheckListItemPage.setAction("android.intent.action.EDIT");
                                goToCheckListItemPage.putExtra(CheckList.TITLE_STRING,currentCheckList.getName());
                                goToCheckListItemPage.putExtra(CheckList.CONTENT_STRING,currentCheckList.getContent());
                                goToCheckListItemPage.putExtra(CheckList.DATE_STRING,currentCheckList.getDate());
                                startActivityForResult(goToCheckListItemPage,REQUEST_EDIT);
                            }
                        }).show();


            }
        });

    }
    private void loadCheckLists(){
        int userId = getIntent().getIntExtra(User.ID_String,-1);
        CheckList checklist;
        Log.d("myLog","取得user"+userId+"的所有checklist....");
        Cursor checklistCursor = check_db_facade.getSpecifiedTupleByUserId(userId);
        if ( checklistCursor.getCount() == 0 )
            Log.d("myLog","此使用者沒有checklist資料");
        else {
            checklistCursor.moveToFirst();

            do {
                checklist = new CheckList(checklistCursor.getString(CheckList_DB_Facade.COLUMN_NAME),
                        checklistCursor.getString(CheckList_DB_Facade.COLUMN_CONTENT)
                        ,checklistCursor.getString(CheckList_DB_Facade.COLUMN_DATE));
                checkList.add(checklist);
            } while (checklistCursor.moveToNext());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_);
        initiate();
        loadCheckLists();
        processViews();
        processControl();
        checkListView.setAdapter(checkAdapter);
    }

    public void addCheckListOnClick (View v){
        Intent goEditPage = new Intent(this,CheckList_Add_Activity.class);
        goEditPage.setAction("android.intent.action.EDIT");
        startActivityForResult(goEditPage,ADD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == ADD_REQUEST )
        {
            if ( resultCode == RESULT_OK )
            {
                CheckList checklist = new CheckList(data.getStringExtra(CheckList.TITLE_STRING)
                        , data.getStringExtra(CheckList.CONTENT_STRING),data.getStringExtra(CheckList.DATE_STRING));
                check_db_facade.InsertTuple(checklist);
                checkList.add(checklist);
                checkAdapter.notifyDataSetChanged();
            }
        }
    }

    }
