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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.berfinertoglu.veterinarytrackingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VeterinaySignin extends AppCompatActivity {
    private EditText emailEt, passwordEt;
    private Button signInButton;
    private TextView signUp;
    private Button userSignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veterinary_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        signInButton = findViewById(R.id.veterinarylogin);
        userSignIn = findViewById(R.id.backtosignin);
        progressDialog = new ProgressDialog(this);
        signUp = findViewById(R.id.signUpTv);
        forgotPassword = findViewById(R.id.forgotpassword);

        userSignIn.setOnClickListener(new View.OnClickListener() {   //bu butona tıklanırsa, üye girişi sayfasına geri dönecek.
            @Override
            public void onClick(View view) {
                Intent intent = (new Intent(VeterinaySignin.this, MainActivity.class));
                startActivity(intent);
                finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() { //bu butona tıklandığında veteriner dashboarda giriş yapacak.
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VeterinaySignin.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaySignin.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Login() {
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter your email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Enter your password");
        }
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(VeterinaySignin.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(VeterinaySignin.this, VeterinaryDBoard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VeterinaySignin.this, "Sign in fail!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}



