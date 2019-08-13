package gps.map.navigator;

import android.app.Application;

import gps.map.navigator.model.impl.common.DataCache;
import gps.map.navigator.model.impl.sdk.MapBoxSdkImpl;
import gps.map.navigator.model.strategy.CacheStrategy;
import gps.map.navigator.model.strategy.MapStrategy;
import gps.map.navigator.presenter.impl.PresenterImpl;
import gps.map.navigator.presenter.strategy.PresenterStrategy;

public class NavigatorApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        configureSingletonReferences();
    }

    private void configureSingletonReferences() {
        PresenterStrategy.getInstance().setStrategy(new PresenterImpl());
        CacheStrategy.getInstance().setStrategy(new DataCache(this));
        MapStrategy.getInstance().setStrategy(new MapBoxSdkImpl(this));
    }
}
