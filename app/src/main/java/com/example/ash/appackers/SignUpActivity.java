package com.example.ash.appackers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

// import com.example.ash.appackers.R;
// import com.google.firebase.firestore.DocumentReference

public class SignUpActivity extends AppCompatActivity {

    private RadioGroup radioGroupGender, radioGroupUserType;
    private RadioButton radioButtonMale, radioButtonBackpacker;
    private Button buttonSignUp;
    // TextView testmsg;
    private ProgressBar progressBar;
    private EditText editTextEmail, editTextUsername, editTextPassword, editTextFullName, editTextNationality, editTextAge, editTextAbout;

    // create a firebase auth object
    private FirebaseAuth mAuth;

    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFullName = findViewById(R.id.editTextContactName);
        editTextNationality = findViewById(R.id.editTextNationality);
        editTextAge = findViewById(R.id.editTextAge);
        editTextAbout = findViewById(R.id.editTextAbout);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioGroupUserType = findViewById(R.id.radioGroupUserType);
        radioButtonBackpacker = findViewById(R.id.radioButtonBackpacker);

        // initialize the firebase object
        mAuth = FirebaseAuth.getInstance();

        mDb = FirebaseFirestore.getInstance();

        buttonSignUp = findViewById(R.id.buttonSignUp);

        // text view just for test
        // testmsg = (TextView) findViewById(R.id.textViewTest);



        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.buttonSignUp:
                        registerUser();
                        break;
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // check if user is already sign in
        if(mAuth.getCurrentUser() != null){
            // handle the already registered user (firebase auth series) Do it later
        }

    }

    private void registerUser(){

        // get all the information that we write
        final String email = editTextEmail.getText().toString().trim();    // get the text from email field and store into this var
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String full_name = editTextFullName.getText().toString().trim();
        final String nationality = editTextNationality.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String about = editTextAbout.getText().toString().trim();
        final String gender;
        final String userType;

        // get selected radio button
        int selectedId = radioGroupGender.getCheckedRadioButtonId();

        if(selectedId == radioButtonMale.getId()){
            // testmsg.append("male");
            gender = "Male";
        }else{
            // testmsg.append("female");
            gender = "Female";
        }

        int selectedUser = radioGroupUserType.getCheckedRadioButtonId();

        if(selectedUser == radioButtonBackpacker.getId()){
            userType = "Backpacker";
        }else{
            userType = "Buddy";
        }

        // start doing the validation
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

        if(username.isEmpty()){
            editTextUsername.setError("username required");
            editTextUsername.requestFocus();
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

        if(full_name.isEmpty()){
            editTextFullName.setError("full name required");
            editTextFullName.requestFocus();
            return;
        }

        if(nationality.isEmpty()){
            editTextNationality.setError("nationality required");
            editTextNationality.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("age required");
            editTextAge.requestFocus();
            return;
        }

        if(about.isEmpty()){
            editTextAbout.setError("about required");
            editTextAbout.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        // here we will register the user
        // notice that we do not need to store password into the firebase realtime database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // check if the registration is successful or not
                        if(task.isSuccessful()){

                            // we will store the additional fields into firebase database
                            // create the user class object
                            User user = new User(email, username, full_name, nationality, age, about, gender, userType);
                            user.setUser_id(FirebaseAuth.getInstance().getUid());
                            user.setEmail(email);
                            user.setAvatar("R.drawable.cwm_logo");
                            user.setUsername(username);

                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                    .setTimestampsInSnapshotsEnabled(true)
                                    .build();
                            mDb.setFirestoreSettings(settings);

                            DocumentReference newUserRef = mDb
                                    .collection("users")
                                    .document(FirebaseAuth.getInstance().getUid());

                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });


                            // set this user object to firebase database inside the Users node
                            // go to the unique user id that has been created while creating a user in firebase auth
                            // inside the node, set the value as user object
                            FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        finish();
                                        Intent signUpIntent = new Intent(getApplicationContext(), MainAppackersActivity.class);
                                        SignUpActivity.this.startActivity(signUpIntent);
                                    }else{

                                        // check if email is already registered
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });



                        }else{
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

}
