package info.example.tryonstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends Activity {
    private EditText Username, Email, Password,Phone_no;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    TextView movetologin;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        movetologin = (TextView) findViewById(R.id.movetologin);
        Phone_no= findViewById(R.id.editTextTextPhone);
        btnSignUp = (Button) findViewById(R.id.signup);
        Email = (EditText) findViewById(R.id.editTextemailSignUp);
        Password = (EditText) findViewById(R.id.editTextPasswordSignUp);
        Username = findViewById(R.id.editTexttextUsername);
        movetologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(i);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String username= Username.getText().toString();
                final String phone_no= Phone_no.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone_no)) {
                    Toast.makeText(getApplicationContext(), "Enter Phone No!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone_no.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Enter the correct phone", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpPage.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpPage.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    UserId=mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fstore.collection("Users").document(UserId);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("Email",email);
                                    user.put("Username",username);
                                    user.put("Phone_No",phone_no);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignUpPage.this, "On Success: User Profile is " + UserId,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    startActivity(new Intent(SignUpPage.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });


    }
}