package gps.map.navigator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;

import gps.map.navigator.view.ui.BottomNavigationDrawerFragment;

public class MainActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BottomNavigationDrawerFragment bottomNavDrawerFragment = new BottomNavigationDrawerFragment();
                bottomNavDrawerFragment.show(fragmentManager, bottomNavDrawerFragment.getTag());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
