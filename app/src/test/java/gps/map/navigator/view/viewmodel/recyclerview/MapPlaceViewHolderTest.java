package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.viewmodel.recyclerview.listener.DeleteListener;
import gps.map.navigator.view.viewmodel.recyclerview.listener.FavouriteListener;
import gps.map.navigator.view.viewmodel.recyclerview.listener.PickedListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MapPlaceViewHolderTest {

    private ImageView favouriteImage;
    private TextView titleView;
    private TextView addressView;
    private IMapPlace mapPlace;
    private IPlacePickerCallback fragment;
    private ImageView deleteButton;
    private View rootView;

    @Before
    public void setUp() throws Exception {
        rootView = mock(View.class);
        favouriteImage = mock(ImageView.class);
        titleView = mock(TextView.class);
        addressView = mock(TextView.class);
        mapPlace = mock(IMapPlace.class);
        fragment = mock(IPlacePickerCallback.class);
        deleteButton = mock(ImageView.class);

        when(rootView.findViewById(eq(R.id.favourite_map_place))).thenReturn(favouriteImage);
        when(rootView.findViewById(eq(R.id.map_place_title))).thenReturn(titleView);
        when(rootView.findViewById(eq(R.id.map_place_address))).thenReturn(addressView);
        when(rootView.findViewById(eq(R.id.delete_map_place))).thenReturn(deleteButton);


        when(mapPlace.getTitle()).thenReturn("origin");
        when(mapPlace.getAddress()).thenReturn("address");
    }

    @Test
    public void make_setMapPlace_is_favourite_verify() {
        MapPlaceViewHolder holder = new MapPlaceViewHolder(rootView, fragment);
        when(mapPlace.isFavourite()).thenReturn(true);

        holder.setMapPlace(mapPlace);

        verify(favouriteImage).setImageResource(eq(R.drawable.outline_star_black_24dp));
        verify(titleView).setText(eq("origin"));
        verify(addressView).setText(eq("address"));

        verify(favouriteImage).setOnClickListener(any(FavouriteListener.class));
        verify(deleteButton).setOnClickListener(any(DeleteListener.class));
        verify(rootView).setOnClickListener(any(PickedListener.class));
    }

    @Test
    public void make_setMapPlace_not_favourite_verify() {
        MapPlaceViewHolder holder = new MapPlaceViewHolder(rootView, fragment);
        when(mapPlace.isFavourite()).thenReturn(false);

        holder.setMapPlace(mapPlace);

        verify(favouriteImage).setImageResource(eq(R.drawable.outline_star_border_black_24dp));
        verify(titleView).setText(eq("origin"));
        verify(addressView).setText(eq("address"));

        verify(favouriteImage).setOnClickListener(any(FavouriteListener.class));
        verify(deleteButton).setOnClickListener(any(DeleteListener.class));
        verify(rootView).setOnClickListener(any(PickedListener.class));
    }
}