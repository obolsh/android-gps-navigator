package gps.map.navigator.view.ui.fragment.listener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.common.debug.Logger;

public class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    @Named(Constants.ApplicationContext)
    Context context;
    @Inject
    Logger logger;

    @Inject
    NavigationViewListener() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.rate_us:
                showMyAppPage();
                logger.debug("Rate us picked");
                break;
            case R.id.share:
                shareThisApp();
                logger.debug("Share picked");
                break;

            default:
        }
        return true;
    }

    private void showMyAppPage() {
        try {
            startActivity(getLaunchIntent());
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    private void startActivity(Intent intent) {
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    private Intent getLaunchIntent() {
        String appPackageName = context.getApplicationContext().getPackageName();
        try {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        } catch (Throwable e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
        }
    }

    private void shareThisApp() {
        try {
            startActivity(getShareIntent());
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    private Intent getShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_my_app));
        sendIntent.setType("text/plain");
        return Intent.createChooser(sendIntent, "");
    }
}
