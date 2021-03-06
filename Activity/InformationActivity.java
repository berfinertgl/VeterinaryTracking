package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Models.Info;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static android.provider.MediaStore.*;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    Bitmap selectedImage;
    private ImageView profileImage;
    private EditText petName;
    private EditText userEmail;
    private EditText userName;
    private EditText petType;
    private EditText petAge;
    private Button add;
    private ImageButton back;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    Uri imageData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        profileImage = findViewById(R.id.petimage);
        petName = findViewById(R.id.input_pet_name);
        userName = findViewById(R.id.input_userName);
        userEmail = findViewById(R.id.input_email);
        petType = findViewById(R.id.input_pet_type);
        petAge = findViewById(R.id.input_age_pet);
        add = findViewById(R.id.addpet);
        back = findViewById(R.id.back);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        // String user_id = auth.getCurrentUser().getUid();
        user = auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference(); //Firebase storage'da nereye ne kaydedilece??i tek tek se??ilmek zorundad??r.


        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addpet:
                String name, username, email, type;
                int age;
                name = petName.getText().toString().trim();
                username = userName.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                type = petType.getText().toString().trim();
                age = Integer.valueOf(petAge.getText().toString().trim());
                addPet(name, username, email, type, age);
                break;
            case R.id.back:
                Intent intent = new Intent(InformationActivity.this,userdashboard.class);
                startActivity(intent);
                finish();
        }
    }


    public void addPet(String userName, String petName, String userEmail, String petType, int petAge) {
        reference = firebaseDatabase.getReference("Pet's information").child(user.getUid());
        String key = reference.push().getKey(); //verilerin ??st ??ste yaz??lmamas?? i??in olu??turuldu.
        DatabaseReference referencenew = firebaseDatabase.getReference("Pet's information/" + key);
        referencenew.setValue(new Info(petName, userName, userEmail, petType, petAge));
    }


    public void selectImage(View view) { //package manager k??sm?? : E??er izin yoksa.
        if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //izin olup olmad??????n?? kontrol ediyoruz.
            ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1); //izinleri istiyoruz, yeni dizi olu??turup hangi izni istedi??imizi yaz??yoruz.
        } //request code : bu iznin istendi??i kod anlam??na gelmektedir.
        else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI); //nu izin zaten verilmi??se intent kullan??l??yor. Nereden image alaca????m??z?? g??steriyoruz.(action pick)
            startActivityForResult(intentToGallery, 2); //bir sonu?? i??in bu activity ba??lat, 1 kullan??ld?????? i??in 2 veriliyor.
        }

    }


//G??RSEL SE????LD??????NDE NE YAPACA??IZ?

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //istenilen izinlerin sonucu

        if (requestCode == 1) { //izin verildiyse.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //bana bir dizi sonu?? veriliyor, bunlar??n i??erisinde verilen bir sonu?? varsa i??lem yapaca????m.
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI); //nu izin zaten verilmi??se intent kullan??l??yor. Nereden image alaca????m??z?? g??steriyoruz.(action pick)
                startActivityForResult(intentToGallery, 2); //bir sonu?? i??in bu activity ba??lat, 1 kullan??ld?????? i??in 2 veriliyor.
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //ba??lat??lan activity'nin sonucunu g??steren kod blo??u.

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) { //g??rsel se??ildiyse ve ger??ekten geriye gelen bir g??rsel varsa
            //verilen datay?? uri'a ??evirmemiz gerekiyor. Elimizdeki data intent, biz bunu getData ile ger??ek g??rsele ??evirmeliyiz. Bu da bize uri veriyor(uri : g??rselin nerde kay??tl?? oldu??unun adresi.)
            imageData = data.getData();
            StorageReference ref = storageReference.child("petimages");
            ref.putFile(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(InformationActivity.this, "Image updated...", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(InformationActivity.this, "Image does not updated...", Toast.LENGTH_LONG).show();
                    }
                }
            });

            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    profileImage.setImageBitmap(selectedImage);
                } else {
                    selectedImage = Images.Media.getBitmap(this.getContentResolver(), imageData);
                    profileImage.setImageBitmap(selectedImage);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

