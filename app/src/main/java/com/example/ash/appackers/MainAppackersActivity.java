package com.example.ash.appackers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ash.appackers.ui.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class MainAppackersActivity extends AppCompatActivity {

    private static final String TAG = "MainAppackersActivity";

    // global static constant
    // error that gonna handle if user don't have the correct version
    private static final int ERROR_DIALOG_REQUEST = 9001;

    FirebaseAuth mAuth;

    private RelativeLayout layout;
    private DrawerLayout drawerLayout;
    private NavigationView navigation;

    // create option and back symbol in navigation drawer view
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    //private View headerView;
    //private TextView navUsername;
    //private TextView email;

    private String username, emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appackers);

        mAuth = FirebaseAuth.getInstance();

        // retrieve the data that we just parse to the mainAppackersActivity
        Intent intent = getIntent();
        String full_name = intent.getStringExtra("full_name");
        username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNewAct();

        // create option and back symbol in navigation drawer view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // intent for forum
        CardView cardViewForum = (CardView) findViewById(R.id.cardViewForum);
        cardViewForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent forumIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(forumIntent);
            }
        });

        // intent for money changer
        CardView cardViewMoneyChanger = (CardView) findViewById(R.id.cardViewMoneyChanger);
        cardViewMoneyChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moneyChangerIntent = new Intent(getApplicationContext(), CheckCurrencyActivity.class);
                startActivity(moneyChangerIntent);
            }
        });

        // start the locate me function if google play services is okay
        if(isServicesOK()){
            init();
        }

        // intent for buddy
        CardView cardViewBuddy = (CardView) findViewById(R.id.cardViewBuddy);
        cardViewBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buddyIntent = new Intent(getApplicationContext(), BuddyActivity.class);
                startActivity(buddyIntent);
            }
        });

    }

    // method to check if user has the correct version of api
    public boolean isServicesOK(){

        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainAppackersActivity.this);

        if(available == ConnectionResult.SUCCESS){
            // everything is fine and the user can make the map request
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            // an error occurred but we can fix it
            Log.d(TAG, "isServicesOK: an error occurred and we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainAppackersActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return  false;

    }

    // init for what we have for forum card view
    private void init(){

        CardView cardViewLocate = (CardView) findViewById(R.id.cardViewLocate);
        cardViewLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locateIntent = new Intent(getApplicationContext(), LocateActivity.class);
                startActivity(locateIntent);
            }
        });

    }


    // check if user is not sign in
    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null){
            finish();
            // start the sign in activity
            startActivity(new Intent(this, SignInActivity.class));
        }

    }

    // method to display and hide navigation view when clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return true;
    }

    // method to start a new activity when item in navigation bar is clicked
    private void initNewAct(){

        // set text user name and email
        navigation = (NavigationView) findViewById(R.id.nav_view);
        //headerView = navigation.getHeaderView(0);
        //navUsername = (TextView) headerView.findViewById(R.id.tvUserName);
        //navUsername.setText(username);

        //email = (TextView) headerView.findViewById(R.id.tvEmail);
        //email.setText(emails);

        // navigation.set
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){

                    case R.id.settings:
                        Intent settingIntent = new Intent(MainAppackersActivity.this, SettingsActivity.class);
                        startActivity(settingIntent);
                        break;
                    case R.id.help:
                        Intent helpIntent = new Intent(MainAppackersActivity.this, HelpActivity.class);
                        startActivity(helpIntent);
                        break;
                    case R.id.history:
                        Intent historyIntent = new Intent(MainAppackersActivity.this, HistoryActivity.class);
                        startActivity(historyIntent);
                        break;
                    case R.id.download:
                        Intent downloadIntent = new Intent(MainAppackersActivity.this, DownloadActivity.class);
                        startActivity(downloadIntent);
                        break;
                    case R.id.emergency:
                        Intent emergencyIntent = new Intent(MainAppackersActivity.this, EmergencyActivity.class);
                        startActivity(emergencyIntent);
                        break;
                    case R.id.forum:
                        Intent forumIntent = new Intent(MainAppackersActivity.this, ForumActivity.class);
                        startActivity(forumIntent);
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(MainAppackersActivity.this, SignInActivity.class));
                        break;

                }
                return false;
            }
        });

    }


}
