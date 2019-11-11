package gps.map.navigator.view.viewmodel.recyclerview.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteListenerTest {
    private RecyclerView.ViewHolder viewHolder;
    private IMapPlace mapPlace;
    private IPlacePickerCallback placePickerCallback;


    @Before
    public void setUp() throws Exception {
        viewHolder = mock(RecyclerView.ViewHolder.class);
        mapPlace = mock(IMapPlace.class);
        placePickerCallback = mock(IPlacePickerCallback.class);
        when(viewHolder.getAdapterPosition()).thenReturn(123);
    }

    @Test
    public void receive_onClick_favourite_verify() {
        DeleteListener listener = new DeleteListener(viewHolder, mapPlace, placePickerCallback);

        listener.onClick(null);

        verify(placePickerCallback).deleteHistoryPlace(eq(123), eq(mapPlace));
    }
}