package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Adapter.AppointmentAdapter;
import Models.Appointment;
import Models.Info;

public class AppointmentActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ArrayList<Appointment> appointments;
    private Button showAppointment;
    private ImageButton back;
    private GridView gosterilen;

    AppointmentAdapter appointmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        appointments = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(AppointmentActivity.this,appointments);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        gosterilen = (GridView)findViewById(R.id.gosterilen);

        showAppointment = (Button) findViewById(R.id.showAppointmentRequest);
        back = (ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, VeterinaryDBoard.class);
                startActivity(intent);
                finish();
            }
        });

        showAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gosterilen.setAdapter(appointmentAdapter);

                appointmentAdapter.notifyDataSetChanged();
            }
        });

        reference.child("Appointments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){
                    Appointment appointment = snp.getValue(Appointment.class);
                    appointments.add(new Appointment(appointment.getUsername(),appointment.getAppointmenttext()));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}
