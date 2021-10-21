package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import Models.Users;

public class MainActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt;
    private Button SignInButton;
    private TextView SignUpTv;
    private Button SignInVet;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        emailEt = findViewById(R.id.email);
        passwordEt =findViewById(R.id.password);
        SignInButton = findViewById(R.id.login);
        SignInVet = findViewById(R.id.veterinarylogin);
        progressDialog = new ProgressDialog(this);
        SignUpTv = findViewById(R.id.signUpTv);
        forgotPassword = findViewById(R.id.forgotpassword);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();

            }
        });
        SignInVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,VeterinaySignin.class);
                startActivity(intent);
                finish();
            }
        });

        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String email=emailEt.getText().toString();
        String password1=passwordEt.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt.setError("Enter your password");
        }

        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    GlobalVar.currentUser = snapshot.getValue(Users.class);
                                     Intent intent=new Intent(MainActivity.this,UserProfile.class);
                                     startActivity(intent);
                                     Toast.makeText(MainActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                                     finish();
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                }
                else{
                    Toast.makeText(MainActivity.this,"Sign in fail!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
