package com.example.a11984.mady_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    GlobalClass globalClass;
    private static final String TAG = "Main_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase= FirebaseDatabase.getInstance().getReference();
      FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                TextView name = findViewById(R.id.Name);
                TextView email = findViewById(R.id.Email);
                name.setText(user.username);
                email.setText(user.email);
           GlobalClass.name=user.username;
           GlobalClass.email=user.email;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read user!");
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tender_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (id == R.id.nav_create) {
            closeOptionsMenu();
            startActivity(new Intent(Main_Activity.this, Create_Activity.class));
        } else if (id == R.id.nav_search) {
            closeOptionsMenu();
            startActivity(
                    new Intent(Main_Activity.this, Search_Tender.class));
        } else if (id == R.id.nav_complete) {
            closeOptionsMenu();
            startActivity(new Intent(Main_Activity.this, Complete_Tenders.class));
        } else if (id == R.id.nav_incopmlete) {
            closeOptionsMenu();
            startActivity(new Intent(Main_Activity.this, Incomplete_Tenders.class));
        } else if (id == R.id.nav_calendar) {
            closeOptionsMenu();
            startActivity(new Intent(Main_Activity.this, Calendar.class));
        } else if (id == R.id.nav_message) {
            closeOptionsMenu();
            startActivity(new Intent(Main_Activity.this, Select_Recipient.class));
        }else if (id == R.id.nav_settings) {
            closeOptionsMenu();
            startActivity(new Intent(Main_Activity.this, SettingsActivity.class));
        }

        return true;
    }
}
