package net.kuwalab.android.copypic;

import android.view.View;
import android.view.View.OnFocusChangeListener;

public class OnFocusChangeListenerImpl implements OnFocusChangeListener {
    private MainActivity activity;

    public OnFocusChangeListenerImpl(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.localPath:
                activity.onFocusOutLocalPathText(v, hasFocus);
                break;
        }
    }
}
