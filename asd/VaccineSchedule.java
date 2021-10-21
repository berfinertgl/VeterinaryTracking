package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class VaccineSchedule extends AppCompatActivity {
    private Button selectDatebtn;
    private EditText vaccinetype;
    private EditText etTo;
    private EditText etSubject;
    private EditText etMessage;
    private Button send;
    private ImageButton back;
   // private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog dpd;
    private TextView datetxt;
    private Button selecttimebtn;
    private TextView timetxt;
    private int timerhour;
    private int timerminute;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_schedule);

        selectDatebtn = findViewById(R.id.selectDate);
        vaccinetype = findViewById(R.id.input_vaccinetype);
        datetxt =findViewById(R.id.dateTxt);
        selecttimebtn = findViewById(R.id.selectTime);
        timetxt = findViewById(R.id.timetxt);

        etTo = findViewById(R.id.et_to);
        etSubject = findViewById(R.id.subject);
        send = findViewById(R.id.sendemail);
        back = findViewById(R.id.back);
        etMessage = findViewById(R.id.message);

        selectDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now =Calendar.getInstance();
                year = now.get(Calendar.YEAR); //mevcut yılı alıyor.
                month = now.get(Calendar.MONTH); //mevcut ayı alır.
                day = now.get(Calendar.DAY_OF_MONTH); //bugünü alır.

                dpd = new DatePickerDialog(VaccineSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //tarihi set ediyoruz.
                        datetxt.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },year,month,day); //başlangıçta set edilecek değerler.
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dpd.setTitle("Select a date");
                dpd.show();

            }

        });

        selecttimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize time picker dialog
                TimePickerDialog tpd = new TimePickerDialog(VaccineSchedule.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Initialize hour and minute
                        timerhour = hourOfDay;
                        timerminute = minute;
                        //Store hour and minute in string
                        String time = timerhour + ":" + timerminute;
                        //initialize 24 hours time format
                        SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24hours.parse(time);
                            //Initialize 12 hours time format
                            SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
                            //Set selected time on text view
                            timetxt.setText(f12hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },12,0,false);
                //Set transparent backgorund
                tpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Displayed previous selected time
                tpd.updateTime(timerhour,timerminute);
                tpd.show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + etTo.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT,etSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,etMessage.getText().toString());
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaccineSchedule.this,VeterinaryDBoard.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
