package com.waterball.life_simulator2.CheckList;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.waterball.life_simulator2.Items.CheckList;
import com.waterball.life_simulator2.R;

import java.util.GregorianCalendar;

public class CheckList_Add_Activity extends AppCompatActivity {
    private Button addBTN;
    private EditText titleED;
    private EditText contentED;
    private EditText dateED;
    private DatePickerDialog date;

    private void processViews(){
        addBTN = (Button)findViewById(R.id.BTNadd_checklist);
        titleED = (EditText)findViewById(R.id.titleED_checklist);
        contentED = (EditText)findViewById(R.id.contentED_checklist);
        dateED = (EditText) findViewById(R.id.dateED_checklist);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list__add_);
        processViews();
        loadDataIfEditing();
        //-----------------待更新-------------------------
        final GregorianCalendar calendar = new GregorianCalendar();
        dateED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.show();
            }
        });
        date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateED.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
            }
        }, calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) ,calendar.get(Calendar.MONDAY));

        //-----------------待更新-------------------------
    }
    private void loadDataIfEditing(){
        Intent intent = getIntent();
        if ( intent.getAction() == "android.intent.action.EDIT" )
        {
            titleED.setText(intent.getStringExtra(CheckList.TITLE_STRING));
            dateED.setText(intent.getStringExtra(CheckList.DATE_STRING));
            contentED.setText(intent.getStringExtra(CheckList.CONTENT_STRING));
        }
    }
    public void onSubmit(View view) {
        Button btn = (Button)view;
        if ( btn == addBTN)
        {
            Intent result = getIntent();
            String title = titleED.getText().toString();
            String content = contentED.getText().toString();
            String date = dateED.getText().toString();
            //判斷欄位
            if ( title.length() == 0 || content.length() == 0 || date.length() == 0)
                Toast.makeText(this, "輸入欄位不能為空~", Toast.LENGTH_SHORT).show();
            else
            {
                result.putExtra(CheckList.TITLE_STRING,title);
                result.putExtra(CheckList.CONTENT_STRING,content);
                result.putExtra(CheckList.DATE_STRING,date);
                setResult(CheckList_Activity.RESULT_OK,result);
                finish();
            }
        }
        else  // == cancelBTN
        {
            setResult(1000);  //1000代表什麼都不做 因為什麼屁都不是
            finish();
        }
    }
}
