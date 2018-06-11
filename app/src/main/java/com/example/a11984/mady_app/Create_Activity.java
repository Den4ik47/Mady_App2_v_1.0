package com.example.a11984.mady_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String[] data = {"Choice type of your tender","Finanse", "Food", "Service", "IT", "Celebration","Economics"};
    private String type;
    private static final String TAG = "Registration_Activity";
    private EditText name;
    private EditText details;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private  FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
         name= findViewById(R.id.Name);
         details= findViewById(R.id.Details);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.Choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                type=data[position];
    }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
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
        getMenuInflater().inflate(R.menu.create_, menu);
        return true;
    }
public void Setup_cursor(View view){
    details.setSelection(0);
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
    public void Create(View view){
      String  mName=name.getText().toString();
       String mDetails=details.getText().toString();
       if( !validateForm()){return;}

writeNewUser(user.getUid(),mName,mDetails);
        Toast.makeText(getApplicationContext(), "Create complete" , Toast.LENGTH_SHORT).show();
        finish();
    }
    private void writeNewUser(String User_name,String mName,String mDetails) {

        mDatabase= FirebaseDatabase.getInstance().getReference();
        Tender tender = new Tender(mDetails,type,User_name);
        FirebaseDatabase.getInstance().getReference().child("Tenders").child(type).child(mName).setValue(tender);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (id == R.id.nav_create) {
            closeOptionsMenu();
            startActivity(new Intent(Create_Activity.this, Create_Activity.class));
        } else if (id == R.id.nav_search) {
            closeOptionsMenu();
            startActivity(
                    new Intent(Create_Activity.this, Search_Tender.class));
        } else if (id == R.id.nav_complete) {
            closeOptionsMenu();
            startActivity(new Intent(Create_Activity.this, Complete_Tenders.class));
        } else if (id == R.id.nav_incopmlete) {
            closeOptionsMenu();
            startActivity(new Intent(Create_Activity.this, Incomplete_Tenders.class));
        } else if (id == R.id.nav_calendar) {
            closeOptionsMenu();
            startActivity(new Intent(Create_Activity.this, Calendar.class));
        } else if (id == R.id.nav_message) {
            closeOptionsMenu();
            startActivity(new Intent(Create_Activity.this, Select_Recipient.class));
        }else if (id == R.id.nav_settings) {
            closeOptionsMenu();
            startActivity(new Intent(Create_Activity.this, SettingsActivity.class));
        }

        return true;
    }

    private boolean validateForm() {
        boolean valid = true;
        String  mName=name.getText().toString();
        String mDetails=details.getText().toString();
        if (TextUtils.isEmpty(mName)||mName.length()<2) {
            Toast.makeText(getApplicationContext(), "Name of Tender can't be clear or have less than 2 characters" , Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if (TextUtils.isEmpty(mDetails)||mDetails.length()<10) {
            Toast.makeText(getApplicationContext(), "Details of Tender can't be clear or have less than 10 characters" , Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(type=="Choice type of your tender"){Toast.makeText(getApplicationContext(),"Please, make your choice", Toast.LENGTH_SHORT).show();
            valid=false;}
        return valid;
    }
}
