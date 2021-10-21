package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class userdashboard extends AppCompatActivity {

    private ImageView informationimg;
    private TextView informationtxt;
    private ImageView appointmentimg;
    private TextView appointmenttxt;

    private FirebaseAuth firebaseAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menüyü ilgili activitye bağlamak için kullanılan metot.

        MenuInflater menuInflater = getMenuInflater(); //xml dosyasını kodla bağlamak için yapılan işlem.
        menuInflater.inflate(R.menu.user_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //herhangi bir şey seçildiğinde yapılacak işlemleri belirtmek için oluşturulan metot.

        if (item.getItemId() == R.id.signout) {
            firebaseAuth.signOut();

            Intent intent = new Intent(userdashboard.this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.profile) {
            Intent intentToProfile = new Intent(userdashboard.this, UserProfile.class);
            startActivity(intentToProfile);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);

        firebaseAuth = FirebaseAuth.getInstance();


        informationimg = findViewById(R.id.informationimg);
        informationtxt = findViewById(R.id.informationtxt);
        appointmentimg = findViewById(R.id.appointmentimg);
        appointmenttxt = findViewById(R.id.informationtxt);
        //  signoutbtn = findViewById(R.id.signoutbtn);


        Toolbar toolbar = findViewById(R.id.usertoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DASHBOARD");
        toolbar.setSubtitle("Veterinary Tracking Application");
        toolbar.setLogo(android.R.drawable.ic_menu_info_details);


        informationimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdashboard.this, InformationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        informationtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdashboard.this, InformationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        appointmentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdashboard.this, MakeAppointment.class);
                startActivity(intent);
                finish();
            }
        });

        appointmenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdashboard.this, MakeAppointment.class);
                startActivity(intent);
                finish();
            }
        });


    /*    signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Intent intent = new Intent(userdashboard.this,MainActivity.class);
                startActivity(intent);
            }
        });*/
    }
}