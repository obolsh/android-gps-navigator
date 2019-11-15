package gps.map.navigator.view.ui.fragment.listener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.R;
import gps.map.navigator.policy.DialogFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NavigationViewListener.class, Intent.class, Uri.class, DialogFactory.class})
public class NavigationViewListenerTest {

    private Activity activity;
    private MenuItem menuItem;
    private Intent intent;
    private Uri uri;
    private DialogFactory dialogFactory;

    @Before
    public void setUp() throws Exception {
        mockStatic(Intent.class);
        mockStatic(Uri.class);

        activity = mock(Activity.class);
        menuItem = mock(MenuItem.class);
        intent = mock(Intent.class);
        uri = mock(Uri.class);
        dialogFactory = mock(DialogFactory.class);

        when(activity.getApplicationContext()).thenReturn(activity);
        when(activity.getString(anyInt())).thenReturn("foo");
        when(Uri.parse(anyString())).thenReturn(uri);
        when(Intent.createChooser(eq(intent), anyString())).thenReturn(intent);

        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
        whenNew(DialogFactory.class).withAnyArguments().thenReturn(dialogFactory);

        when(dialogFactory.buildPolicy()).thenReturn(dialogFactory);
        when(dialogFactory.buildTerms()).thenReturn(dialogFactory);
    }

    @After
    public void tearDown() throws Exception {
        when(menuItem.getItemId()).thenReturn(-1);
    }

    private NavigationViewListener createListener() {
        NavigationViewListener listener = new NavigationViewListener();
        setInternalState(listener, "activity", activity);
        return listener;
    }

    @Test
    public void make_menu_picked_unknown_key_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(-1);

        listener.onNavigationItemSelected(menuItem);

        verify(activity, times(0)).startActivity(any(Intent.class));
    }

    @Test
    public void make_navigate_to_rate_us_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(R.id.rate_us);

        listener.onNavigationItemSelected(menuItem);

        verify(activity).startActivity(eq(intent));
        verify(intent).setAction(eq(Intent.ACTION_VIEW));
        verify(intent).setData(eq(uri));
    }

    @Test
    public void make_share_the_app_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(R.id.share);

        listener.onNavigationItemSelected(menuItem);

        verify(activity).startActivity(eq(intent));
        verify(intent).setAction(eq(Intent.ACTION_SEND));
        verify(intent).putExtra(eq(Intent.EXTRA_TEXT), anyString());
        verify(intent).setType(eq("text/plain"));
    }

    @Test
    public void make_show_policy_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(R.id.privacy_policy);

        listener.onNavigationItemSelected(menuItem);

        verify(dialogFactory).buildPolicy();
        verify(dialogFactory).show();
    }

    @Test
    public void make_show_terms_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(R.id.tos);

        listener.onNavigationItemSelected(menuItem);

        verify(dialogFactory).buildTerms();
        verify(dialogFactory).show();
    }
}