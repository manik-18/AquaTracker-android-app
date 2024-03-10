package com.majorproject.aquatracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.majorproject.aquatracker.Fragments.HardnessFragment;
import com.majorproject.aquatracker.Fragments.HomeFragment;
import com.majorproject.aquatracker.Fragments.OxygenFragment;
import com.majorproject.aquatracker.Fragments.TemperatureFragment;
import com.majorproject.aquatracker.Fragments.phFragment;
import com.majorproject.aquatracker.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find and set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find and set the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        View headerView = navigationView.getHeaderView(0);
        TextView textViewEmail = headerView.findViewById(R.id.profile_email);
        textViewEmail.setText(user.getEmail());

        navigationView.setNavigationItemSelectedListener(this);
        // Create and set the ActionBarDrawerToggle for handling navigation drawer open/close
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Check if savedInstanceState is null and if so, replace the fragment and set the selected item in the navigation view
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_ph) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new phFragment()).commit();
        } else if (itemId == R.id.nav_temperature) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TemperatureFragment()).commit();
        }
        else if (itemId == R.id.nav_hardness) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HardnessFragment()).commit();
        }
        else if (itemId == R.id.nav_oxygen) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OxygenFragment()).commit();
        }else if (itemId == R.id.nav_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                    })
                    .show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Handle the back button press - close the drawer if it's open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
