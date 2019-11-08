package androidx.fragment.app;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentTransaction {

    public FragmentTransaction replace(@IdRes int containerViewId,
                                       @NonNull Fragment fragment, @Nullable String tag) {
        return this;
    }

    public FragmentTransaction addToBackStack(@Nullable String name) {
        return this;
    }

    public int commit() {
        return 0;
    }

    public FragmentTransaction remove(@NonNull Fragment fragment) {
        return this;
    }
}
