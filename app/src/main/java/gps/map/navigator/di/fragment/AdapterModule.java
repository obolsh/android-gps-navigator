package gps.map.navigator.di.fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;
import gps.map.navigator.view.viewmodel.recyclerview.MapPlaceAdapter;

@Module
public class AdapterModule {

    @FragmentScope
    @Provides
    AbstractAdapter provideMapPlaceAdapter(MapPlaceAdapter adapter) {
        return adapter;
    }

}
