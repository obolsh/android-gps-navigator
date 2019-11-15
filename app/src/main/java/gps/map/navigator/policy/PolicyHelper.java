package gps.map.navigator.policy;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.Cache;

public class PolicyHelper implements Policy {

    @Nullable
    private Cache cache;
    private AtomicBoolean fromCache = new AtomicBoolean(false);

    public PolicyHelper(@Nullable Cache cache) {
        this.cache = cache;
    }

    @Override
    public boolean policyAndTermsAccepted() {
        return accepted();
    }

    private boolean accepted() {
        if (fromCache.get()) {
            return true;
        }
        if (cache == null) {
            return false;
        }
        byte[] data = cache.getRawData(Constants.ConsentStatus);
        if (data != null && data.length > 0) {
            String result = new String(data);
            return "accepted".equals(result);
        }
        return false;
    }

    @Override
    public void markPolicyAndTermsAsAccepted() {
        if (cache != null) {
            cache.setRawData(Constants.ConsentStatus, getPositiveStamp());
            fromCache.set(true);
        }
    }

    private byte[] getPositiveStamp() {
        return "accepted".getBytes();
    }
}
