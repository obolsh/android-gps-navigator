package androidx.appcompat.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;


public class AppCompatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void onDestroy() {

    }

    @Override
    public View findViewById(int id) {
        return null;
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    public void setSupportActionBar(Toolbar toolbar) {

    }

    @Override
    public MenuInflater getMenuInflater() {
        return null;
    }
}
