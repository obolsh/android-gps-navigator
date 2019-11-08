package gps.map.navigator.view.ui.fragment.listener;

import android.content.Context;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NavigationViewListener.class, Intent.class, Uri.class})
public class NavigationViewListenerTest {

    private Context context;
    private MenuItem menuItem;
    private Intent intent;
    private Uri uri;

    @Before
    public void setUp() throws Exception {
        mockStatic(Intent.class);
        mockStatic(Uri.class);

        context = mock(Context.class);
        menuItem = mock(MenuItem.class);
        intent = mock(Intent.class);
        uri = mock(Uri.class);

        when(context.getApplicationContext()).thenReturn(context);
        when(context.getString(anyInt())).thenReturn("foo");
        when(Uri.parse(anyString())).thenReturn(uri);
        when(Intent.createChooser(eq(intent), anyString())).thenReturn(intent);

        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
    }

    @After
    public void tearDown() throws Exception {
        when(menuItem.getItemId()).thenReturn(-1);
    }

    private NavigationViewListener createListener() {
        NavigationViewListener listener = new NavigationViewListener();
        setInternalState(listener, "context", context);
        return listener;
    }

    @Test
    public void make_menu_picked_unknown_key_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(-1);

        listener.onNavigationItemSelected(menuItem);

        verify(context, times(0)).startActivity(any(Intent.class));
    }

    @Test
    public void make_navigate_to_rate_us_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(R.id.rate_us);

        listener.onNavigationItemSelected(menuItem);

        verify(context).startActivity(eq(intent));
        verify(intent).setAction(eq(Intent.ACTION_VIEW));
        verify(intent).setData(eq(uri));
    }

    @Test
    public void make_share_the_app_verify() {
        NavigationViewListener listener = createListener();
        when(menuItem.getItemId()).thenReturn(R.id.share);

        listener.onNavigationItemSelected(menuItem);

        verify(context).startActivity(eq(intent));
        verify(intent).setAction(eq(Intent.ACTION_SEND));
        verify(intent).putExtra(eq(Intent.EXTRA_TEXT), eq("foo"));
        verify(intent).setType(eq("text/plain"));
    }
}