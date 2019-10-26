package gps.map.navigator.di;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.MainActivity;
import gps.map.navigator.view.viewmodel.DecorController;

@Module
public class DecorModule {

    @Provides
    DecorController provideDecorController(MainActivity mainActivity) {
        return (DecorController) mainActivity;
    }
}
