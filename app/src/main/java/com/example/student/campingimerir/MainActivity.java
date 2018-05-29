package com.example.student.campingimerir;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recherche:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new MapSearchFragment())
                            .commit();
                    return true;
                case R.id.navigation_activite:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new ActiviteFragment())
                            .commit();
                    return true;
                case R.id.navigation_reservation:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new ReservFragment())
                            .commit();
                    return true;
                case R.id.navigation_compte:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new CompteFragment())
                            .commit();
                    return true;
                case R.id.localisation:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new LocalisationFragment())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new MapSearchFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.changeSearchMap:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new MapSearchFragment())
                        .commit();
                return true;
            case R.id.changeSearchList:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new ListSearchFragment())
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
