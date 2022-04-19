package com.example.se328_midterm2_nouraalsaawi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=alaska&appid=e3d28ef2d973071a35dea631944d11db&units=metric";
    TextView temperature, humidity;
    Spinner cities;
    private TextView dateP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateP = findViewById(R.id.dateText);
        Button bttn = findViewById(R.id.chooseDate);
        Calendar c = Calendar.getInstance();
        DateFormat fmtDate = DateFormat.getDateInstance();
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateP.setText("Date is "+
                        fmtDate.format(c.getTime()));
                Log.d("Noura","Chosen Date: "+fmtDate.format(c.getTime()));
            }
        };
        bttn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button Act1ToAct2 = findViewById(R.id.Act1ToAct2);

        Act1ToAct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                Log.d("Noura","Successful move from act 1 to act 2");
            }
        });

        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        cities=findViewById(R.id.citiesSpinner);

        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q="+ cities.getSelectedItem() +"&appid=e3d28ef2d973071a35dea631944d11db&units=metric";
                weather(weatherWebserviceURL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    } //end of oncreate

    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Log.d("Noura","Response received");
            Log.d("Noura", response.toString());
            try{
                JSONObject jsonMain = response.getJSONObject("main");

                double temp=jsonMain.getDouble("temp");
                Log.d("Noura-temp:", String.valueOf(temp));
                temperature.setText(Math.round(temp) +" °C");
                humidity.setText(jsonMain.getDouble("humidity") +" %");


                JSONObject jsonSys = response.getJSONObject("sys");


                /* sub categories as JSON arrays */
                JSONArray jsonArray = response.getJSONArray("weather");
                for (int i=0; i<jsonArray.length();i++){
                    Log.d("Noura-array",jsonArray.getString(i));
                    JSONObject oneObject = jsonArray.getJSONObject(i);

                    String weather = oneObject.getString("main");
                    Log.d("Noura-w",weather);
                }


            } catch (JSONException e){
                e.printStackTrace();
                Log.e("Receive Error", e.toString());
            }

        }, error -> Log.d("Noura","Error retrieving URL")
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}