package com.example.militarytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    Button start_button;
    myDBHelper myHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText enter_name;
    EditText enter_belong;
    String name;
    String belonging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new myDBHelper(this);
        start_button = (Button)findViewById(R.id.start_button);
        enter_name = (EditText)findViewById(R.id.name_et);
        enter_belong = (EditText)findViewById(R.id.belong_et);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = enter_name.getText().toString();
                belonging = enter_belong.getText().toString();

                if(!name.isEmpty()){
                    try {
                        sqLiteDatabase = myHelper.getWritableDatabase();
                        sqLiteDatabase.execSQL("INSERT INTO contacts VALUES(null,'" + name + "','"+belonging+"');");
                        sqLiteDatabase.close();
                        enter_name.setText("");
                        enter_belong.setText("");
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context) {
            super(context, "groupdb", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE contacts (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, belong TEXT)");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
}

