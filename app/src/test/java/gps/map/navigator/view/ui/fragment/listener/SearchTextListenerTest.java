package gps.map.navigator.view.ui.fragment.listener;

import android.widget.Filter;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

public class SearchTextListenerTest {

    private AbstractAdapter adapter;
    private Presenter presenter;
    private IPlaceListener placeListener;
    private Filter filter;

    @Before
    public void setUp() throws Exception {
        adapter = mock(AbstractAdapter.class);
        presenter = mock(Presenter.class);
        placeListener = mock(IPlaceListener.class);
        filter = mock(Filter.class);

        when(adapter.getFilter()).thenReturn(filter);
    }

    @Test
    public void receive_onQueryTextSubmit_verify() {
        SearchTextListener listener = new SearchTextListener();
        setInternalState(listener, "presenter", presenter);
        setInternalState(listener, "placeListener", placeListener);

        boolean return_false = listener.onQueryTextSubmit("foo");

        assertFalse(return_false);

        verify(presenter).findPlace(eq("foo"), eq(placeListener));
    }

    @Test
    public void receive_onQueryTextChange_verify() {
        SearchTextListener listener = new SearchTextListener();
        setInternalState(listener, "adapter", adapter);

        boolean return_true = listener.onQueryTextChange("foo");

        assertTrue(return_true);

        verify(filter).filter(eq("foo"));
    }
}