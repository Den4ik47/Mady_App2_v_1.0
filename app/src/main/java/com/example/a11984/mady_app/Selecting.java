package com.example.a11984.mady_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Selecting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "Selecting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView name= findViewById(R.id.User_name);
        TextView details= findViewById(R.id.Details);
        name.setText(GlobalClass.data);

        mDatabase.child("Tenders").child(GlobalClass.data1).child(GlobalClass.data).addListenerForSingleValueEvent(new ValueEventListener() {
           public void onDataChange(DataSnapshot dataSnapshot) {
                Tender tender = dataSnapshot.getValue(Tender.class);
                TextView details= findViewById(R.id.Details);
                TextView title= findViewById(R.id.Title);
               title.setText(tender.type);
                details.setText(tender.details);
               GlobalClass.data2=tender.Name;
           }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read user!");
            }
        });
        TextView msg= findViewById(R.id.Msg_click);
        msg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Selecting.this, Message.class));
            }
        });

        details.setMovementMethod(new ScrollingMovementMethod());
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
        getMenuInflater().inflate(R.menu.selecting, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
            startActivity(new Intent(Selecting.this, Create_Activity.class));
        } else if (id == R.id.nav_search) {
            closeOptionsMenu();
            startActivity(
                    new Intent(Selecting.this, Search_Tender.class));
        } else if (id == R.id.nav_complete) {
            closeOptionsMenu();
            startActivity(new Intent(Selecting.this, Complete_Tenders.class));
        } else if (id == R.id.nav_incopmlete) {
            closeOptionsMenu();
            startActivity(new Intent(Selecting.this, Incomplete_Tenders.class));
        } else if (id == R.id.nav_calendar) {
            closeOptionsMenu();
            startActivity(new Intent(Selecting.this, Calendar.class));
        } else if (id == R.id.nav_message) {
            closeOptionsMenu();
            startActivity(new Intent(Selecting.this, Select_Recipient.class));
        }else if (id == R.id.nav_settings) {
            closeOptionsMenu();
            startActivity(new Intent(Selecting.this, SettingsActivity.class));
        }

        return true;
    }
}
