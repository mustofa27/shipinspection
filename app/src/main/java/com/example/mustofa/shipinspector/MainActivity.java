package com.example.mustofa.shipinspector;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView username,role;
    UserLocalStore userLocalStore;
    FragmentManager fragmentManager;
    View utama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        fragmentManager = getSupportFragmentManager();
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvusername);
        role = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvuserrole);
        userLocalStore = new UserLocalStore(getApplicationContext());
        username.setText(userLocalStore.getLoggedInUser().getUsername());
        role.setText(userLocalStore.getLoggedInUser().getRole());
        utama = findViewById(R.id.content_utama);
        if(getIntent().getSerializableExtra("state") != null)
        {
            String name = "nav_" + getIntent().getSerializableExtra("state").toString();
            int resId = getResources().getIdentifier(name,"id",this.getPackageName());
            utama.setVisibility(View.GONE);
            fragmentManager.beginTransaction().
                    replace(R.id.frameLayout, getFragment(resId)).
                    commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();
        fragment = getFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (fragment != null){
            utama.setVisibility(View.GONE);
            fragmentManager.beginTransaction().
                    replace(R.id.frameLayout, fragment).
                    commit();
        }
        return true;
    }
    private Fragment getFragment(int id)
    {
        if (id == R.id.nav_doc) {
            return Document.newInstance(null,null);// nanti diganti properti kapal yang diparsing
        }
        else if (id == R.id.nav_hull) {
            return Hull.newInstance(null,null);
        }
        else if (id == R.id.nav_machinery) {
            return Machinery.newInstance(null,null);
        }
        else if (id == R.id.nav_outfitting) {
            return Outfitting.newInstance(null,null);
        }
        else if (id == R.id.nav_navigation) {
            return Navigation.newInstance(null,null);
        }
        else if (id == R.id.nav_launching) {
            return Launching.newInstance(null,null);
        }
        else if (id == R.id.nav_inclining) {
            return Inclining.newInstance(null,null);
        }
        else if (id == R.id.nav_commisioning) {
            return Commisioning.newInstance(null,null);
        }
        else
            return null;
    }
}
