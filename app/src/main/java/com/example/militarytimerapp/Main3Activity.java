package com.example.militarytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    TextView progress_text;
    Integer dDay,total_Day;
    TextView test_text;

    Calendar endDay;

    Integer end_year, end_Month, end_Date;
    double my_progress;
    Integer did_day;
    Integer int_progressday;

    AlertDialog.Builder alertDialog;
    View progress_view;


    SeekBar seekBar;
    Chronometer chronometer;

    Button show_progress;
    Button show_chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //  Main2 에서 넘긴 값 받음
        try {
            Intent intent = getIntent();
            y = intent.getIntExtra("enterYear", 0);
            m = intent.getIntExtra("enterMonth", 0);
            d = intent.getIntExtra("enterDate", 0);

            // 텍스트 뷰 설정
            enterWhen = (TextView) findViewById(R.id.enterWhen); //입대일
            endWhen = (TextView) findViewById(R.id.endWhen); //전역일
            textView_dDay = (TextView) findViewById(R.id.dDay); // d-Day

            show_progress = (Button) findViewById(R.id.show_progress);


            enterWhen.setText(y + "년 " + m + "월 " + d + "일");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            endDay = endDay(y, m, d);
            endWhen.setText(simpleDateFormat.format(endDay.getTime()));

            end_year = endDay.get(Calendar.YEAR);
            end_Month = endDay.get(Calendar.MONTH);
            end_Date = endDay.get(Calendar.DAY_OF_MONTH);

            total_Day = totalDay(y, m, d, end_year, end_Month, end_Date);
            dDay = countDday(end_year, end_Month, end_Date);
            did_day = total_Day - dDay;
            textView_dDay.setText("현재 복무일 : " + did_day + "\n남은 복무일 : " + dDay.toString() + "\n총 복무일 : " + total_Day.toString());

            my_progress = ((double) did_day / (double) total_Day) * 100;
            int_progressday = (int) my_progress;
            test_text.setText(int_progressday.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        /////////////////////////////////////////////////////////////////////

        //복무 진행도 seekbar로 표현하기
        show_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_view = (View)View.inflate(Main3Activity.this,R.layout.show_progress,null);
                alertDialog = new AlertDialog.Builder(Main3Activity.this);
                progress_text = (TextView)progress_view.findViewById(R.id.progress_text);
                seekBar = (SeekBar)progress_view.findViewById(R.id.seek_bar);
                alertDialog.setTitle("복무율");
                alertDialog.setView(progress_view);
                new Thread(){
                    public void run(){
                        for(int i=seekBar.getProgress();i<int_progressday;i++){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    seekBar.setProgress(seekBar.getProgress()+1);
                                    progress_text.setText(seekBar.getProgress()+"%");
                                }
                            });
                            SystemClock.sleep(100);
                        }
                    }
                }.start();
                alertDialog.setNegativeButton("닫기",null);
                alertDialog.show();
            }
        });

        chronometer = (Chronometer)findViewById(R.id.cm);
        final long a = (long)did_day;
        final long b = (long)total_Day;
        long one_day = -24*60*60*1000;
        chronometer.setBase(SystemClock.elapsedRealtime()+((one_day*a)/b));
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometer.start();
            }
        });


    }
    //dDay 계산하는 함수.
    public int countDday(Integer myear, Integer mmonth, Integer mdate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar todayCal = Calendar.getInstance(); // 오늘의 날짜를 가져온다.
            Calendar dDayCal = Calendar.getInstance(); //오늘 날짜를 가져와서 변경
            dDayCal.set(myear, mmonth, mdate); // month는 1을 빼준다.
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
            enterCal.set(enter_Year,enter_Month,enter_Date);
            //1년 6개월 15일 기준.
            enterCal.add(Calendar.YEAR,1);
            enterCal.add(Calendar.MONTH,5);//month 는 1을 빼준다.
            enterCal.add(Calendar.DAY_OF_MONTH,15);
            simpleDateFormat.format(enterCal.getTime());
            return enterCal;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 총복무일 계산해주는 함수.
    public int totalDay(Integer enteryear, Integer entermonth, Integer enterdate, Integer endyear, Integer endmonth, Integer enddate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar startDay = Calendar.getInstance(); // 오늘의 날짜를 가져온다.
            Calendar endDay = Calendar.getInstance(); //오늘 날짜를 가져와서 변경
            startDay.set(enteryear, entermonth, enterdate); // month는 1을 빼준다.
            endDay.set(endyear,endmonth+1, enddate+1);
            Log.e("테스트", simpleDateFormat.format(startDay.getTime()) + "");
            Log.e("테스트", simpleDateFormat.format(endDay.getTime()) + "");
            long start = startDay.getTimeInMillis() / 86400000; //(24 * 60 * 60 * 1000) ms 단위이므로 1000곱해줌
            long end = endDay.getTimeInMillis() / 86400000;
            long count = end - start;
            return (int) count;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}