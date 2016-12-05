package com.waterball.life_simulator2.CheckList;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.waterball.life_simulator2.Items.CheckList;
import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;

public class CheckList_Add_Reset_Activity extends AppCompatActivity {

    private Button addBTN;
    private EditText titleED;
    private EditText contentED;
    private EditText dateED;
    private DatePickerDialog date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list__add_);

    }


}
