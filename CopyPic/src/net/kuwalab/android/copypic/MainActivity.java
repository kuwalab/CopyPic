package net.kuwalab.android.copypic;

import java.io.File;
import java.util.Calendar;

import net.kuwalab.android.copypic.DirSelectDialog.OnDirSelectDialogListener;
import net.kuwalab.copypic.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		OnDirSelectDialogListener {
	private EditText localPathText;
	private EditText serverPathText;
	private EditText serverIdText;
	private EditText serverPasswordText;

	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pref = PreferenceManager.getDefaultSharedPreferences(this);
		localPathText = (EditText) findViewById(R.id.localPath);
		localPathText.setText(pref.getString("localPath", ""));
		Button dirSelectButton = (Button) findViewById(R.id.dirSelectButton);
		dirSelectButton.setOnClickListener(this);

		serverPathText = (EditText) findViewById(R.id.serverPath);
		serverPathText.setText(pref.getString("serverPath", ""));
		serverIdText = (EditText) findViewById(R.id.serverId);
		serverPasswordText = (EditText) findViewById(R.id.serverPassword);

		Button copyButton = (Button) findViewById(R.id.copyButton);
		copyButton.setOnClickListener(this);

		localPathText.addTextChangedListener(tw);
		serverPathText.addTextChangedListener(tw2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.copyButton:
			String date = DateFormat.format("yyyyMMdd", Calendar.getInstance())
					.toString();
			CopySetting cs = new CopySetting(
					localPathText.getText().toString(), serverPathText
							.getText().toString() + "pic" + date + "/",
					serverIdText.getText().toString(), serverPasswordText
							.getText().toString());

			new RefTask(v.getContext(), cs).execute();
			break;
		case R.id.dirSelectButton:
			// ファイル選択ダイアログを表示
			DirSelectDialog dialog = new DirSelectDialog(this);
			dialog.setOnDirSelectDialogListener(this);

			// 表示
			dialog.show(Environment.getExternalStorageDirectory().getPath());
			break;
		}

	}

	private TextWatcher tw = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			Editor editor = pref.edit();
			editor.putString("localPath", s.toString());
			editor.commit();
		}
	};

	private TextWatcher tw2 = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			Editor editor = pref.edit();
			editor.putString("serverPath", s.toString());
			editor.commit();
		}
	};

	/**
	 * ディレクトリ選択完了イベント
	 */
	@Override
	public void onClickDirSelect(File file) {

		if (file != null) {
			// 選択ディレクトリを設定
			((TextView) findViewById(R.id.localPath)).setText(file.getPath());
		}
	}
}
