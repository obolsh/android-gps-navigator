package androidx.recyclerview.widget;

import android.content.Context;
import android.view.ViewGroup;

public class LinearLayoutManager extends RecyclerView.LayoutManager {

    public LinearLayoutManager(Context context) {
    }

    public void setOrientation(int orientation) {

    }

    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
