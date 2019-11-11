package gps.map.navigator.di.fragment;

import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.ui.fragment.listener.SearchTextListener;
import gps.map.navigator.view.ui.fragment.listener.ShowSinglePlaceListener;
import gps.map.navigator.view.viewmodel.callback.FindPlaceCallback;


@Module(includes = {AdapterModule.class, CallbackModule.class})
public class FindPlaceFragmentModule {

    @Provides
    Fragment provideFragment(FindPlaceFragment fragment) {
        return fragment;
    }

    @Provides
    IPlacePickerCallback provideIPlacePickerCallback(FindPlaceFragment fragment) {
        return fragment;
    }

    @Provides
    ICachedPlaceCallback provideICachedPlaceCallback(FindPlaceFragment fragment) {
        return fragment;
    }

    @FragmentScope
    @Provides
    SearchView.OnQueryTextListener provideSearchTextListener(SearchTextListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    PlaceProxyListener provideShowSinglePlaceListener(ShowSinglePlaceListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    IPlaceListener provideFindPlaceCallback(FindPlaceCallback findPlaceCallback) {
        return findPlaceCallback;
    }
}
