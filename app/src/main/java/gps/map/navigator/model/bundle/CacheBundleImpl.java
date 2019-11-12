package gps.map.navigator.model.bundle;

import android.os.Bundle;

import javax.inject.Inject;

import gps.map.navigator.common.Constants;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.ICacheBundle;
import gps.map.navigator.model.interfaces.IDecorCache;

public class CacheBundleImpl implements ICacheBundle {

    private IDecorCache decorCache;
    private String fragmentTag;
    @Inject
    Logger logger;

    @Inject
    CacheBundleImpl() {
    }

    @Override
    public IDecorCache getDecorCache() {
        if (decorCache == null) {
            decorCache = new DecorCacheImpl();
        }
        return decorCache;
    }

    @Override
    public String getActiveFragmentTag() {
        return fragmentTag;
    }

    @Override
    public void readBundle(Bundle bundle) {
        try {
            IDecorCache cache = (IDecorCache) bundle.getSerializable(Constants.DecorCache);
            if (cache != null) {
                decorCache =
                        new DecorCacheImpl(cache.isShowMeOnMapActive(),
                                cache.isFloatingActionButtonActive(),
                                cache.isButtomAppBarActive(),
                                cache.getFloatingActionButtonAlignent());
            }
        } catch (Throwable t) {
            logger.error(t);
        }

        String tag = bundle.getString(Constants.FragmentTagCache);
        if (tag != null) {
            fragmentTag = tag;
        }
    }
}
