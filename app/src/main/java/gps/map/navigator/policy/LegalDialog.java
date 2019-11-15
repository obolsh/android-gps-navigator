package gps.map.navigator.policy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class LegalDialog {

    private AlertDialog.Builder builder;
    private boolean disableButtons;
    private Legal legal;

    LegalDialog(Activity activity, Legal legal) {
        builder = new AlertDialog.Builder(activity)
                .setView(buildView(activity, legal))
                .setTitle(legal.getTitle());
        this.legal = legal;
    }

    private View buildView(Context context, Legal legal) {
        WebView webView = new WebView(context.getApplicationContext());
        webView.loadUrl(legal.getLegalUrl());
        return webView;
    }

    void disableButtons(boolean disableButtons) {
        this.disableButtons = disableButtons;
    }

    void show() {
        if (!disableButtons) {
            builder.setPositiveButton(legal.getPositiveText(), new Positive(legal))
                    .setNegativeButton(legal.getNegativeText(), new Negative(legal));
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static class Positive extends ReactionListener {

        private Positive(@Nullable Legal legal) {
            super(legal);
        }

        @Override
        void react(@NonNull LegalListener listener) {
            listener.onAgreed();
        }
    }

    private static class Negative extends ReactionListener {

        private Negative(@Nullable Legal legal) {
            super(legal);
        }

        @Override
        void react(@NonNull LegalListener listener) {
            listener.onDeclined();
        }
    }

    private static abstract class ReactionListener implements DialogInterface.OnClickListener {
        @Nullable
        private Legal legal;

        private ReactionListener(@Nullable Legal legal) {
            this.legal = legal;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (legal != null) {
                LegalListener listener = legal.getLegalListener();
                if (listener != null) {
                    react(listener);
                }
            }

            if (dialog != null) {
                dialog.dismiss();
            }
            legal = null;
        }

        abstract void react(@NonNull LegalListener listener);
    }
}
