package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


public class RecyclerView extends View {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public RecyclerView(Context context) {
        super(context);
    }

    public static class LayoutManager {

    }

    public static class Adapter<ViewHolder> {

    }

    public static class ViewHolder {

    }
    public void setAdapter(Adapter adapter) {

    }

    public void setHasFixedSize(boolean hasFixedSize) {

    }

    public void setLayoutManager(LayoutManager layout) {

    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

}
