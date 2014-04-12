package net.kuwalab.android.copypic;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.text.TextWatcher;

public class TextWatcherImpl implements TextWatcher {
    private SharedPreferences pref;
    private String key;

    public TextWatcherImpl(SharedPreferences pref, String key) {
        this.pref = pref;
        this.key = key;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Editor editor = pref.edit();
        editor.putString(key, s.toString());
        editor.commit();
    }
}
