package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import gps.map.navigator.R;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    private NavigationView navigationView;
//    private IViewInteraction viewInteraction;

//    public BottomNavigationDrawerFragment(IViewInteraction viewInteraction) {
//        this.viewInteraction = viewInteraction;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
        navigationView = view.findViewById(R.id.navigation_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.enable_traffic:
//                        viewInteraction.enableTrafficMode(true);
                        break;
                    case R.id.enable_night_mode:
//                        viewInteraction.enableNightMode(true);
                        break;

                    default:

                }
                return true;
            }
        });
    }
}
