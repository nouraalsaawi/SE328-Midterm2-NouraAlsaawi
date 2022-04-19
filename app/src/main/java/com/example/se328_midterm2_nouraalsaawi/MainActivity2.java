package com.example.se328_midterm2_nouraalsaawi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class MainActivity2 extends AppCompatActivity {
    final DatabaseHelper MyDB = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText id = findViewById(R.id.idTf);
        EditText name = findViewById(R.id.nameTf);
        EditText lname = findViewById(R.id.SurnameTf);
        EditText natID = findViewById(R.id.NatIDTf);

        Button insert = findViewById(R.id.insert);

        Button Act2ToAct1 = findViewById(R.id.Act2ToAct1);
        Button Act2ToAct3 = findViewById(R.id.Act2ToAct3);


        if(TextUtils.isEmpty(id.getText()) || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(lname.getText()) || TextUtils.isEmpty(natID.getText()))
        {

            if(TextUtils.isEmpty(id.getText()))
                id.setError(" Please insert a value");
            else if(TextUtils.isEmpty(name.getText()))
                name.setError(" Please insert a value");
            else if(TextUtils.isEmpty(lname.getText()))
                lname.setError(" Please insert a value");
            else
                natID.setError(" Please insert a value");
        }else {
            //proceed with operation
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id_data = id.getText().toString();
                    String name_data = name.getText().toString();
                    String lname_data = lname.getText().toString();
                    String natID_data = natID.getText().toString();
                    MyDB.addData(id_data, name_data, lname_data, natID_data);
                    Toasty.success(MainActivity2.this, "Successful Add", Toast.LENGTH_SHORT).show();
                    Log.d("Noura","Successful Insert");
                }
            });
        }

        Act2ToAct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
                Log.d("Noura","Successful move from act 2 to act 1");
            }
        });

        Act2ToAct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity3.class));
                Log.d("Noura","Successful move from act 2 to act 3");
            }
        });
    }
}