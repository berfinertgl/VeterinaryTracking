package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import Models.Appointment;
import Models.Users;

public class MakeAppointment extends AppCompatActivity {

    EditText userName;
    EditText appointmentText;
    Button sendAppointmentRequest;

    FirebaseDatabase database;
    DatabaseReference reference;

   /* private TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;

    Intent intent;

    ImageButton btn_send;
    EditText text_send;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        userName = findViewById(R.id.input_userName);
        appointmentText = findViewById(R.id.writeapp);
        sendAppointmentRequest = findViewById(R.id.sendappointment);

        database = FirebaseDatabase.getInstance();


        sendAppointmentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, appointmenttext;
                username = userName.getText().toString().trim();
                appointmenttext = appointmentText.getText().toString().trim();

                sendAppointment(username, appointmenttext);
            }
        });
    }
    public void sendAppointment(String userName, String appointmentText){
        reference = database.getReference("Appointments");
        String key = reference.push().getKey();
        DatabaseReference ref = database.getReference("Appointments/" + key);
        ref.setValue(new Appointment(userName, appointmentText));
    }
}

       /* Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
//         getSupportActionBar().setTitle("");
        //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //    toolbar.setNavigationOnClickListener(v -> finish());

        username = (TextView) findViewById(R.id.userName);
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        text_send = (EditText) findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MakeAppointment.this, "You can not send empty message", Toast.LENGTH_LONG).show();
                }
                text_send.setText("");
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        if (fuser != null) {
            reference.child(userid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    username.setText(user.getUsername());
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }
}

/*private FloatingActionButton send;
private EditText sendmessage;

        FirebaseUser user;

        Intent intent;

        //   DatabaseReference reference;

        String userId;
        String vetId;
        String key;
//String vet;
//String user;
       /* reference = FirebaseDatabase.getInstance().getReference();
        userId = "1";
        vetId = "2";

        sendMessage("selam");


    }

    public void sendMessage(String msgbody){

        String user = "messages/"+userId+"/"+vetId;
        String vet = "messages/"+vetId+"/"+userId;
        key = reference.child("messages").child("users").child("vet").push().getKey();

        Log.i("keyim",key);
        Map map = send(msgbody,userId);
        Map map2 = new HashMap();
        map2.put(user+"/"+key,map);
        map2.put(vet+"/"+key,map);

        reference.updateChildren(map2, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {

            }
        });
    }

    public Map send(String msgbody, String userId){
        Map msg = new HashMap();
        msg.put("message",msgbody);
        msg.put("from",userId);
        return msg;
    }*/