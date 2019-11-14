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

    public FragmentTransaction setCustomAnimations(int enter,
                                                            int exit, int popEnter,
                                                            int popExit) {
        return this;
    }

    public FragmentTransaction detach(@NonNull Fragment fragment) {
        return this;
    }


    public FragmentTransaction attach(@NonNull Fragment fragment) {
        return this;
    }
}
