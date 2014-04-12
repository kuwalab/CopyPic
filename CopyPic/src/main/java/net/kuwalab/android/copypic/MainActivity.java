package net.kuwalab.android.copypic;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import net.kuwalab.android.copypic.DirSelectDialog.OnDirSelectDialogListener;
import net.kuwalab.android.util.FileSizeUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText localPathText;
    private TextView dirInfo;
    private EditText serverPathText;
    private EditText serverIdText;
    private EditText serverPasswordText;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClickListener ocl = new OnClickListenerImpl(this);
        OnFocusChangeListener ofcl = new OnFocusChangeListenerImpl(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        localPathText = (EditText) findViewById(R.id.localPath);
        localPathText.setText(pref.getString("localPath", ""));
        dirInfo = (TextView) findViewById(R.id.dirInfo);
        Button dirSelectButton = (Button) findViewById(R.id.dirSelectButton);
        dirSelectButton.setOnClickListener(ocl);
        Button autoSearcch = (Button) findViewById(R.id.autoSearch);
        autoSearcch.setOnClickListener(ocl);

        serverPathText = (EditText) findViewById(R.id.serverPath);
        serverPathText.setText(pref.getString("serverPath", ""));
        serverIdText = (EditText) findViewById(R.id.serverId);
        serverPasswordText = (EditText) findViewById(R.id.serverPassword);

        Button copyButton = (Button) findViewById(R.id.copyButton);
        copyButton.setOnClickListener(ocl);

        localPathText.addTextChangedListener(new TextWatcherImpl(pref,
                "localPath"));
        localPathText.setOnFocusChangeListener(ofcl);
        serverPathText.addTextChangedListener(new TextWatcherImpl(pref,
                "serverPath"));

        calcDirSize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * ディレクトリ選択完了イベント
     */
    public void onClickDirSelect(File file) {
        if (file != null) {
            // 選択ディレクトリを設定
            ((TextView) findViewById(R.id.localPath)).setText(file.getPath());
        }
    }

    private Long calcDirSize() {
        File file = new File(localPathText.getText().toString());

        if (!file.exists()) {
            dirInfo.setText(R.string.local_dir_not_exist);
            return 0L;
        }
        if (!file.isDirectory()) {
            dirInfo.setText(R.string.local_dir_not_dir);
            return 0L;
        }

        File[] files = file.listFiles();
        int fileCount = 0;
        long fileSize = 0;
        for (File localFile : files) {
            if (!localFile.isDirectory()) {
                fileCount++;
                try {
                    fileSize += localFile.length();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        dirInfo.setText(String.format(getString(R.string.local_dir_info),
                fileCount, FileSizeUtil.getFileSizeForView(fileSize)));

        return fileSize;
    }

    public void onClickAutoSearch(View v) {
        File file = new File(Environment.getExternalStorageDirectory(),
                "DCIM/Camera");
        if (file.exists()) {
            try {
                localPathText.setText(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "見つからなかったです。", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCopyButton(View v) {
        String date = DateFormat.format("yyyyMMdd", Calendar.getInstance())
                .toString();
        String serverPath = serverPathText.getText().toString();
        if (!serverPath.endsWith("/")) {
            serverPath = serverPath + "/";
        }
        CopySetting cs = new CopySetting(localPathText.getText().toString(),
                serverPath + "pic" + date + "/", serverIdText.getText()
                .toString(), serverPasswordText.getText().toString()
        );

        RefTask refTask = new RefTask(v.getContext(), cs);
        refTask.setFileSize((int) (calcDirSize() / 1024));
        refTask.execute();
    }

    public void onClickDirSelectButton(View v) {
        OnDirSelectDialogListener odsdl = new OnDirSelectDialogListenerImpl(
                this);

        // ファイル選択ダイアログを表示
        DirSelectDialog dialog = new DirSelectDialog(this);
        dialog.setOnDirSelectDialogListener(odsdl);

        // 表示
        dialog.show(Environment.getExternalStorageDirectory().getPath());
    }

    public void onFocusOutLocalPathText(View v, boolean hasFocus) {
        if (!hasFocus) {
            calcDirSize();
        }
    }
}
