package com.example.militarytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    Integer y;
    Integer m;
    Integer d;
    TextView enterWhen;
    TextView endWhen;
    TextView textView_dDay;
    Integer dDay;
    Calendar endDay;
    Integer test_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //  Main2 에서 넘긴 값 받음
        Intent intent = getIntent();
        y = intent.getIntExtra("enterYear",0);
        m = intent.getIntExtra("enterMonth",0);
        d = intent.getIntExtra("enterDate",0);
        // 텍스트 뷰 설정
        enterWhen = (TextView)findViewById(R.id.enterWhen); //입대일
        endWhen = (TextView)findViewById(R.id.endWhen); //전역일
        textView_dDay = (TextView)findViewById(R.id.dDay); // d-Day

        enterWhen.setText(y+"년 "+m+"월 "+d+"일");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        endDay = endDay(y,m,d);
        test_int = endDay.get(Calendar.YEAR);
        //endWhen.setText(simpleDateFormat.format(endDay.getTime()));
        endWhen.setText(test_int.toString());

        dDay = countDday(y,m,d);
        textView_dDay.setText(dDay.toString());


    }
    //dDay 계산하는 함수.
    public int countDday(Integer myear, Integer mmonth, Integer mdate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar todayCal = Calendar.getInstance(); // 오늘의 날짜를 가져온다.
            Calendar dDayCal = Calendar.getInstance(); //오늘 날짜를 가져와서 변경
            dDayCal.set(myear, mmonth-1, mdate); // month는 1을 빼준다.
            Log.e("테스트", simpleDateFormat.format(todayCal.getTime()) + "");
            Log.e("테스트", simpleDateFormat.format(dDayCal.getTime()) + "");
            long today = todayCal.getTimeInMillis() / 86400000; //(24 * 60 * 60 * 1000) ms 단위이므로 1000곱해줌
            long dday = dDayCal.getTimeInMillis() / 86400000;
            long count = dday - today;
            return (int) count;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 전역일을 계산해주는 함수.
    public Calendar endDay(Integer enter_Year, Integer enter_Month, Integer enter_Date){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar enterCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            enterCal.set(enter_Year,enter_Month,enter_Date);
            //1년 6개월 기준.
            enterCal.add(Calendar.YEAR,1);
            enterCal.add(Calendar.MONTH,5); //month 는 1을 빼준다.
            simpleDateFormat.format(enterCal.getTime());
            return enterCal;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
