package com.example.assightnment1_notes_pr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ass_ticket extends AppCompatActivity {
    ExtendedFloatingActionButton btn_save;
    Spinner spinnerType;
    Spinner spinnerStatus;
    EditText note;

    ImageView imageItem;

    private String selectedSpinnerItemStatus;
    private String selectedSpinnerItemType;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String DATA = "DATA";
    public static final String ARRAY_LENGTH = "ARRAY_LENGTH";

    public static int count=0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_ticket);

        btn_save = findViewById(R.id.fabsave);
        spinnerStatus = findViewById(R.id.spinner_status);
        spinnerType = findViewById(R.id.spinner_type);
        note = findViewById(R.id.edit_add_note);
        imageItem = findViewById(R.id.image_item);



        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItemStatus = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItemType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Date date = new Date();
                // Format the date as a string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(date);

                int imageSelect;
                if (selectedSpinnerItemType.equals("hotFix")) {
                    imageSelect = R.drawable.icons8bugs64;
                } else if (selectedSpinnerItemType.equals("enhancement")) {
                    imageSelect = R.drawable.icons8enhancement50;
                } else {
                    imageSelect = R.drawable.bugimg;
                }


                item myData;
                myData= new item(selectedSpinnerItemType.toString(),formattedDate,note.getText().toString(),selectedSpinnerItemStatus.toString(),imageSelect);

                saveDataToSharedPreferences( myData, getApplicationContext());
            }
        });
    }
    private void saveDataToSharedPreferences(item myData, Context context) {
//        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences prefs = getSharedPreferences(DATA, MODE_PRIVATE);
        editor = prefs.edit();

// Retrieve the String from SharedPreferences
        String strLength = prefs.getString(ARRAY_LENGTH, "");

// Convert the String to an int
        int length = 0;  // default value in case of an error
        try {
            length = Integer.parseInt(strLength);
        } catch (NumberFormatException e) {
            // Handle the case where the String is not a valid integer
            e.printStackTrace();
        }


        count = count+length;
       Gson gson = new Gson();
        String itemString = gson.toJson(myData);
        editor.putString(count+"", itemString);
        editor.commit();
        count++;

        String jsonValue = String.valueOf(count);
        editor.putString(ARRAY_LENGTH,jsonValue);
        editor.apply();

        Toast.makeText(ass_ticket.this, "Saved :" +itemString , Toast.LENGTH_LONG).show();

        note.setText("");

        Intent intent = new Intent(ass_ticket.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}