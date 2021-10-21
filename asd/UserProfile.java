package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import Models.Info;
import Models.Users;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.squareup.picasso.Picasso.*;

public class UserProfile extends AppCompatActivity {


    String uid;

    //Auth
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //firabase storage
    StorageReference storageReference;
    private StorageTask storageTask;

    //layout views
    CircleImageView avatar;
    TextView nameTxt, emailTxt, fullnameTxt;
    Button updateProfile;

    //Progress Dialog = yüklemek için
    ProgressDialog pd;


    //seçilen resmin uri adresi
    Uri image_uri;
    Uri sonuc_Uri;
    String my_uri = "";

    private static final int GaleriSecme = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pic"); //Firebase Storage Reference

        //init layout views
        avatar = findViewById(R.id.profileImage);
        nameTxt = findViewById(R.id.username);
        emailTxt = findViewById(R.id.email);
        fullnameTxt = findViewById(R.id.fullname);
        updateProfile = findViewById(R.id.update);
        pd = new ProgressDialog(this);

        nameTxt.setVisibility(View.INVISIBLE);

        nameTxt.setText(GlobalVar.currentUser.getUsername());
        emailTxt.setText(GlobalVar.currentUser.getEmail());
        fullnameTxt.setText(GlobalVar.currentUser.getFullname());

        //   getUserInfo();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tıklandığında galeriyi açmak

                CropImage.activity().setAspectRatio(1, 1)
                        .start(UserProfile.this);
              /*  CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON) //oran ayarlama
                        .setAspectRatio(1,1)
                        .start(UserProfile.this);*/


              /*  Intent galeriIntent = new Intent();
                galeriIntent.setAction(Intent.ACTION_GET_CONTENT);
                galeriIntent.setType("image/*");
                startActivityForResult(galeriIntent, GaleriSecme);*/
            }
        });
    }


    private String getFile(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Resim seçme kodlarının olduğu kısım
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //Resim seçiliyorsa
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            image_uri = result.getUri();
            avatar.setImageURI(image_uri);
        } else {
            //Resim seçilemiyorsa
            Toast.makeText(this, "Image is not choose..", Toast.LENGTH_LONG).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


  /*  private void getUserInfo(){
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists() && (snapshot.hasChild("user name") && (snapshot.hasChild("image") && (snapshot.hasChild("full name")
                && (snapshot.hasChild("email")))))){
                    String getUserName = snapshot.child("user name").getValue().toString();
                    String getEmail = snapshot.child("email").getValue().toString();
                    String getFullName = snapshot.child("full name").getValue().toString();
                    String getImage = snapshot.child("image").getValue().toString();

                    nameTxt.setText(getUserName);
                    fullnameTxt.setText(getFullName);
                    emailTxt.setText(getEmail);
                }else if(snapshot.exists() && (snapshot.hasChild("user name"))){
                    String getUserName = snapshot.child("user name").getValue().toString();

                    nameTxt.setText(getUserName);
                }else{
                    nameTxt.setVisibility(View.VISIBLE); //Kullanıcı görünürlüğü görebilsin adını girebilsin.
                    Toast.makeText(UserProfile.this,"Please set your informations",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }*/


    private void uploadImage() {

        pd.setTitle("Information transfer");
        pd.setMessage("Please wait..");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        final StorageReference imagePath = storageReference.child(uid + "." + getFile(image_uri));

        if (image_uri != null) {
            final StorageReference fileRef = storageReference.child(firebaseAuth.getCurrentUser().getUid() + ".jpg");

            storageTask = fileRef.putFile(image_uri);
            storageTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull @NotNull Task task) throws Exception {
                    if (task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef;
                }

            });
        }
    }
}

                  /*  storageTask = imagePath.putFile(image_uri);

                    storageTask.continueWithTask(new Continuation() {
                        @Override
                        public Object then(@NonNull @NotNull Task task) throws Exception {

                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return imagePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Uri> task) {
                            //Tamamlandığında
                            if (task.isSuccessful()) {

                                //veritabanına resmi aktar

                                Uri dowload_uri = task.getResult();
                                my_uri = dowload_uri.toString();

                                DatabaseReference dataPath = FirebaseDatabase.getInstance().getReference().child("Users");

                                String gonderiId = dataPath.push().getKey();
                                //  String getUserName = nameTxt.getText().toString();
                                //  String getEmail = emailTxt.getText().toString();
                                //  String getFullName = fullnameTxt.getText().toString();

                                HashMap<String, String> profileMap = new HashMap<>();
                                //  profileMap.put("user name", getUserName);
                                //  profileMap.put("email", getEmail);
                                //  profileMap.put("full name", getFullName);
                                profileMap.put("uid", gonderiId);
                                profileMap.put("image", my_uri);

                                dataPath.child(uid).setValue(profileMap);

                                pd.dismiss();

                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(UserProfile.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(UserProfile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        }
                    });
                }
            }*/

  /*  public void selectImage(View view) { //package manager kısmı : Eğer izin yoksa.
        if (ContextCompat.checkSelfPermission(UserProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //izin olup olmadığını kontrol ediyoruz.
            ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1); //izinleri istiyoruz, yeni dizi oluşturup hangi izni istediğimizi yazıyoruz.
        } //request code : bu iznin istendiği kod anlamına gelmektedir.
        else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //nu izin zaten verilmişse intent kullanılıyor. Nereden image alacağımızı gösteriyoruz.(action pick)
            startActivityForResult(intentToGallery, 2); //bir sonuç için bu activity başlat, 1 kullanıldığı için 2 veriliyor.
        }

    }


//GÖRSEL SEÇİLDİĞİNDE NE YAPACAĞIZ?

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //istenilen izinlerin sonucu

        if (requestCode == 1) { //izin verildiyse.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //bana bir dizi sonuç veriliyor, bunların içerisinde verilen bir sonuç varsa işlem yapacağım.
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //nu izin zaten verilmişse intent kullanılıyor. Nereden image alacağımızı gösteriyoruz.(action pick)
                startActivityForResult(intentToGallery, 2); //bir sonuç için bu activity başlat, 1 kullanıldığı için 2 veriliyor.
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}*/






