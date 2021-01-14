package info.example.tryonstore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.provider.FirebaseInitProvider;

import static android.content.ContentValues.TAG;

public class LoginPage extends Activity {
    private static final int RC_SIGN_IN = 101;
    TextView forgotpassword, movetosignup;
    EditText Email, Password;
    CoordinatorLayout Loginlayout;
    FirebaseInitProvider AuthUI;
    Button loginbtn;
    SignInButton googlebutton;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser User = mAuth.getCurrentUser();
        if (User != null) {
            Intent in = new Intent(LoginPage.this, MainActivity.class);
            startActivity(in);
            overridePendingTransition(R.anim.anim, R.anim.anim2);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginPage.this, MainActivity.class));
            finish();

        }
        Typeface tY = Typeface.createFromAsset(getAssets(), "times_new_roman.ttf");


        setContentView(R.layout.activity_login_page);
        forgotpassword = findViewById(R.id.forgotpassword);
        googlebutton = findViewById(R.id.googlebutton);
        Email = findViewById(R.id.editTextTextPersonName);
        Loginlayout = findViewById(R.id.LoginLayout);
        Password = findViewById(R.id.editTextPassword);
        movetosignup = findViewById(R.id.movetosignup);
        loginbtn = findViewById(R.id.login);

        Password.setTypeface(tY, Typeface.BOLD);


        movetosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, SignUpPage.class));
                finish();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                final String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Snackbar snackbar = Snackbar
                                                .make(Loginlayout, "Enter Minimum 6 Characters", Snackbar.LENGTH_LONG);

                                        snackbar.show();
                                    } else {
                                        Snackbar snackbar = Snackbar
                                                .make(Loginlayout, "Check your email and password Or Your internet is not working", Snackbar.LENGTH_LONG);

                                        snackbar.show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Snackbar snackbar = Snackbar
                                            .make(Loginlayout, "Successfully login", Snackbar.LENGTH_LONG);

                                    snackbar.show();
                                }
                            }
                        });
            }

        });

        createRequest();
        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });
        for (int i = 0; i < googlebutton.getChildCount(); i++) {
            View v = googlebutton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;


                tv.setText("Google Sign In");


                tv.setSingleLine(true);
                tv.setPadding(15, 15, 15, 15);

                return;
            }
        }
    }


    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser User = mAuth.getCurrentUser();
                            Intent in = new Intent(LoginPage.this, MainActivity.class);
                            startActivity(in);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }
}