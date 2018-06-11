package com.example.a11984.mady_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Message extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseListAdapter<ChatMessage> adapter;
    private DatabaseReference mDatabase;
    private static final String TAG = "Message";
    private String Id;
    String global;
    String global2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        global2=GlobalClass.data2;
        Id= FirebaseAuth.getInstance().getCurrentUser().getUid();
            displayChatMessages();
        FloatingActionButton fab =
                findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance().getReference().child("Message").child(Id).child(global2).push().setValue(new ChatMessage(input.getText().toString(),Id));
                FirebaseDatabase.getInstance().getReference().child("Message").child(global2).child(Id).push().setValue(new ChatMessage(input.getText().toString(),Id));
                        // Clear the input
                input.setText("");
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
        getMenuInflater().inflate(R.menu.message, menu);
        return true;
    }
    public void displayChatMessages(){
        ListView listOfMessages = findViewById(R.id.list_of_messages);
        Id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child("Message").child(Id).child(global2)) {
            @Override
            protected void populateView(View v,ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);
                // Set their text
                messageText.setText(model.getMessageText());
              if(model.getMessageUser()==Id) {
                  messageUser.setText("You");
              }
              else{
                  messageUser.setText("Friend");
              }
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (id == R.id.nav_create) {startActivity(new Intent(Message.this, Create_Activity.class));
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(Message.this, Search_Tender.class));
        } else if (id == R.id.nav_complete) {
            startActivity(new Intent(Message.this, Complete_Tenders.class));
        } else if (id == R.id.nav_incopmlete) {
            startActivity(new Intent(Message.this, Incomplete_Tenders.class));
        } else if (id == R.id.nav_calendar) {
            startActivity(new Intent(Message.this, Calendar.class));
        } else if (id == R.id.nav_message) {
            startActivity(new Intent(Message.this, Select_Recipient.class));
        }else if (id == R.id.nav_settings) {
            startActivity(new Intent(Message.this, SettingsActivity.class));
        }

        return true;
    }
}
