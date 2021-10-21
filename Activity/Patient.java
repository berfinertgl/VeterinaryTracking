package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import Adapter.PatientAdapter;
import Models.Info;
import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Patient extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ArrayList<Info> infos;
    private Button listPatient;
   // LinearLayout anaLayout;
    RelativeLayout relativeLayout;


    private ImageButton back;
     PatientAdapter patientAdapter;
     private GridView listelenen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);


        infos = new ArrayList<>();
        patientAdapter = new PatientAdapter(Patient.this,infos);
        listelenen = (GridView) findViewById(R.id.listelenen);
      //  anaLayout = findViewById(R.id.anaLayout);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        back = findViewById(R.id.back);
        listPatient = (Button) findViewById(R.id.listpatients);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient.this,VeterinaryDBoard.class);
                startActivity(intent);
                finish();
            }
        });



        listPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listelenen.setAdapter(patientAdapter);

                patientAdapter.notifyDataSetChanged();

            }
        });

        reference.child("Pet's information").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){

                    Info info = snp.getValue(Info.class);
                    infos.add(
                            new Info(
                                    info.getName(),
                                    info.getUsername(),
                                    info.getUseremail(),
                                    info.getType(),
                                    info.getAge()
                            )
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

