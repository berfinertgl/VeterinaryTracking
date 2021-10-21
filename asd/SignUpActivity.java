package Activity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt1,getPasswordEt2,nameEt,usernameEt;
    private Button SignUpButton;
    private TextView SignInTv;
    private Button UserSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebaseAuth = FirebaseAuth.getInstance();
        nameEt = findViewById(R.id.name);
        usernameEt = findViewById(R.id.username);
        emailEt = findViewById(R.id.email);
        passwordEt1 =findViewById(R.id.password);
        getPasswordEt2 = findViewById(R.id.password2);
        SignUpButton = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        SignInTv = findViewById(R.id.signInTv);
        UserSignUp = findViewById(R.id.usersignup);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();

            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        UserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,UserSignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Register(){
        String name = nameEt.getText().toString();
        String username = usernameEt.getText().toString();
        String email=emailEt.getText().toString();
        String password1=passwordEt1.getText().toString();
        String password2=getPasswordEt2.getText().toString();

        if (TextUtils.isEmpty(name)){
            nameEt.setError("Enter your name");
        }
        else if(TextUtils.isEmpty(username)){
            usernameEt.setError("Enter your username");
        }
        else if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt1.setError("Enter your password");
        }
        else if(TextUtils.isEmpty(password2)){
            getPasswordEt2.setError("Confirm your password");
        }
        else if(!password1.equals(password2)){
            getPasswordEt2.setError("Different password");
            return;
        }
        else if(password1.length()<4){
            getPasswordEt2.setError("Length should be > 4");
            return;
        }
        else if(!isValidEmail(email)){
            emailEt.setError("Invalid Email");
            return;
        }
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String veterinary_id = firebaseAuth.getCurrentUser().getUid();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference().child("Veterinaries").child(veterinary_id);
                    HashMap<String,String> veterinaryMap = new HashMap<>();
                    veterinaryMap.put("full name",name);
                    veterinaryMap.put("user name", username);
                    veterinaryMap.put("email", email);
                 //   veterinaryMap.put("image","default");

                    reference.setValue(veterinaryMap);

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser(); //içeri giriş yapmış kullanıcı varsa gösterir.
                    Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this,"Sign up fail!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
