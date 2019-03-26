package com.alanmbennett.petcare;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddReminderActivity extends AppCompatActivity implements HttpPostCallback {

    Button createReminder;
    Button dateButton;
    Button timeButton;
    AlertDialog.Builder dateBuilder;
    AlertDialog.Builder timeBuilder;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText title;
    EditText notes;
    CheckBox reoccurring;
    String date;
    String time;
    Spinner petSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Pet> listPet = (ArrayList<Pet>) bundle.getSerializable("arraylist");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createReminder = (Button) this.findViewById(R.id.create_reminder_button);
        dateButton = (Button) this.findViewById(R.id.date_button);
        timeButton = (Button) this.findViewById(R.id.time_button);
        title = (EditText) this.findViewById(R.id.title_editText);
        notes = (EditText) this.findViewById(R.id.notes_editText);
        reoccurring = (CheckBox) this.findViewById(R.id.reoccurring_checkbox);

        ArrayList<String> petArr = new ArrayList<String>();

        for(int i = 0; i < listPet.size(); i++)
        {
            petArr.add(listPet.get(i).getName());
        }

        ArrayAdapter<String> petAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, petArr);
        petSpinner.setAdapter(petAdapter);


        Calendar today = Calendar.getInstance();
        String date = today.get(Calendar.MONTH) + 1 + "-" + today.get(Calendar.DAY_OF_MONTH) + "-" + today.get(Calendar.YEAR);

        dateButton.setText(date);
        datePicker = new DatePicker(this);
        datePicker.updateDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        timePicker = new TimePicker(this);
        timePicker.setCurrentHour(3);
        timePicker.setCurrentMinute(0);

        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker = new DatePicker(AddReminderActivity.this);
                dateBuilder = new AlertDialog.Builder(AddReminderActivity.this);

                dateBuilder.setTitle("Choose a date");
                dateBuilder.setView(datePicker);

                dateBuilder.setPositiveButton("Set Date", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dateButton.setText((datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() +"-" + datePicker.getYear());
                    }
                });

                dateBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {

                    }
                });

                dateBuilder.show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePicker = new TimePicker(AddReminderActivity.this);
                timeBuilder = new AlertDialog.Builder(AddReminderActivity.this);
                timeBuilder.setTitle("Pick a time");
                timeBuilder.setView(timePicker);

                timeBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });

                timeBuilder.setPositiveButton("Set Time", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        timeButton.setText(makeTimeString(timePicker.getCurrentHour(),timePicker.getCurrentMinute()));
                    }
                });


                timeBuilder.show();
            }
        });

        createReminder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    JSONObject reminderJSON = new JSONObject();
                    reminderJSON.put("title", title.getText().toString());
                    reminderJSON.put("description", notes.getText().toString());

                    if(reoccurring.isChecked())
                    {
                        reminderJSON.put("reoccurring", "t");
                    }
                    else {
                        reminderJSON.put("reoccurring", "f");
                    }

                    Log.d("Date: ", (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() +"-" + datePicker.getYear());

                    //reminderJSON.put("time", );
                }
                catch(Exception e)
                {
                    Log.d("Error: ", e.getMessage());
                }



               // new HttpPostRequestTask().execute("https://kennel-server.herokuapp.com/");


                //startActivity(new Intent(AddReminderActivity.this, DashboardActivity.class));
            }
        });
    }

    public String makeTimeString(Integer hour, Integer minute) {
        String str;

        if (hour <= 12) {
            if(hour == 0)
                str = 12 + ":" + minute;
            else
                str = hour + ":" + minute;
        }
        else {
            str = (hour - 12) + ":" + minute;
        }

        if(hour < 12)
        {
            str += " AM";
        }
        else
        {
            str += " PM";
        }

        return str;
    }

    @Override
    public void onHttpPostDone(String result) {

    }
}
