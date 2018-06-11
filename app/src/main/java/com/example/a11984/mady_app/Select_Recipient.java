package com.example.a11984.mady_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Select_Recipient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__recipient);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            displayView(0);
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
            //getMenuInflater().inflate(R.menu.search__tender, menu);
            return true;
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (id == R.id.nav_create) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, Create_Activity.class));
            } else if (id == R.id.nav_search) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, Search_Tender.class));
            } else if (id == R.id.nav_complete) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, Complete_Tenders.class));
            } else if (id == R.id.nav_incopmlete) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, Incomplete_Tenders.class));
            } else if (id == R.id.nav_calendar) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, Calendar.class));
            } else if (id == R.id.nav_message) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, Select_Recipient.class));
            }else if (id == R.id.nav_settings) {
                closeOptionsMenu();
                startActivity(new Intent(Select_Recipient.this, SettingsActivity.class));
            }
            displayView(0); // call search fragment.

            drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    private void displayView(int position) {
        fragment = null;
        String fragmentTags = "";
        switch (position) {
            case 0:
                fragment = new Message_Adapter();
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragmentTags).commit();
        }
    }

}
