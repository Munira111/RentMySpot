package com.example.rentmyspot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
//
public class HomepageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    homepageFragment homepageFragment;
    profileFragment profileFragment = new profileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        String currentUser = getIntent().getStringExtra("username");

        // Pass the currentUser to the homepageFragment
        Bundle bundle = new Bundle();
        bundle.putString("username", currentUser);
        homepageFragment = new homepageFragment();
        homepageFragment.setArguments(bundle);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.containar, homepageFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containar, homepageFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containar, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}