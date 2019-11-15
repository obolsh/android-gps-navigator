package gps.map.navigator.view.ui.fragment.listener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import gps.map.navigator.R;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.policy.DialogFactory;
public class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    Activity activity;
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
            case R.id.privacy_policy:
                new DialogFactory(activity).buildPolicy().show();
                break;
            case R.id.tos:
                new DialogFactory(activity).buildTerms().show();
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
            activity.getApplicationContext().startActivity(intent);
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
        String appPackageName = activity.getApplicationContext().getPackageName();
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
        intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.share_my_app));
        intent.setType("text/plain");
        return Intent.createChooser(intent, "");
    }
}
