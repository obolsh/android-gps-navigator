package gps.map.navigator.model.bundle;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.common.Constants;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.IDecorCache;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CacheBundleImpl.class})
public class CacheBundleImplTest {

    private DecorCacheImpl decorCache;
    private String fragmentTag = "foo";
    private Bundle bundle;
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        decorCache = mock(DecorCacheImpl.class, CALLS_REAL_METHODS);
        bundle = mock(Bundle.class);
        logger = mock(Logger.class);

        whenNew(DecorCacheImpl.class).withAnyArguments().thenReturn(decorCache);
    }

    private CacheBundleImpl getCache() {
        CacheBundleImpl cacheBundle = new CacheBundleImpl();
        setInternalState(cacheBundle, "decorCache", decorCache);
        setInternalState(cacheBundle, "logger", logger);
        setInternalState(cacheBundle, "fragmentTag", fragmentTag);
        return cacheBundle;
    }

    @Test
    public void make_getDecorCache_verify() {
        CacheBundleImpl cacheBundle = getCache();

        IDecorCache created = cacheBundle.getDecorCache();

        assertSame(decorCache, created);
    }

    @Test
    public void make_getActiveFragmentTag_verify() {
        CacheBundleImpl cacheBundle = getCache();

        String created = cacheBundle.getActiveFragmentTag();

        assertEquals(fragmentTag, created);
    }

    @Test
    public void make_readBundle_missing_decor_cache_verify() {
        CacheBundleImpl cacheBundle = new CacheBundleImpl();

        cacheBundle.readBundle(bundle);

        String tag = getInternalState(cacheBundle, "fragmentTag");
        IDecorCache cache = getInternalState(cacheBundle, "decorCache");

        assertNull(tag);
        assertNull(cache);
    }

    @Test
    public void make_readBundle_has_decor_cache_verify() {
        CacheBundleImpl cacheBundle = getCache();
        when(bundle.getSerializable(eq(Constants.DecorCache))).thenReturn(decorCache);
        when(bundle.getString(eq(Constants.FragmentTagCache))).thenReturn(fragmentTag);

        cacheBundle.readBundle(bundle);

        String tag = getInternalState(cacheBundle, "fragmentTag");
        IDecorCache cache = getInternalState(cacheBundle, "decorCache");

        assertEquals(fragmentTag, tag);
        assertSame(decorCache, cache);
    }
}