package com.example.se328_midterm2_nouraalsaawi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity3 extends AppCompatActivity {
    final DatabaseHelper MyDB = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText dataInput = findViewById(R.id.dataInput);

        Button view = findViewById(R.id.view);
        Button del = findViewById(R.id.delete);

        Button Act3ToAct2 = findViewById(R.id.Act2ToAct1);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = MyDB.getSpecificResult(dataInput.getText().toString());

                String cursorId = cursor.getString(0);
                String cursorName = cursor.getString(1);
                String cursorLname = cursor.getString(2);
                String cursorNatId = cursor.getString(3);

                String searchForChar=cursorId+cursorName+cursorLname+cursorNatId.toCharArray();
                // i dont have enough time to implement this, but the idea is to concatenate all column values into one string, turn the data to an array of chars as well
                // then in a while (result), result is a boolean set to true at first, search through that string for a specific character that is inserted by the user
                // then loop through the concatenated string of characters, for ex if searchForChar[0] = data[n], result=true, delete that user. else result=false and search through another user




                MyDB.deleteData(dataInput.getText().toString());

                Log.d("Noura","Successful delete");
                Toasty.info(getBaseContext(), "Successful Delete of: "+cursorId + " " + cursorName + " " + cursorLname+" "+cursorNatId, Toast.LENGTH_SHORT, true).show();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = MyDB.ViewUsers();
                StringBuffer buffer = new StringBuffer();

                while(cur.moveToNext()){
                    buffer.append("ID: "+cur.getString(0)+ "\n");
                    buffer.append("Name: "+cur.getString(1)+ "\n");
                    buffer.append("Last Name: "+cur.getString(2)+ "\n\n");
                    buffer.append("National ID: "+cur.getString(3)+ "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
                builder.setCancelable(true);
                builder.setTitle("All Users");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Act3ToAct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this, MainActivity2.class));
            }
        });
    }

}