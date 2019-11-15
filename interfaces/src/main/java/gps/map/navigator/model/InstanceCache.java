package gps.map.navigator.model;

import java.lang.ref.SoftReference;

public class InstanceCache<T> {

    private SoftReference<T> reference;

    public InstanceCache(T instance) {
        reference = new SoftReference<>(instance);
    }

    public T getInstance() {
        if (reference != null) {
            return reference.get();
        } else {
            return null;
        }
    }

    public void invalidate() {
        reference = null;
    }
}
