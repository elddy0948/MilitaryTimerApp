package com.example.militarytimerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    RadioGroup military_group;
    ImageView military_image;
    Button date_select;
    View date_view;
    TextView date_text;
    Integer y,m,d;
    String string_y;
    String string_m;
    String string_d;
    Button gotomain3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        military_group = (RadioGroup)findViewById(R.id.military_group);
        military_image = (ImageView)findViewById(R.id.military_image);
        date_select = (Button)findViewById(R.id.go_to_datepicker);
        date_text = (TextView)findViewById(R.id.text_date);
        gotomain3 = (Button)findViewById(R.id.gotomain3);

        military_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.marine){
                    military_image.setImageResource(R.mipmap.marine_icon);
                }
                else if(checkedId == R.id.navy){
                    military_image.setImageResource(R.mipmap.navy_icon);
                }
                else if(checkedId == R.id.airforce){
                    military_image.setImageResource(R.mipmap.airforce_icon);
                }
            }
        });
        date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_view = (View)View.inflate(Main2Activity.this,R.layout.enter_date,null);
                AlertDialog.Builder dv = new AlertDialog.Builder(Main2Activity.this);
                dv.setTitle("입대 날짜 선택");
                dv.setView(date_view);
                dv.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker datePicker = (DatePicker)date_view.findViewById(R.id.date_picker);
                        //18개월
                        y = datePicker.getYear();
                        m = datePicker.getMonth() + 1;
                        d = datePicker.getDayOfMonth();
                        date_text.setText("입대일 : "+y.toString()+"년 "+m.toString()+"월 "+d.toString()+"일");
                    }
                });
                dv.setNegativeButton("닫기",null);
                dv.show();
            }
        });
        gotomain3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                    intent.putExtra("enterYear", y);
                    intent.putExtra("enterMonth", m);
                    intent.putExtra("enterDate", d);
                    startActivity(intent);
                }catch (Exception e){

                }
            }
        });
    }
}
