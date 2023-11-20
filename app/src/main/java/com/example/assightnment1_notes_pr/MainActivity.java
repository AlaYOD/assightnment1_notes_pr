package com.example.assightnment1_notes_pr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button addButton;
    ListView listView;
    ArrayList<item> data;
    MyAdapter ma;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String DATA = "DATA";
    public static final String STRINGKEY = "myData";
    public static final String ARRAY_LENGTH = "ARRAY_LENGTH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.my_list);
        addButton = findViewById(R.id.add_button_ticket);

        data = new ArrayList<>();
        ma = new MyAdapter(MainActivity.this, data);
        listView.setAdapter(ma);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, ass_ticket.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

         prefs = getSharedPreferences(DATA, MODE_PRIVATE);

        Gson gson = new Gson();
        int size=0;
        String strLenght = prefs.getString(ARRAY_LENGTH,"");
        if(!strLenght.equals("")){
            String strLength = prefs.getString(ARRAY_LENGTH, "");

            try {
                size = Integer.parseInt(strLength);
            } catch (NumberFormatException e) {
                // Handle the case where the String is not a valid integer
                e.printStackTrace();
            }
        }



        for(int i=0;i< size;i++){
            String str = prefs.getString(i+"", "");
            if (!str.equals("")) {
                Toast.makeText(this, "Data found", Toast.LENGTH_LONG).show();

                item array_item = gson.fromJson(str, item.class);
                Collections.addAll(data, array_item);
                ma.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No found data", Toast.LENGTH_LONG).show();
            }
        }
        ma.notifyDataSetChanged();


    }

}
