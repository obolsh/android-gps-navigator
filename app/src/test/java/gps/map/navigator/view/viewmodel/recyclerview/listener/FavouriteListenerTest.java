package gps.map.navigator.view.viewmodel.recyclerview.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavouriteListenerTest {
    private IMapPlace mapPlace;
    private IPlacePickerCallback placePickerCallback;


    @Before
    public void setUp() throws Exception {
        mapPlace = mock(IMapPlace.class);
        placePickerCallback = mock(IPlacePickerCallback.class);
    }

    @Test
    public void receive_onClick_non_favourite_verify() {
        FavouriteListener listener = new FavouriteListener(mapPlace, placePickerCallback);
        when(mapPlace.isFavourite()).thenReturn(false);

        listener.onClick(null);

        verify(placePickerCallback).markAsFavouritePlace(eq(mapPlace));
    }

    @Test
    public void receive_onClick_is_favourite_verify() {
        FavouriteListener listener = new FavouriteListener(mapPlace, placePickerCallback);
        when(mapPlace.isFavourite()).thenReturn(true);

        listener.onClick(null);

        verify(placePickerCallback).markAdNotFavouritePlace(eq(mapPlace));
    }
}