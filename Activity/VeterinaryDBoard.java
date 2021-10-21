package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import Models.Veterinaries;

public class VeterinaryDBoard extends AppCompatActivity {
    private ImageView vetImage;
    private ImageView vaccineimg;
    private ImageView appointmentimg;
    private TextView vaccinetxt;
    private TextView appointmenttxt;
    private TextView patienttxt;
    private ImageView patientimg;
    private Button signoutbtn;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    public Uri imageUri;


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menüyü bağlamak için
        MenuInflater menuInflater = getMenuInflater(); //bir xml dosyasını kodla bağlamak için kullandık.
        menuInflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //menüde seçilirse yapılacakları belirtmek.

        if(item.getItemId() == R.id.upload){ //tıklanırsa ne yapılacağı
            Intent intentToUpload = new Intent(VeterinaryDBoard.this,UploadActivity.class);
            startActivity(intentToUpload);
        }
        else if (item.getItemId()== R.id.signout){
           firebaseAuth.signOut();
           Intent intentToSignUp = new Intent(VeterinaryDBoard.this,SignUpActivity.class);
           startActivity(intentToSignUp);
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinary_d_board);
        vetImage = findViewById(R.id.vetImage);
        vaccineimg=findViewById(R.id.vaccineimg);
        appointmentimg=findViewById(R.id.appointmentimg);
        vaccinetxt=findViewById(R.id.vaccinetxt);
        appointmenttxt=findViewById(R.id.appointmenttxt);
        patientimg = findViewById(R.id.patientimg);
        patienttxt = findViewById(R.id.patienttxt);
        signoutbtn = findViewById(R.id.signoutbtn);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Veterinaries").child(user.getUid());

        vetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });



        vaccineimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VeterinaryDBoard.this,VaccineSchedule.class);
                startActivity(intent);
                finish();
            }
        });
        vaccinetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaryDBoard.this,VaccineSchedule.class);
                startActivity(intent);
                finish();
            }
        });
        patienttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaryDBoard.this,Patient.class);
                startActivity(intent);
                finish();
            }
        });
        patientimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaryDBoard.this,Patient.class);
                startActivity(intent);
                finish();
            }
        });
        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

                Intent intent = new Intent(VeterinaryDBoard.this,MainActivity.class);
                startActivity(intent);
            }
        });
        appointmentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaryDBoard.this,AppointmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
        appointmenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaryDBoard.this,AppointmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode==RESULT_OK && data!=null && data.getData() != null){
            imageUri = data.getData();
            vetImage.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading Image...");
        dialog.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference vetImageRef = storageReference.child("vet images/" + randomKey);

        vetImageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image uploaded.",Snackbar.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed To Upload",Toast.LENGTH_LONG).show();
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        dialog.setMessage("Percentage: "+(int) progressPercent + "%");
                    }
                });

        // Create a reference to 'vet images/'
      //  StorageReference mountainImagesRef = storageReference.child("images/mountains.jpg");

// While the file names are the same, the references point to different files
      //  mountainsRef.getName().equals(mountainImagesRef.getName());    // true
       // mountainsRef.getPath().equals(mountainImagesRef.getPath());
    }

    /* public void define(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Veterinaries").child(user.getUid());
    }
    public void Information(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Veterinaries vet = snapshot.getValue(Veterinaries.class);
                Log.i("Informations",vet.toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/
}