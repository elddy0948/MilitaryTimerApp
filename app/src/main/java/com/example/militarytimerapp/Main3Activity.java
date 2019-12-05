package com.example.militarytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    Intent intent = getIntent();
//    Integer y=intent.getIntExtra("enterYear",0);
//    Integer m = intent.getIntExtra("enterMonth",0);
//    Integer d = intent.getIntExtra("enterDate",0);
//    TextView enterWhen;
//    TextView endWhen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        enterWhen = (TextView)findViewById(R.id.enterWhen);
//        endWhen = (TextView)findViewById(R.id.endWhen);
//        enterWhen.setText(y+"년 "+m+"월 "+d+"일 ");
    }
}
