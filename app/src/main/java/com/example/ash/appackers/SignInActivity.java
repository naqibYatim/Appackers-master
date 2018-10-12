package com.example.ash.appackers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private ProgressBar progressBar;
    private EditText editTextEmail, editTextPassword;

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        // set variables
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        TextView textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent textSignUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(textSignUpIntent);
            }
        });




        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userLogin();

            }

        });
    }

    private void userLogin(){

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("email required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("password required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("password should be at least 6 characters long");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                Log.d(TAG, "onComplete: onAuthStateChanged:signed_in:" + user.getUid());
//                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//
/*
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                db.setFirestoreSettings(settings);

                DocumentReference userRef = db.collection("users")
                        .document(user.getUid());



                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        // might needed changed
                        User user = (User) task.getResult().toObject(User.class);
                        ((UserClient)(getApplicationContext())).setUser(user);

                        // how to get user object
                        //User someUser = ((UserClient)(getApplicationContext())).getUser();

                    }
                });
*/
//

            }
        };

        // call the sign in method
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                // if user successfully login
                if(task.isSuccessful()){
                    // finish the activity to prevent udser press back to this activity
                    finish();

                    Intent signInIntent = new Intent(getApplicationContext(), MainAppackersActivity.class);
                    // add flag to clear all the opened activity
                    // we do this to prevent user from tapping on back button to be able to go back to this activity
                    signInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    SignInActivity.this.startActivity(signInIntent);

                }else{

                    // actual exception that has occurred
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}
