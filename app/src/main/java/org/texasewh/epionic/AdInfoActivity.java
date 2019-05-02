package org.texasewh.epionic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.texasewh.epionic.AdInfoActivity;
import org.texasewh.epionic.FragmentNotes;
import org.texasewh.epionic.MainActivity;
import org.texasewh.epionic.SettingsActivity;

import java.math.BigInteger;

public class AdInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_info_activity);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);



        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //FloatingActionButton floatingActionButton2=(FloatingActionButton)getWindow().getDecorView().findViewById(R.id.floatingActionButton9);
        //floatingActionButton2.setOnClickListener(new View.OnClickListener(){
            //public void onClick(View view){
         //       startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        //});


    @Override
    /*public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            startActivity(new Intent(AdInfoActivity.this, MainActivity.class));

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(AdInfoActivity.this, SettingsActivity.class));

        } else if (id == R.id.nav_notes) {
            startActivity(new Intent(AdInfoActivity.this, FragmentNotes.class));

        } else if (id == R.id.nav_patientInfo) {
            startActivity(new Intent(AdInfoActivity.this, PatientInfoActivity.class));

        } else if (id == R.id.nav_adinfo) {
            startActivity(new Intent(AdInfoActivity.this, AdInfoActivity.class));

        }

      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       // drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
