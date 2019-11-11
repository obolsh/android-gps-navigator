package androidx.fragment.app;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return null;
    }

    public void onAttach(Context context) {

    }

    public void onStart() {

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    public Resources getResources() {
        return null;
    }
}
