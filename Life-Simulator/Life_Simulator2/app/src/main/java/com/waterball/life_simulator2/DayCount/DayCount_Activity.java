package com.waterball.life_simulator2.DayCount;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.Items.DayCount;
import com.waterball.life_simulator2.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class DayCount_Activity extends AppCompatActivity {
    private ListView daycountListView;
    private List<DayCount> daycountList;
    private Button btn_day_add;
    private Calendar m_Calender = Calendar.getInstance();

    //時間
    private String leftdate;
    private String nowdate;

    //private DayCountAdapter checkAdapter;
    private DB_Facade DayCount_DB_Facade;
    private static final int ADD_REQUEST = 0;

    /*LayoutInflater inflater=LayoutInflater.from(DayCount_Activity.this);
    final View v=inflater.inflate(R.layout.activity_day_count__add_,null);
*/

    class DayCountAdapter extends BaseAdapter {
        private Context context;
        LayoutInflater inflater;
        public DayCountAdapter(Context c){
            context = c;
            inflater = LayoutInflater.from(this.context);
        }
        @Override
        public int getCount() {
            return daycountList.size();
        }
        @Override
        public Object getItem(int i)
        {
            return daycountList.get(i);
        }
        @Override
        public long getItemId(int i) {

            return daycountList.get(i).getId();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            convertView = inflater.inflate(R.layout.activity_checklist_itemlist , null);
            DayCount daycountlist;
            daycountlist = (DayCount) getItem(position);
            TextView texv1 = (TextView)convertView.findViewById(R.id.title_txt);
            TextView texv2 = (TextView)convertView.findViewById(R.id.date_txt);
            texv1.setText(daycountlist.getName());
            texv2.setText(daycountlist.getDate());

            return convertView;
        }
    }

    public void getLeftDate(String date){

        //取得系統日期
        SimpleDateFormat nowsystemdate=new SimpleDateFormat("yyyy/MM/dd");
        nowdate= nowsystemdate.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_count_);

        btn_day_add = (Button) findViewById(R.id.add_daycount_BTN);
        btn_day_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(DayCount_Activity.this);
                View v=inflater.inflate(R.layout.activity_day_count__add_,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DayCount_Activity.this);
                builder.setTitle("新增事件");
                builder.setView(v);
                builder.setCancelable(true);
                builder.create().show();
                EditText edt_title_add = (EditText) v.findViewById(R.id.title_EDT);
                final EditText edt_date_add = (EditText) v.findViewById(R.id.date_EDT);
                Button btn_add = (Button) v.findViewById(R.id.add_BTN);

                //跳出日期選擇
                edt_date_add.setOnClickListener(this);

                DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        m_Calender.set(Calendar.YEAR, year);
                        m_Calender.set(Calendar.MONTH, month);
                        m_Calender.set(Calendar.DAY_OF_MONTH, day);
                        String myFormat = "yyyy/MM/dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                        edt_date_add.setText(sdf.format(m_Calender.getTime()));
                    }
                };


                    DatePickerDialog dialog =
                            new DatePickerDialog(DayCount_Activity.this,datepicker,
                                m_Calender.get(Calendar.YEAR),
                                m_Calender.get(Calendar.MONTH),
                                m_Calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();


                //按確認新增
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        });

    }


}