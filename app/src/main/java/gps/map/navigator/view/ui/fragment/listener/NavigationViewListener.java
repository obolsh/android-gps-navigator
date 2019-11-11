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
                break;
            case R.id.share:
                shareThisApp();
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

    private void startActivity(@NonNull Intent intent) {
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    @NonNull
    private Intent getLaunchIntent() {
        Intent intent = createIntentWithAction(Intent.ACTION_VIEW);
        intent.setData(getRateUsUri());
        return intent;
    }

    @NonNull
    private Intent createIntentWithAction(@NonNull String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        return intent;
    }

    @NonNull
    private Uri getRateUsUri() {
        String appPackageName = context.getApplicationContext().getPackageName();
        try {
            return Uri.parse("market://details?id=" + appPackageName);
        } catch (Throwable e) {
            return Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName);
        }
    }

    private void shareThisApp() {
        try {
            startActivity(getShareIntent());
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    @NonNull
    private Intent getShareIntent() {
        Intent intent = createIntentWithAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_my_app));
        intent.setType("text/plain");
        return Intent.createChooser(intent, "");
    }
}
