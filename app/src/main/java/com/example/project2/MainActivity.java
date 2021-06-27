package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
   static Boolean coldStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        if(coldStart){
            Intent intent = new Intent(MainActivity.this, StartScreen.class);
            startActivity(intent);
            finish();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,new WalkFragment()).commit();

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment(); break;
                        case R.id.nav_running:
                            selectedFragment = new RunningFragment(); break;
                        case R.id.nav_walk:
                            selectedFragment = new WalkFragment(); break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment(); break;
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view,selectedFragment).commit();
                    return true;
                }
            };

}